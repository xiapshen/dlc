/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年6月1日 下午3:10:14
 *
 * @Package com.happgo.dlc.base.ignite.service  
 * @Title: DlcIgniteService.java
 * @Description: DlcIgniteService.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happgo.dlc.base.ignite.service;

import java.util.List;

import com.happgo.dlc.base.bean.DlcLog;

/**
 * ClassName:DlcIgniteService
 *
 * @author sxp (1378127237@qq.com)
 * @Description: DlcIgniteService.java
 * @date:2017年6月1日 下午3 :10:14
 */
public interface DlcIgniteService {

	/**
	 * Log query list.
	 *
	 * @param keyWord   the key word
	 * @param appName   the app name
	 * @return List<DlcLog>  list
	 * @MethodName: logQuery
	 * @Description: the logQuery
	 */
	List<DlcLog> logQuery(String keyWord, String appName);

}
