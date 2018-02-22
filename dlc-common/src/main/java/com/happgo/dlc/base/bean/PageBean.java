/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年7月15日 下午7:10:07
 *
 * @Package com.happgo.dlc.base.bean  
 * @Title: PageBean.java
 * @Description: PageBean.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happgo.dlc.base.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:PageBean
 * 
 * @Description: PageBean.java
 * @author sxp (1378127237@qq.com)
 * @date:2017年7月15日 下午7:10:07
 */
public class PageBean<T> {
	// 当前页
	private int currentPage;

	// 每页显示多少条
	private int numPerPage;

	// 总记录数
	private int totalCount;

	// 本页的数据列表
	private List<T> recordList = new ArrayList<T>(0);

	// 总页数
	private int totalPage;

	/**
	 * Constructor com.happgo.dlc.base.bean.PageBean
	 */
	public PageBean() {
	}

	/**
	 * 计算总页数 .
	 * 
	 * @param totalCount
	 *            总记录数.
	 * @param numPerPage
	 *            每页记录数.
	 * @return totalPage 总页数.
	 */
	public static int countTotalPage(int totalCount, int numPerPage) {
		if (totalCount % numPerPage == 0) {
			// 刚好整除
			return totalCount / numPerPage;
		} else {
			// 不能整除则总页数为：商 + 1
			return totalCount / numPerPage + 1;
		}
	}

	/**
	 * 校验当前页数currentPage.<br/>
	 * 1、先根据总记录数totalCount和每页记录数numPerPage，计算出总页数totalPage.<br/>
	 * 2、判断页面提交过来的当前页数currentPage是否大于总页数totalPage，大于则返回totalPage.<br/>
	 * 3、判断currentPage是否小于1，小于则返回1.<br/>
	 * 4、其它则直接返回currentPage .
	 * 
	 * @param totalCount
	 *            要分页的总记录数 .
	 * @param numPerPage
	 *            每页记录数大小 .
	 * @param currentPage
	 *            输入的当前页数 .
	 * @return currentPage .
	 */
	public static int checkCurrentPage(int totalCount, int numPerPage,
			int currentPage) {
		int totalPage = PageBean.countTotalPage(totalCount, numPerPage); // 最大页数
		if (currentPage > totalPage) {
			// 如果页面提交过来的页数大于总页数，则将当前页设为总页数
			// 此时要求totalPage要大于获等于1
			if (totalPage < 1) {
				return 1;
			}
			return totalPage;
		} else if (currentPage < 1) {
			return 1; // 当前页不能小于1（避免页面输入不正确值）
		} else {
			return currentPage;
		}
	}

	/**
	 * 只接受前4个必要的属性，会自动的计算出其他3个属生的值
	 * 
	 * @param currentPage
	 * @param numPerPage
	 * @param totalCount
	 * @param recordList
	 */
	public PageBean(int currentPage, int numPerPage, int totalCount,
			List<T> recordList) {
		this.currentPage = currentPage;
		this.numPerPage = numPerPage;
		this.totalCount = totalCount;
		this.recordList = recordList;

		// 计算总页码
		totalPage = (totalCount + numPerPage - 1) / numPerPage;
	}

	public List<T> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<T> recordList) {
		this.recordList = recordList;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
