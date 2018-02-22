/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年6月5日 上午10:13:54
 *
 * @Package com.happygo.dlc.api  
 * @Title: DlcLogQueryController.java
 * @Description: DlcLogQueryController.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happygo.dlc.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.happgo.dlc.base.DLCException;
import com.happgo.dlc.base.DlcConstants;
import com.happgo.dlc.base.bean.DlcLog;
import com.happgo.dlc.base.bean.PageParam;
import com.happygo.dlc.biz.service.DlcLogQueryService;
import com.happygo.dlc.biz.service.LogSourceService;
import com.happygo.dlc.common.entity.DlcLogResult;
import com.happygo.dlc.common.entity.LogSource;
import com.happygo.dlc.common.entity.helper.DlcLogResultHelper;

/**
 * ClassName:DlcLogQueryController
 * 
 * @Description: DlcLogQueryController.java
 * @author sxp (1378127237@qq.com)
 * @date:2017年6月5日 上午10:13:54
 */
@RestController
@RequestMapping("/dlc")
public class DlcLogQueryController {
	
	/**
	 * Logger the LOGGER 
	 */
	private static final Logger LOGGER = LogManager.getLogger();
	
	/** 
	* The field dlcLogQueryService
	*/
	@Autowired
	private transient DlcLogQueryService dlcLogQueryService;

	/**
	 * LogSourceService the logSourceService 
	 */
	@Autowired
	private transient LogSourceService logSourceService;
	
	/**
	 * @MethodName: logQuery
	 * @Description: the method logQuery
	 * @param keyWord
	 * @return String
	 */
	@PostMapping(value = "/log/query")
	public ModelAndView logQuery(
			@RequestParam(value = "keyWord") String keyWord, PageParam pageParam) {
		LOGGER.info("^------- DLC 日志查询开始，keyWord:[" + keyWord + "] -------^");
		long startTime = System.currentTimeMillis();
		LogSource defaultLogSource = logSourceService.selectDefault(DlcConstants.DEFAULT);
		ModelAndView modelAndView = new ModelAndView("search_results");
		if (defaultLogSource == null) {
			modelAndView.addObject("message", "日志源未设置，请至主页 左侧菜单 >> 新增日志源！");
			buildWarnModleAndView(modelAndView, keyWord, pageParam);
            return modelAndView;
		}
		try {
			String appName = defaultLogSource.getAppName();
			List<List<DlcLog>> queryDlcLogs = dlcLogQueryService.logQuery(keyWord.trim(), appName, pageParam);
			long endTime = System.currentTimeMillis();
			long searchTime = endTime - startTime;
			DlcLogResult dlcLogResult = DlcLogResultHelper.buildDlcLogResult(
					keyWord, searchTime, queryDlcLogs, pageParam);
			List<String> queryConditions = dlcLogQueryService.getQueryConditions(appName);
			modelAndView.addObject("dlcLogResult", dlcLogResult);
			modelAndView.addObject("queryConditions", queryConditions);
		} catch(DLCException ex) {
	        buildWarnModleAndView(modelAndView, keyWord, pageParam);
			modelAndView.addObject("message", ex.getMessage());
		}
		LOGGER.info("^------- DLC 日志查询结束  -------^");
		return modelAndView;
	}
	
	/**
	* @MethodName: buildWarnModleAndView
	* @Description: the buildWarnModleAndView
	* @param modelAndView
	* @param keyWord
	* @param pageParam
	*/
	private void buildWarnModleAndView(ModelAndView modelAndView, String keyWord, PageParam pageParam) {
		DlcLogResult dlcLogResult = DlcLogResultHelper.buildDlcLogResult(
				keyWord, 0, null, pageParam);
		modelAndView.addObject("dlcLogResult", dlcLogResult);
		modelAndView.addObject("queryConditions", new ArrayList<>(0));
	}
}
