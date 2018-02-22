/**
 * Copyright  2017
 * <p>
 * All  right  reserved.
 * <p>
 * Created  on  2017年6月4日 上午9:25:42
 *
 * @Package com.happygo.dlc.biz
 * @Title: DlcLogQueryServiceImpl.java
 * @Description: DlcLogQueryServiceImpl.java
 * @author sxp (1378127237@qq.com)
 * @version 1.0.0
 */
package com.happygo.dlc.biz;

import com.happgo.dlc.base.DLCException;
import com.happgo.dlc.base.DlcConstants;
import com.happgo.dlc.base.bean.DlcLog;
import com.happgo.dlc.base.bean.PageParam;
import com.happgo.dlc.base.ignite.service.DlcQueryConditionService;
import com.happgo.dlc.base.util.CollectionUtils;
import com.happygo.dlc.biz.service.DlcLogQueryService;
import com.happygo.dlc.dal.callback.DlcLogQueryCallback;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.cluster.ClusterGroupEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
/**
 * ClassName:DlcLogQueryServiceImpl
 * @Description: DlcLogQueryServiceImpl.java
 * @author sxp (1378127237@qq.com) 
 * @date:2017年7月13日 下午1:16:26
 */
@Service
public class DlcLogQueryServiceImpl implements DlcLogQueryService {

    /**
     * Logger the LOGGER
     */
    private static final Logger LOGGER = LogManager.getLogger(DlcLogQueryServiceImpl.class);

    /**
     * Ignite the ignite
     */
    @Autowired
    private Ignite ignite;

    /**
     * IgniteCache<String,Object> the igniteCache
     */
    private IgniteCache<String, Object> igniteCache;

    /**
     * Init ignite cache.
     *
     * @return void
     * @MethodName: initIgniteCache
     * @Description: the initIgniteCache
     */
    @PostConstruct
    public void initIgniteCache() {
        String cacheName = "dlcLogCache";
        igniteCache = ignite.getOrCreateCache(cacheName);
    }

    /* (non-Javadoc)
     * @see com.happygo.dlc.biz.service.DlcLogQueryService
     * @see #logQuery(java.lang.String, com.happgo.dlc.base.bean.PageParam)
     */
    public List<List<DlcLog>> logQuery(String keyWord, String appName, PageParam pageParam) {
        //1.根据key在IgniteCache查询是否有缓存，如果有直接返回，否则继续第二步
        String igniteCacheKey = keyWord + "@" + appName;
        List<List<DlcLog>> splitLogQueryDlcLogs = (List<List<DlcLog>>) igniteCache.get(igniteCacheKey);
        if (splitLogQueryDlcLogs != null && !splitLogQueryDlcLogs.isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("get keyword[" + keyWord + "] dlc log from ignite cache");
            }
            return splitLogQueryDlcLogs;
        }

        int partitionSize = pageParam.getNumPerPage();

        //2.根据keyword关键字匹配
        List<DlcLog> logQueryDlcLogs = broadcastLogQuery(keyWord, appName);
        splitLogQueryDlcLogs = splitLogAndPutInCache(igniteCacheKey, partitionSize, logQueryDlcLogs);
        return splitLogQueryDlcLogs;
    }

    /**
     * @MethodName: broadcastLogQuery
     * @Description: the broadcastLogQuery
     * @param keyWord
     * @return List<DlcLog>
     */
    private List<DlcLog> broadcastLogQuery(String keyWord, String appName) {
		try {
			ClusterGroup workers = ignite.cluster().forAttribute("ROLE", appName);
			Collection<List<DlcLog>> logQueryResults = ignite.compute(workers).broadcast(
					new DlcLogQueryCallback(keyWord, appName));
			if (logQueryResults == null) {
				return null;
			}
			List<DlcLog> logQueryDlcLogs = new ArrayList<DlcLog>();
            List<DlcLog> dlcLogs = null;
			for (Iterator<List<DlcLog>> it = logQueryResults.iterator(); it
					.hasNext(); ) {
                dlcLogs = it.next();
                if (dlcLogs == null || dlcLogs.isEmpty()) {
                    continue;
                }
				logQueryDlcLogs.addAll(dlcLogs);
			}
			return logQueryDlcLogs;
		} catch (ClusterGroupEmptyException e) {
			LOGGER.warn("Not find cluster nodes of appName:[" + appName + "]!");
			throw new DLCException("集群组【" + appName + "】未找到，请检查集群是否存在！");
		}
    }

    /**
     * @MethodName: splitLogAndPutInCache
     * @Description: the splitLogAndPutInCache
     * @param igniteCacheKey
     * @param partitionSize
     * @param logQueryDlcLogs
     * @return List<List<DlcLog>>
     */
    private List<List<DlcLog>> splitLogAndPutInCache(String igniteCacheKey, int partitionSize,
                                                     List<DlcLog> logQueryDlcLogs) {
        if (logQueryDlcLogs.isEmpty()) {
            return null;
        }
        List<List<DlcLog>> splitLogQueryDlcLogs = CollectionUtils.split(logQueryDlcLogs, partitionSize);
        boolean isSuccess = igniteCache.replace(igniteCacheKey, splitLogQueryDlcLogs);
        if (!isSuccess) {
            igniteCache.put(igniteCacheKey, splitLogQueryDlcLogs);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("keyword[" + igniteCacheKey + "] dlc log put ignite cache");
        }
        return splitLogQueryDlcLogs;
    }

	/* (non-Javadoc)
	 * @see com.happygo.dlc.biz.service.DlcLogQueryService#getQueryConditions(java.lang.String)
	 */
	@Override
	public List<String> getQueryConditions(final String appName) {
        String igniteCacheKey = appName;
		try {
            List<String> queryConditionList = (List<String>) igniteCache.get(igniteCacheKey);
            if (queryConditionList != null && !queryConditionList.isEmpty()) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("get appName '" + appName + "' query conditions cache from ignite cache");
                }
                return queryConditionList;
            }
			final ClusterGroup clsGroup = ignite.cluster().forAttribute("ROLE", appName);
            DlcQueryConditionService queryConditionService = ignite.services(clsGroup).
                    serviceProxy(appName + "#" + DlcConstants.DLC_LOG_QUERY_CONDITION_SERVICE_NAME,
                            DlcQueryConditionService.class, /*not-sticky*/false);
            queryConditionList = queryConditionService.getQueryConditions();
            boolean isSuccess = igniteCache.replace(igniteCacheKey, queryConditionList);
            if (!isSuccess) {
                igniteCache.put(igniteCacheKey, queryConditionList);
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("put appName '" + appName + "' query conditions cache in ignite cache");
            }
            return queryConditionList;
		} catch (ClusterGroupEmptyException ex) {
			LOGGER.warn("Not find cluster nodes of appName:[" + appName + "]!");
			throw new DLCException("集群组【" + appName + "】未找到，请检查集群是否存在！");
		} catch(IgniteException ex) {
			LOGGER.warn("Not find cluster nodes of appName:[" + appName + "]!");
			throw new DLCException("集群组【" + appName + "】未找到，请检查集群是否存在！");
		}
	}
}
