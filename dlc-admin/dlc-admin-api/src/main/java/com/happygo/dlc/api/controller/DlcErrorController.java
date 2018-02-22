/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年6月6日 下午3:06:34
 *
 * @Package com.happygo.dlc.api.controller  
 * @Title: DlcErrorController.java
 * @Description: DlcErrorController.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happygo.dlc.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * ClassName:DlcErrorController
 * 
 * @Description: DlcErrorController.java
 * @author sxp (1378127237@qq.com)
 * @date:2017年6月6日 下午3:06:34
 */
@Controller
@RequestMapping(value = "/dlc")
public class DlcErrorController {
	
	/**
	* @MethodName: handleError_404
	* @Description: the method handleError_404
	* @return ModelAndView
	*/
	@RequestMapping(value = "/error/404")
	public ModelAndView handleError_404() {
		return new ModelAndView("404");
	}

	/**
	* @MethodName: handleError_500
	* @Description: the method handleError_500
	* @return ModelAndView
	*/
	@RequestMapping(value = "/error/500")
	public ModelAndView handleError_500() {
		return new ModelAndView("500");
	}
}
