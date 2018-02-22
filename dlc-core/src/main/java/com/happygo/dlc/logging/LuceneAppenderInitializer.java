/**
* Copyright  2017
* 
* All  right  reserved.
*
* Created  on  2017年8月15日 下午7:32:46
*
* @Package com.happygo.dlc.logging  
* @Title: LuceneAppenderInitializer.java
* @Description: LuceneAppenderInitializer.java
* @author sxp (1378127237@qq.com) 
* @version 1.0.0 
*/
package com.happygo.dlc.logging;

import java.io.File;
import java.util.Calendar;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.search.NumericRangeQuery;

import com.happgo.dlc.base.DLCException;
import com.happygo.dlc.lucene.LuceneIndexWriter;

/**
 * ClassName:LuceneAppenderInitializer
 * @Description: LuceneAppenderInitializer.java
 * @author sxp (1378127237@qq.com) 
 * @date:2017年8月15日 下午7:32:46
 */
public class LuceneAppenderInitializer {
	
    /**
     * The constant LOGGER.
     */
	private static final Log LOGGER = LogFactory.getLog(LuceneAppenderInitializer.class);
	
	/**
	 * Constructor com.happygo.dlc.logging.LuceneAppenderInitializer
	 */
	private LuceneAppenderInitializer() {}
	
	public static void init(String target, long expiryTime, 
			ConcurrentMap<String, LuceneIndexWriter> writerMap,
			ScheduledExecutorService scheduledExecutor) {
        //初始化writeMap
        initLuceneIndexWriter(target, writerMap);
        if (expiryTime != 0) {
            //注册定时清理过期索引文件定时器
            registerClearTimer(target, expiryTime, writerMap, scheduledExecutor);
        }
	}
    
    /**
     * Register clear timer.
     */
    private static void registerClearTimer(final String target, final long expiryTime, 
			final ConcurrentMap<String, LuceneIndexWriter> writerMap,
			ScheduledExecutorService scheduledExecutor) {
        Calendar calendar = Calendar.getInstance();
        long curMillis = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long difMinutes = (calendar.getTimeInMillis() - curMillis)
                / (1000 * 60);

        scheduledExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                LOGGER.info("delete index start!");
                LuceneIndexWriter indexWriter = initLuceneIndexWriter(target, writerMap);
                if (null != indexWriter) {
                    Long start = 0L;
                    Long end = System.currentTimeMillis() - expiryTime * 1000;
                    NumericRangeQuery<Long> rangeQuery = NumericRangeQuery
                            .newLongRange("time", start, end, true, true);
                    indexWriter.deleteDocuments(rangeQuery);
                    indexWriter.commit();
                    LOGGER.info("delete index end!");
                }
            }
        }, difMinutes, 1440, TimeUnit.MINUTES);
    }
    
    /**
     * Init lucene index writer lucene index writer.
     *
     * @return the lucene index writer
     */
    public static LuceneIndexWriter initLuceneIndexWriter(String target, 
			ConcurrentMap<String, LuceneIndexWriter> writerMap) {
        if(new File(target).isFile()) {
            throw new DLCException("Lucene index path must be directory, but target path:'" + target + "' is file!");
        }
        int writerSize = writerMap.size();
        if ((writerSize == 0) || (writerSize == 1)) {
            if (null == writerMap.get(target)) {
                writerMap.putIfAbsent(target, LuceneIndexWriter.indexWriter(
                        new CJKAnalyzer(), target));
            }
            return writerMap.get(target);
        }
        throw new DLCException("lucene index store path must be only one!");
    }
}
