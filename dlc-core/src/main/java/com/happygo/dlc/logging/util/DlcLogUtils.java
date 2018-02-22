/**
* Copyright  2017
* 
* All  right  reserved.
*
* Created  on  2017年8月15日 下午8:13:56
*
* @Package com.happygo.dlc.logging.util  
* @Title: DlcLogUtils.java
* @Description: DlcLogUtils.java
* @author sxp (1378127237@qq.com) 
* @version 1.0.0 
*/
package com.happygo.dlc.logging.util;

import com.happygo.dlc.logging.Log4j2LuceneAppender;
import com.happygo.dlc.logging.Log4jLuceneAppender;
import com.happygo.dlc.lucene.LuceneIndexWriter;

import java.util.List;
import java.util.Map;

/**
 * ClassName:DlcLogUtils
 *
 * @author sxp (1378127237@qq.com)
 * @Description: DlcLogUtils.java
 * @date:2017年8月15日 下午8 :13:56
 */
public class DlcLogUtils {

	/**
	 * List<String> the queryConditions
	 */
	private static List<String> queryConditions;

	/**
	 * Map<String,LuceneIndexWriter> the writeMap
	 */
	private static Map<String, LuceneIndexWriter> writeMap;

	/**
	 * The constant LOG4J_TYPE.
	 */
	private static final String LOG4J_TYPE = "log4j";

	/**
	 * The constant LOG4J2_TYPE.
	 */
	private static final String LOG4J2_TYPE = "log4j2";

	/**
	 * Constructor com.happygo.dlc.logging.util.LogUtils
	 */
	private DlcLogUtils() {}

	/**
	 * Gets query conditions from appender.
	 *
	 * @return List<String> query conditions from appender
	 * @MethodName: getQueryConditionsFromAppender
	 * @Description: the getQueryConditionsFromAppender
	 */
	public static List<String> getQueryConditionsFromAppender() {
		if (queryConditions != null && !queryConditions.isEmpty()) {
			return queryConditions;
		}
		String logUtilName = System.getProperty("dlc.log.util");
		if (LOG4J_TYPE.equals(logUtilName)) {
			return Log4jLuceneAppender.indexFieldNameList;
		} else if (LOG4J2_TYPE.equals(logUtilName)) {
			return Log4j2LuceneAppender.indexFieldNameList;
		}
		return null;
	}

	/**
	 * Gets write map.
	 *
	 * @return Map<String LuceneIndexWriter>
	 * @MethodName: getWriteMap
	 * @Description: the getWriteMap
	 */
	public static Map<String, LuceneIndexWriter> getWriteMap() {
		if (writeMap != null && !writeMap.isEmpty()) {
			return writeMap;
		}
		String logUtilName = System.getProperty("dlc.log.util");
		if (LOG4J_TYPE.equals(logUtilName)) {
			return Log4jLuceneAppender.writerMap;
		} else if (LOG4J2_TYPE.equals(logUtilName)) {
			return Log4j2LuceneAppender.writerMap;
		}
		return null;
	}
}