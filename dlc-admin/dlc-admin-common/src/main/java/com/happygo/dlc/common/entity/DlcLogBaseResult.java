/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年6月7日 上午9:45:26
 *
 * @Package com.happygo.dlc.common.entity  
 * @Title: DlcLogBaseResult.java
 * @Description: DlcLogBaseResult.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happygo.dlc.common.entity;

/**
 * ClassName:DlcLogBaseResult
 * 
 * @Description: DlcLogBaseResult.java
 * @author sxp (1378127237@qq.com)
 * @date:2017年6月7日 上午9:45:26
 */
public class DlcLogBaseResult {
	
	/** 
	* The field content
	*/
	private String content;
	
	/** 
	* The field logRecordTime
	*/
	private String logRecordTime;
	
	/** 
	* The field hostIP
	*/
	private String hostIP;
	
	/** 
	* The field systemName
	*/
	private String systemName;

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the logRecordTime
	 */
	public String getLogRecordTime() {
		return logRecordTime;
	}

	/**
	 * @param logRecordTime the logRecordTime to set
	 */
	public void setLogRecordTime(String logRecordTime) {
		this.logRecordTime = logRecordTime;
	}

	/**
	 * @return the hostIP
	 */
	public String getHostIP() {
		return hostIP;
	}

	/**
	 * @param hostIP the hostIP to set
	 */
	public void setHostIP(String hostIP) {
		this.hostIP = hostIP;
	}

	/**
	 * @return the systemName
	 */
	public String getSystemName() {
		return systemName;
	}

	/**
	 * @param systemName the systemName to set
	 */
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
}
