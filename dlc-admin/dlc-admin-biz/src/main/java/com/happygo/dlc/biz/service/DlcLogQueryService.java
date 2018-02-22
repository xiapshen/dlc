/**
* Copyright  2017
* 
* All  right  reserved.
*
* Created  on  2017年6月4日 上午9:21:11
*
* @Package com.happygo.dlc.biz  
* @Title: DlcLogQueryService.java
* @Description: DlcLogQueryService.java
* @author sxp (1378127237@qq.com) 
* @version 1.0.0 
*/
package com.happygo.dlc.biz.service;

import java.util.List;

import com.happgo.dlc.base.bean.DlcLog;
import com.happgo.dlc.base.bean.PageParam;

/**
 * ClassName:DlcLogQueryService
 *
 * @author sxp (1378127237@qq.com)
 * @Description: DlcLogQueryService.java
 * @date:2017年6月4日 上午9 :21:11
 */
public interface DlcLogQueryService {

	/**
	 * Log query list.
	 * @MethodName: logQuery
	 * @Description: the logQuery
	 * @param keyWord   the key word
	 * @param appName   the app name
	 * @param pageParam the page param
	 * @return List<List DlcLog>>
	 */
	List<List<DlcLog>> logQuery(String keyWord, String appName, PageParam pageParam);
	
	/**
	* @MethodName: getQueryConditions
	* @Description: the getQueryConditions
	* @param appName
	* @return List<String>
	*/
	List<String> getQueryConditions(String appName);

}
