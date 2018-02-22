/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年6月5日 下午1:59:47
 *
 * @Package com.happygo.dlc.api.config  
 * @Title: DefaultView.java
 * @Description: DefaultView.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happygo.dlc.api.config;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * ClassName:DefaultView
 * 
 * @Description: DefaultView.java
 * @author sxp (1378127237@qq.com)
 * @date:2017年6月5日 下午1:59:47
 */
public class DefaultView extends WebMvcConfigurerAdapter{

	/**
	 * addViewControllers
	 * 
	 * @param registry
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
	 * @see #addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry)
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName(
				"redirect:/dlc/index");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.addViewControllers(registry);
	}
}
