/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年6月7日 上午9:57:21
 *
 * @Package com.happygo.dlc.common.util  
 * @Title: DateUtils.java
 * @Description: DateUtils.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happygo.dlc.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName:DateUtils
 * 
 * @Description: DateUtils.java
 * @author sxp (1378127237@qq.com)
 * @date:2017年6月7日 上午9:57:21
 */
public class DateUtils {
	
	private DateUtils() {}
	
	/**
	* @MethodName: date2String
	* @Description: the method date2String
	* @param time
	* @param pattern
	* @return String
	*/
	public static String date2String(long time, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = new Date(time);
		return sdf.format(date);
	}
}
