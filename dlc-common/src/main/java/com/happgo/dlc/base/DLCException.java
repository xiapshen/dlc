/**
* Copyright  2017
* 
* All  right  reserved.
*
* Created  on  2017年5月30日 下午6:44:15
*
* @Package com.happgo.dlc.base  
* @Title: DLCException.java
* @Description: DLCException.java
* @author sxp (1378127237@qq.com) 
* @version 1.0.0 
*/
package com.happgo.dlc.base;

/**
 * ClassName:DLCException
 * @Description: DLCException.java
 * @author sxp (1378127237@qq.com) 
 * @date:2017年6月2日 下午9:35:36
 */
public class DLCException extends RuntimeException {
	
	/**
	 * long the serialVersionUID 
	 */
	private static final long serialVersionUID = -1366024128692739724L;
	
	/**
	 * String the message 
	 */
	private String message;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param message
	 */
	public DLCException(String message) {
		this(message, null);
		this.message = message;
	}
	
	/**
	 * @param message
	 */
	public DLCException(String message, Throwable e) {
		super(message, e);
		this.message = message;
	}
}
