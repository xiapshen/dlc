/**
* Copyright  2017
* 
* All  right  reserved.
*
* Created  on  2017年6月2日 下午8:07:31
*
* @Package com.happgo.dlc.base.bean 
* @Title: DlcLog.java
* @Description: DlcLog.java
* @author sxp (1378127237@qq.com) 
* @version 1.0.0 
*/
package com.happgo.dlc.base.bean;

/**
 * ClassName:DlcLog
 * @Description: DlcLog.java
 * @author sxp (1378127237@qq.com) 
 * @date:2017年6月2日 下午8:07:31
 */
public class DlcLog {
	
	/**
	 * String the content 
	 */
	private String content;
	
	/**
	 * String the level 
	 */
	private String level;
	
	/**
	 * long the time 
	 */
	private long time;
	
	/**
	 * String the hostIp 
	 */
	private String hostIp;
	
	/**
	 * 
	 */
	private String systemName;

	/**
	 * Constructor com.happgo.dlc.base.DlcLog
	 * @param content
	 * @param level
	 * @param time
	 * @param hostIp
	 * @param systemName
	 */
	public DlcLog(String content, String level, long time, String hostIp, String systemName) {
		this.content = content;
		this.level = level;
		this.time = time;
		this.hostIp = hostIp;
		this.systemName = systemName;
	}

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
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the hostIp
	 */
	public String getHostIp() {
		return hostIp;
	}

	/**
	 * @param hostIp the hostIp to set
	 */
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
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
