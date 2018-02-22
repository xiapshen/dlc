package com.happygo.dlc.logging;

import com.happgo.dlc.base.DLCException;
import com.happgo.dlc.base.DlcConstants;
import com.happgo.dlc.base.util.Assert;
import com.happygo.dlc.lucene.LuceneIndexWriter;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.TextField;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * ClassName:Log4jLuceneAppender
 *
 * @author sxp (1378127237@qq.com)
 * @Description: Log4jLuceneAppender.java
 * @date:2017年8月15日 上午16 :39:31
 */
public class Log4jLuceneAppender extends AppenderSkeleton {

    /**
     * index directory
     */
    private String target;

    /**
     * Index expiration time (seconds)
     */
    private long expiryTime;

    /**
     * The System name.
     */
    private String systemName;

    /**
     * Periodically clear the index and submit the IndexWriter thread pool
     */
    private final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(2);

    /**
     * LuceneIndexWriter corresponding to each index directory.
     */
    public static final ConcurrentHashMap<String, LuceneIndexWriter> writerMap = new ConcurrentHashMap<String, LuceneIndexWriter>();

    /**
     * List<String> the indexFieldNameList 
     */
    public static final List<String> indexFieldNameList = new CopyOnWriteArrayList<>();
    
    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = LogManager.getLogger(Log4jLuceneAppender.class);

    /**
     * Gets target.
     *
     * @return the target
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets target.
     *
     * @param target the target
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * Gets expiry time.
     *
     * @return the expiry time
     */
    public long getExpiryTime() {
        return expiryTime;
    }

    /**
     * Sets expiry time.
     *
     * @param expiryTime the expiry time
     */
    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }

    /**
     * Getter for property 'systemName'.
     *
     * @return Value for property 'systemName'.
     */
    public String getSystemName() {
        return systemName;
    }

    /**
     * Setter for property 'systemName'.
     *
     * @param systemName Value to set for property 'systemName'.
     */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    /**
     * Derived appenders should override this method if option structure
     * requires it.
     */
    @Override
    public void activateOptions() {
        //校验必填索引字段
        validateParams();
        initIndexFieldAndSetSystemProp();
		LuceneAppenderInitializer.init(target, expiryTime, writerMap, scheduledExecutor);
    }
    
    /**
    * @MethodName: initIndexFieldAndSetSystemProp
    * @Description: the initIndexFieldNames
    */
    protected void initIndexFieldAndSetSystemProp() {
    	indexFieldNameList.add(DlcConstants.DLC_LEVEL);
    	indexFieldNameList.add(DlcConstants.DLC_TIME);
    	indexFieldNameList.add(DlcConstants.DLC_CONTENT);
        System.setProperty("dlc.log.util", "log4j");
    }

    /**
     * Validate params.
     */
    private void validateParams() {
        if (target == null || target == "") {
            throw new DLCException("Param 'Target' is required!");
        }
        if (systemName == null || systemName == "") {
            throw new DLCException("Param 'SystemName' is required!");
        }
    }

    /**
     * Subclasses of <code>AppenderSkeleton</code> should implement this
     * method to perform actual logging. See also {@link #doAppend
     * AppenderSkeleton.doAppend}** method.
     *
     * @param event the event
     * @since 0.9.0
     */
    @Override
    protected void append(LoggingEvent event) {
    	luceneIndexWrite(event);
    }
    
    /**
    * @MethodName: luceneIndexWrite
    * @Description: the luceneIndexWrite
    * @param event
    */
    protected void luceneIndexWrite(LoggingEvent event) {
        LuceneIndexWriter indexWriter = LuceneAppenderInitializer.initLuceneIndexWriter(target, writerMap);
        Assert.isNull(indexWriter);
        Document doc = new Document();
    	doc.add(new LongField(DlcConstants.DLC_TIME, event.getTimeStamp(),
				Field.Store.YES));
        doc.add(new NumericDocValuesField(DlcConstants.DLC_TIME, event.getTimeStamp()));
        try {
            String hostIp = InetAddress.getLocalHost().getHostAddress();
            doc.add(new TextField(DlcConstants.DLC_HOST_IP, hostIp,
                    Field.Store.YES));
        } catch (UnknownHostException e) {
            LOGGER.warn("<<<=== Do not get the node hostIp ===>>>");
        }
        doc.add(new TextField(DlcConstants.SYSTEM_NAME, systemName,
                Field.Store.YES));
        String level = event.getLevel().toString();
        doc.add(new TextField(DlcConstants.DLC_LEVEL, level,
                Field.Store.YES));
        String logMsg = this.layout.format(event);
        ThrowableInformation throwableInformation = event.getThrowableInformation();
        if (throwableInformation != null) {
            StackTraceElement[] stackTraceElements = throwableInformation.getThrowable().getStackTrace();
            StringBuilder stringBuilder = new StringBuilder();
            for (final StackTraceElement stackTraceElement : stackTraceElements) {
                stringBuilder.append(stackTraceElement).append("<br/>");
            }
            logMsg += stringBuilder.toString();
        }
        doc.add(new TextField(DlcConstants.DLC_CONTENT, logMsg,
                Field.Store.YES));
        indexWriter.addDocument(doc);
        indexWriter.commit();
	}

    /**
     * Release any resources allocated within the appender such as file
     * handles, network connections, etc.
     * <p/>
     * <p>It is a programming error to append to a closed appender.
     *
     * @since 0.8.4
     */
    @Override
    public void close() {
        LuceneIndexWriter indexWriter = writerMap.get(target);
        if (null != indexWriter) {
            indexWriter.close();
            writerMap.remove(target);
        }
    }

    /**
     * Configurators call this method to determine if the appender
     * requires a layout. If this method returns <code>true</code>,
     * meaning that layout is required, then the configurator will
     * configure an layout using the configuration information at its
     * disposal.  If this method returns <code>false</code>, meaning that
     * a layout is not required, then layout configuration will be
     * skipped even if there is available layout configuration
     * information at the disposal of the configurator..
     * <p/>
     * <p>In the rather exceptional case, where the appender
     * implementation admits a layout but can also work without it, then
     * the appender should return <code>true</code>.
     *
     * @return the boolean
     * @since 0.8.4
     */
    @Override
    public boolean requiresLayout() {
        return true;
    }
}