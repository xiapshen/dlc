/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年6月7日 上午10:01:44
 *
 * @Package com.happygo.dlc.common.entity  
 * @Title: DlcLogResult.java
 * @Description: DlcLogResult.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happygo.dlc.common.entity;

import com.happgo.dlc.base.bean.PageBean;

/**
 * ClassName:DlcLogResult
 * 
 * @Description: DlcLogResult.java
 * @author sxp (1378127237@qq.com)
 * @date:2017年6月7日 上午10:01:44
 */
public class DlcLogResult {
	
	/** 
	* The field searchTime
	*/
	private String searchTime;
	
	/** 
	* The field keyWord
	*/
	private String keyWord;
	
	/**
	 * PageBean<DlcLogBaseResult> the pageBean 
	 */
	private PageBean<DlcLogBaseResult> pageBean;

	/**
	 * @return the searchTime
	 */
	public String getSearchTime() {
		return searchTime;
	}

	/**
	 * @param searchTime the searchTime to set
	 */
	public void setSearchTime(String searchTime) {
		this.searchTime = searchTime;
	}

	/**
	 * @return the keyWord
	 */
	public String getKeyWord() {
		return keyWord;
	}

	/**
	 * @param keyWord the keyWord to set
	 */
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	/**
	 * @return the pageBean
	 */
	public PageBean<DlcLogBaseResult> getPageBean() {
		return pageBean;
	}

	/**
	 * @param pageBean the pageBean to set
	 */
	public void setPageBean(PageBean<DlcLogBaseResult> pageBean) {
		this.pageBean = pageBean;
	}
}
