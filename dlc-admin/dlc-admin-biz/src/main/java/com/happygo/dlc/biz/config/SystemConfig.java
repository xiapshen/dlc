/**
* Copyright  2017
* 
* All  right  reserved.
*
* Created  on  2017年7月16日 上午9:49:06
*
* @Package com.happygo.dlc.api.config  
* @Title: SystemConfig.java
* @Description: SystemConfig.java
* @author sxp (1378127237@qq.com) 
* @version 1.0.0 
*/
package com.happygo.dlc.biz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * ClassName:SystemConfig
 * @Description: SystemConfig.java
 * @author sxp (1378127237@qq.com) 
 * @date:2017年7月16日 上午9:49:06
 */
@Component
@PropertySource(value = "system.properties")
public class SystemConfig {
	
	/**
	 * int the cacheDuration 
	 */
	@Value("${dlc.web.ignite.cache.duration}")
	private int cacheDuration;

	/**
	 * @return the cacheDuration
	 */
	public int getCacheDuration() {
		return cacheDuration;
	}

	/**
	 * @param cacheDuration the cacheDuration to set
	 */
	public void setCacheDuration(int cacheDuration) {
		this.cacheDuration = cacheDuration;
	}
}
