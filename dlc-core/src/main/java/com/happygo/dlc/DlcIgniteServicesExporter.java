/**
 * Copyright  2017
 * 
 * All  right  reserved.
 *
 * Created  on  2017年6月1日 下午3:25:32
 *
 * @Package com.happygo.dlc
 * @Title: DlcIgniteServicesExporter.java
 * @Description: DlcIgniteServicesExporter.java
 * @author sxp (1378127237@qq.com) 
 * @version 1.0.0 
 */
package com.happygo.dlc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteServices;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.services.Service;
import com.happgo.dlc.base.DLCException;
import com.happgo.dlc.base.DlcConstants;
import com.happygo.dlc.ignite.service.DlcQueryConditionServiceImpl;

/**
 * DlcIgniteServicesExporter
 * 
 * @Description: DlcIgniteServicesExporter.java
 * @author sxp (1378127237@qq.com)
 * @date:2017年6月1日 下午3:25:32
 */
public class DlcIgniteServicesExporter {

	/**
	 * The field service
	 */
	private Object service;

	/**
	 * The field mode
	 */
	private String mode;

	/**
	 * Ignite the ignite
	 */
	private static final Ignite ignite;
	
	/**
	 * The LOOGER
	 */
	private static final Log LOGGER = LogFactory.getLog(DlcIgniteServicesExporter.class);
	
	static {
		ignite = Ignition.start("config/dlc-ignite.xml");
	}

	/**
	 * @return the service
	 */
	public Object getService() {
		return service;
	}

	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(Object service) {
		this.service = service;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @MethodName: export
	 * @Description: the export
	 */
	public void export() {
		if (!Service.class.isAssignableFrom(service.getClass())) {
			throw new DLCException(
					"This ignite service is not 'Services' object");
		}
	
		IgniteServices svcs = ignite.services();
		switch (mode) {
		case DlcConstants.DEPLOY_CLUSTER_SINGLETON:
			svcs.deployClusterSingleton(
					DlcConstants.DLC_LOG_QUERY_SERVICE_NAME, (Service) service);
			break;

		case DlcConstants.DEPLOY_NODE_SINGLETON:
			svcs.deployNodeSingleton(DlcConstants.DLC_LOG_QUERY_SERVICE_NAME,
					(Service) service);
			break;

		default:
			throw new DLCException("The mode '" + mode + "' is not supported!");
		}

		String groupName = ignite.cluster().localNode().attribute("ROLE");
		ClusterGroup clusterGroup = ignite.cluster().forAttribute("ROLE", groupName);
		svcs = ignite.services(clusterGroup);
		// 发布查询条件标签服务集群单例
		svcs.deployClusterSingleton(groupName + "#" + DlcConstants.DLC_LOG_QUERY_CONDITION_SERVICE_NAME,
				new DlcQueryConditionServiceImpl());
		
		LOGGER.info("<<<=== Dlc ignite service deploy successfully ===>>>");
	}
}
