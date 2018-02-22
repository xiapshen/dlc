/**
* Copyright  2017
* 
* All  right  reserved.
*
* Created  on  2017年5月30日 下午6:44:15
*
* @Package com.happgo.dlc.base.util 
* @Title: Assert.java
* @Description: Assert.java
* @author sxp (1378127237@qq.com) 
* @version 1.0.0 
*/
package com.happgo.dlc.base.util;

import com.happgo.dlc.base.DLCException;

/**
 * ClassName:Assert
 * @Description: Assert.java
 * @author sxp (1378127237@qq.com) 
 * @date:2017年6月2日 下午9:36:11
 */
public final class Assert {
	/**
	 * Constructor com.happgo.dlc.base.Assert
	 */
	private Assert() {}
	
	/**
	* @MethodName: isNull
	* @Description: the isNull
	* @param obj
	*/
	public static void isNull(Object obj) {
		boolean isNull = (obj == null) ? true : false;
		if (isNull) {
			throw new DLCException("object is required");
		}
	}
}
