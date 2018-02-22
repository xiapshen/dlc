/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年7月15日 下午7:11:53
 *
 * @Package com.happgo.dlc.base.bean  
 * @Title: PageParam.java
 * @Description: PageParam.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happgo.dlc.base.bean;

import java.io.Serializable;

/**
 * ClassName:PageParam
 * 
 * @Description: PageParam.java
 * @author sxp (1378127237@qq.com)
 * @date:2017年7月15日 下午7:11:53
 */
public class PageParam implements Serializable {
	/**
	 * long the serialVersionUID 
	 */
	private static final long serialVersionUID = -8170470517544238709L;

	/**
	 * 默认为第一页.
	 */
	public static final int DEFAULT_PAGE_NUM = 1;

	/**
	 * 默认每页记录数(15).
	 */
	public static final int DEFAULT_NUM_PER_PAGE = 15;

	// 当前页数
	private int pageNum = DEFAULT_PAGE_NUM;

	// 每页记录数
	private int numPerPage;

	/**
	 * 默认构造函数
	 */
	public PageParam() {
	}

	/**
	 * Constructor com.happgo.dlc.base.bean.PageParam
	 * @param pageNum
	 * @param numPerPage
	 * @param totalCount
	 * @param totalPage
	 */
	public PageParam(int pageNum, int numPerPage, int totalCount, int totalPage) {
		this.pageNum = pageNum;
		this.numPerPage = numPerPage;
	}

	/** 当前页数 */
	public int getPageNum() {
		return pageNum;
	}

	/** 当前页数 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/** 每页记录数 */
	public int getNumPerPage() {
		return numPerPage > 0 ? numPerPage : DEFAULT_NUM_PER_PAGE;
	}

	/** 每页记录数 */
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}
}
