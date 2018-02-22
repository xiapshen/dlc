/**
* Copyright  2017
* 
* All  right  reserved.
*
* Created  on  2017年8月12日 下午8:16:13
*
* @Package com.happygo.dlc.biz  
* @Title: DlcSystemInfoServiceImpl.java
* @Description: DlcSystemInfoServiceImpl.java
* @author sxp (1378127237@qq.com) 
* @version 1.0.0 
*/
package com.happygo.dlc.biz;

import java.util.Properties;

import org.apache.ignite.Ignite;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.cluster.ClusterMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happygo.dlc.biz.service.DlcSystemInfoService;
import com.happygo.dlc.common.Version;
import com.happygo.dlc.common.entity.DlcSystemInfo;

/**
 * ClassName:DlcSystemInfoServiceImpl
 * @Description: DlcSystemInfoServiceImpl.java
 * @author sxp (1378127237@qq.com) 
 * @date:2017年8月12日 下午8:16:13
 */
@Service
public class DlcSystemInfoServiceImpl implements DlcSystemInfoService {
	
	/**
	 * Ignite the ignite 
	 */
	@Autowired
	private Ignite ignite;

	/* (non-Javadoc)
	 * @see com.happygo.dlc.biz.service.DlcSystemInfoService#getDlcSystemInfo()
	 */
	@Override
	public DlcSystemInfo getDlcSystemInfo() {
		DlcSystemInfo dlcSystemInfo = new DlcSystemInfo();
		// version
		dlcSystemInfo.setAppName("dlc-admin");
		dlcSystemInfo.setVersion(Version.VERSION);
		
		// jvm
		Properties props = System.getProperties();
		String jvm = props.getProperty("java.vm.name") + " " + props.getProperty("java.version");
		dlcSystemInfo.setJvm(jvm);
		String jvmVersion = props.getProperty("java.vm.version");
		dlcSystemInfo.setJvmVersion(jvmVersion);
		Runtime runtime = Runtime.getRuntime();
		String totalMemory = (runtime.totalMemory() / (1024 * 1024)) + "";
		dlcSystemInfo.setTotalMemory(totalMemory);
		String freeMemory = (runtime.freeMemory() / (1024 * 1024)) + "";
		dlcSystemInfo.setFreeMemory(freeMemory);
		
		// os
		String os = props.getProperty("os.name");
		dlcSystemInfo.setOs(os);
		String osVersion = props.getProperty("os.version");
		dlcSystemInfo.setOsVersion(osVersion);
		
		// ignite server nodes
		ClusterGroup serverClusterGrp = ignite.cluster().forServers();
		// Cluster group metrics.
		ClusterMetrics metrics = serverClusterGrp.metrics();
		// Get some metric values.
		String usedHeap = (metrics.getHeapMemoryUsed() / (1024 * 1024)) + "";
		String numberOfCores = metrics.getTotalCpus() + "";
		String activeJobs = metrics.getCurrentActiveJobs() + "";
		dlcSystemInfo.setUsedHeap(usedHeap);
		dlcSystemInfo.setNumberOfCores(numberOfCores);
		dlcSystemInfo.setActiveJobs(activeJobs);
		
		return dlcSystemInfo;
	}
}
