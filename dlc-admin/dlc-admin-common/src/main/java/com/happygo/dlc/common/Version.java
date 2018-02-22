/**
* Copyright  2017
* 
* All  right  reserved.
*
* Created  on  2017年8月12日 下午8:23:44
*
* @Package com.happygo.dlc.common  
* @Title: Version.java
* @Description: Version.java
* @author sxp (1378127237@qq.com) 
* @version 1.0.0 
*/
package com.happygo.dlc.common;

/**
 * ClassName:Version
 * @Description: Version.java
 * @author sxp (1378127237@qq.com) 
 * @date:2017年8月12日 下午8:23:44
 */
public class Version {
	
	/**
	 * String the VERSION 
	 */
	public static final String VERSION = Version.class.getPackage().getImplementationVersion();
	
	/**
	 * Constructor com.happygo.dlc.common.Version
	 */
	private Version() {}
}
