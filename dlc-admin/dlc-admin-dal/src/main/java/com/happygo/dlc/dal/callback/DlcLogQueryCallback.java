/**
* Copyright  2017
* 
* All  right  reserved.
*
* Created  on  2017年6月4日 上午10:12:30
*
* @Package com.happygo.dlc.dal.callback
* @Title: DlcLogQueryCallback.java
* @Description: DlcLogQueryCallback.java
* @author sxp (1378127237@qq.com) 
* @version 1.0.0 
*/
package com.happygo.dlc.dal.callback;

import java.util.List;

import org.apache.ignite.lang.IgniteCallable;
import org.apache.ignite.resources.ServiceResource;

import com.happgo.dlc.base.DlcConstants;
import com.happgo.dlc.base.bean.DlcLog;
import com.happgo.dlc.base.ignite.service.DlcIgniteService;

/**
 * ClassName:DlcLogQueryCallback
 *
 * @author sxp (1378127237@qq.com)
 * @Description: DlcLogQueryCallback.java
 * @date:2017年6月4日 上午10 :12:30
 */
public class DlcLogQueryCallback implements IgniteCallable<List<DlcLog>> {

	/**
	 * long the serialVersionUID 
	 */
	private static final long serialVersionUID = -1791890440195334801L;
	
	/**
	 * DlcIgniteService the dlcIgniteService 
	 */
	@ServiceResource(serviceName = DlcConstants.DLC_LOG_QUERY_SERVICE_NAME, 
			proxyInterface = DlcIgniteService.class)
	private DlcIgniteService dlcIgniteService;
	
	/**
	 * String the keyWord 
	 */
	private String keyWord;

	/**
	 * String the appName 
	 */
	private String appName;

	/**
	 * Constructor com.happygo.dlc.dal.callback.DlcLogQueryCallback
	 *
	 * @param keyWord   the key word
	 * @param appName   the app name
	 */
	public DlcLogQueryCallback(String keyWord, String appName) {
		this.keyWord = keyWord;
		this.appName = appName;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	public List<DlcLog> call() throws Exception {
		return dlcIgniteService.logQuery(keyWord, appName);
	}
}
