/**
* Copyright  2017
* 
* All  right  reserved.
*
* Created  on  2017年6月6日 下午8:33:35
*
* @Package com.happygo.dlc.api.controller  
* @Title: HtmlHandleController.java
* @Description: HtmlHandleController.java
* @author sxp (1378127237@qq.com) 
* @version 1.0.0 
*/
package com.happygo.dlc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.happygo.dlc.biz.service.DlcSystemInfoService;
import com.happygo.dlc.common.entity.DlcSystemInfo;

/**
 * ClassName:HtmlHandleController
 *
 * @author sxp (1378127237@qq.com)
 * @Description: HtmlHandleController.java
 * @date:2017年6月6日 下午8 :33:36
 */
@Controller
@RequestMapping("/dlc")
public class HtmlHandleController {

	/**
	 * DlcSystemInfoService the dlcSystemInfoService
	 */
	@Autowired
	private DlcSystemInfoService dlcSystemInfoService;

	/**
	 * The Rest template.
	 */
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Index model and view.
	 *
	 * @return ModelAndView model and view
	 * @MethodName: index
	 * @Description: the index
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	/**
	 * Index v 1 model and view.
	 *
	 * @return ModelAndView model and view
	 * @MethodName: index_v1
	 * @Description: the index_v1
	 */
	@RequestMapping(value = "/index_v1")
	public ModelAndView index_v1() {
		DlcSystemInfo dlcSystemInfo = dlcSystemInfoService.getDlcSystemInfo();
		ModelAndView modelAndView = new ModelAndView("welcome");
		modelAndView.addObject("dlcSystemInfo", dlcSystemInfo);
		return modelAndView;
	}

	/**
	 * Add log source model and view.
	 *
	 * @return ModelAndView model and view
	 * @MethodName: addLogSource
	 * @Description: the addLogSource
	 */
	@RequestMapping(value = "/logsource/add")
	public ModelAndView addLogSource() {
		return new ModelAndView("add_logsource");
	}

	/**
	 * Shutdown string.
	 *
	 * @return the string
	 */
	@RequestMapping(value = "/service/{mode}")
	public ModelAndView service(@PathVariable("mode")String mode) {
		ModelAndView modelAndView = new ModelAndView("service");
		String resp = "shutdown".equals(mode) ?
				restTemplate.postForEntity("http://localhost:9090/shutdown", null, String.class).getBody() :
				restTemplate.postForEntity("http://localhost:9090/restart", null, String.class).getBody();
		modelAndView.addObject("message", resp);
		return modelAndView;
	}
}
