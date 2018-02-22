/**
* Copyright  2017
* 
* All  right  reserved.
*
* Created  on  2017年8月9日 下午9:22:19
*
* @Package com.happgo.dlc.base.util  
* @Title: DateUtils.java
* @Description: DateUtils.java
* @author sxp (1378127237@qq.com) 
* @version 1.0.0 
*/
package com.happgo.dlc.base.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.happgo.dlc.base.DLCException;

/**
 * ClassName:DateUtils
 * @Description: DateUtils.java
 * @author sxp (1378127237@qq.com) 
 * @date:2017年8月9日 下午9:22:19
 */
public class DateUtils {
	

    /** 年-月-日 显示格式 */
    public static final String YYYY_MMM_DD = "yyyy-MM-dd";

    /** 年-月-日 时:分:秒 显示格式 */
    // 备注:如果使用大写HH标识使用24小时显示格式,如果使用小写hh就表示使用12小时制格式。
    public static final String YYYY_MMM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    
    /** 年-月-日 时:分:秒 毫秒**/
    public static final String YYYY_MMM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss SSS";
    
    /**
     * String the YYYY_MMM_DD_PATTERN 
     */
    public static final String YYYY_MMM_DD_PATTERN = "^[1-9]\\d{3}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[1-2][0-9]|3[0-1]|[1-9])$";
    
    /**
     * String the YYYY_MMM_DD_HH_MM_SS_PATTERN 
     */
    public static final String YYYY_MMM_DD_HH_MM_SS_PATTERN = "^[1-9]\\d{3}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[1-2][0-9]|3[0-1]|[1-9])\\s+(20|21|22|23|[0-1]\\d|\\d):([0-5]\\d|\\d):([0-5]\\d|\\d)$";

    /**
     * String the YYYY_MMM_DD_HH_MM_SS_SSS_PATTERN 
     */
    public static final String YYYY_MMM_DD_HH_MM_SS_SSS_PATTERN = "^[1-9]\\d{3}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[1-2][0-9]|3[0-1]|[1-9])\\s+(20|21|22|23|[0-1]\\d|\\d):([0-5]\\d|\\d):([0-5]\\d|\\d)\\s+[0-9]?[0-9]?[0-9]$";

    /**
     * SimpleDateFormat the simpleDateFormat 
     */
    private static SimpleDateFormat simpleDateFormat;

    /**
     * Date类型转为指定格式的String类型
     * 
     * @param source
     * @param pattern
     * @return
     */
    public static String DateToString(Date source, String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(source);
    }

    /**
     * 
     * unix时间戳转为指定格式的String类型
     * 
     * 
     * System.currentTimeMillis()获得的是是从1970年1月1日开始所经过的毫秒数
     * unix时间戳:是从1970年1月1日（UTC/GMT的午夜）开始所经过的秒数,不考虑闰秒
     * 
     * @param source
     * @param pattern
     * @return
     */
    public static String timeStampToString(long source, String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(source * 1000);
        return simpleDateFormat.format(date);
    }

    /**
     * 将日期转换为时间戳(unix时间戳,单位秒)
     * 
     * @param date
     * @return
     */
    public static long dateToTimeStamp(Date date) {
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp.getTime() / 1000;
    }

    /**
     * 
     * 字符串转换为对应日期(可能会报错异常)
     * 
     * @param source
     * @param pattern
     * @return
     */
    public static Date stringToDate(String source, String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = simpleDateFormat.parse(source);
        } catch (ParseException e) {
            throw new DLCException("字符串转换日期异常", e);
        }
        return date;
    }
    
    /**
     * 判断字符串是否为日期
     * 
     * @param source
     * @param pattern
     * @return boolean
     */
    public static Date isDate(String source) {
    	if (source.matches(YYYY_MMM_DD_PATTERN)) {
    		return stringToDate(source, YYYY_MMM_DD);
    	}

    	if (source.matches(YYYY_MMM_DD_HH_MM_SS_PATTERN)) {
    		return stringToDate(source, YYYY_MMM_DD_HH_MM_SS);
    	}

    	if (source.matches(YYYY_MMM_DD_HH_MM_SS_SSS_PATTERN)) {
    		return stringToDate(source, YYYY_MMM_DD_HH_MM_SS_SSS);
    	}
    	return null;
    }

    /**
     * 获得当前时间对应的指定格式
     * 
     * @param pattern
     * @return
     */
    public static String currentFormatDate(String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());

    }

    /**
     * 获得当前unix时间戳(单位秒)
     * 
     * @return 当前unix时间戳
     */
    public static long currentTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }
}