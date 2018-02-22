/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年8月13日 上午11:41:08
 *
 * @Package com.happygo.dlc.ignite.service  
 * @Title: DlcQueryConditionServiceImpl.java
 * @Description: DlcQueryConditionServiceImpl.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happygo.dlc.ignite.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ignite.services.Service;
import org.apache.ignite.services.ServiceContext;

import com.happgo.dlc.base.ignite.service.DlcQueryConditionService;
import com.happygo.dlc.logging.util.DlcLogUtils;

/**
 * ClassName:DlcQueryConditionServiceImpl
 * 
 * @Description: DlcQueryConditionServiceImpl.java
 * @author sxp (1378127237@qq.com)
 * @date:2017年8月13日 上午11:41:08
 */
public class DlcQueryConditionServiceImpl implements DlcQueryConditionService,
		Service {

	/**
	 * long the serialVersionUID
	 */
	private static final long serialVersionUID = 4057978072875039088L;

	/**
	 * The field LOGEER
	 */
	private static final Log LOGGER = LogFactory.getLog(DlcQueryConditionServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.happgo.dlc.base.ignite.service.DlcQueryConditionService#
	 * getQueryConditions()
	 */
	@Override
	public List<String> getQueryConditions() {
		return DlcLogUtils.getQueryConditionsFromAppender();
	}

	/**
	 * cancel
	 * 
	 * @param ctx
	 * @see org.apache.ignite.services.Service#cancel(org.apache.ignite.services.ServiceContext)
	 */
	public void cancel(ServiceContext ctx) {
		LOGGER.info("Service was cancel: " + ctx.name());
	}

	/**
	 * init
	 * 
	 * @param ctx
	 * @throws Exception
	 * @see org.apache.ignite.services.Service#init(org.apache.ignite.services.ServiceContext)
	 */
	public void init(ServiceContext ctx) throws Exception {
		LOGGER.info("Service was initialized: " + ctx.name());
	}

	/**
	 * execute
	 * 
	 * @param ctx
	 * @throws Exception
	 * @see org.apache.ignite.services.Service#execute(org.apache.ignite.services.ServiceContext)
	 */
	public void execute(ServiceContext ctx) throws Exception {
		LOGGER.info("Executing distributed service: " + ctx.name());
	}
}
