/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年8月15日 下午8:07:54
 *
 * @Package com.happgo.dlc.base.util  
 * @Title: ClassLoaderUtils.java
 * @Description: ClassloaderUtils.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happgo.dlc.base.util;

/**
 * ClassName:ClassLoaderUtils
 * 
 * @Description: ClassLoaderUtils.java
 * @author sxp (1378127237@qq.com)
 * @date:2017年8月15日 下午8:07:54
 */
public class ClassLoaderUtils {

	/**
	 * Constructor com.happgo.dlc.base.util.ClassLoaderUtils
	 */
	private ClassLoaderUtils() {
	}

	/**
	 * getDefaultClassLoader ClassLoader
	 */
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader defaultClassLoader = getContextClassLoader();
		if (defaultClassLoader == null) {
			defaultClassLoader = ClassLoaderUtils.class.getClassLoader();
		}
		if (defaultClassLoader == null) {
			throw new RuntimeException("get default classloader error");
		}
		return defaultClassLoader;
	}

	/**
	 * getContextClassLoader ClassLoader
	 */
	public static ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
}
