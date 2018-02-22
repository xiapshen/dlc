/**
* Copyright  2017
* 
* All  right  reserved.
*
* Created  on  2017年7月15日 下午7:27:52
*
* @Package com.happgo.dlc.base.util  
* @Title: PageUtils.java
* @Description: PageUtils.java
* @author sxp (1378127237@qq.com) 
* @version 1.0.0 
*/
package com.happgo.dlc.base.util;

import java.util.List;

import com.happgo.dlc.base.bean.PageBean;
import com.happgo.dlc.base.bean.PageParam;

/**
 * ClassName:PageUtils
 * @Description: PageUtils.java
 * @author sxp (1378127237@qq.com) 
 * @param <T>
 * @date:2017年7月15日 下午7:27:52
 */
public class PageUtils {
	
	/**
	 * Constructor com.happgo.dlc.base.util.PageUtils
	 */
	private PageUtils() {}
	
	/**
	* @MethodName: listPage
	* @Description: the listPage
	* @param totalCount
	* @param pageParam
	* @param recordList
	* @return PageBean<T>
	*/
	public static <T> PageBean<T> listPage(int totalCount, PageParam pageParam, List<T> recordList) {
		int currentPage = pageParam.getPageNum();
		int numPerPage = pageParam.getNumPerPage();
		return new PageBean<T>(currentPage, numPerPage, totalCount, recordList);
	}
}
