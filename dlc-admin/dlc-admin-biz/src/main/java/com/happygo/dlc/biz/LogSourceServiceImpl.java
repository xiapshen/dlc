/**
 * Copyright  2017
 * <p>
 * All  right  reserved.
 * <p>
 * Created  on  2017年7月30日 下午7:35:00
 *
 * @Package com.happygo.dlc.biz
 * @Title: LogSourceServiceImpl.java
 * @Description: LogSourceServiceImpl.java
 * @author sxp (1378127237@qq.com)
 * @version 1.0.0
 */
package com.happygo.dlc.biz;

import com.happgo.dlc.base.DlcConstants;
import com.happygo.dlc.biz.service.LogSourceService;
import com.happygo.dlc.common.entity.LogSource;
import com.happygo.dlc.dal.mybatis.mapper.LogSourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName:LogSourceServiceImpl
 * @Description: LogSourceServiceImpl.java
 * @author sxp (1378127237@qq.com) 
 * @date:2017年7月30日 下午7:35:00
 */
@Service
public class LogSourceServiceImpl implements LogSourceService {

    /**
     * LogSourceMapper the logSourceMapper 
     */
    @Autowired
    private transient LogSourceMapper logSourceMapper;


    /* (non-Javadoc)
     * @see com.happygo.dlc.biz.service.LogSourceService#selectList()
     */
    @Override
    public List<LogSource> selectList() {
        return logSourceMapper.selectList();
    }

    /* (non-Javadoc)
     * @see com.happygo.dlc.biz.service.LogSourceService#selectDefault(java.lang.String)
     */
    @Override
    public LogSource selectDefault(String selected) {
        return logSourceMapper.selectBy(selected);
    }

    /* (non-Javadoc)
     * @see com.happygo.dlc.biz.service.LogSourceService#deleteLogSource(int)
     */
    @Override
    public void deleteLogSource(int id) {
        logSourceMapper.deleteByPrimaryKey(id);
    }

    /* (non-Javadoc)
     * @see com.happygo.dlc.biz.service.LogSourceService#saveLogSource(com.happygo.dlc.common.entity.LogSource)
     */
    @Override
    public void saveLogSource(LogSource logSource) {
        if (!DlcConstants.DEFAULT.equals(logSource.getSelected())) {
            logSourceMapper.insert(logSource);
            return;
        }
        LogSource queryLogSource = logSourceMapper.selectBy(DlcConstants.DEFAULT);
        if (queryLogSource != null) {
            queryLogSource.setSelected("");
            logSourceMapper.updateByPrimaryKey(queryLogSource);
        }
        logSourceMapper.insert(logSource);
    }

	/* (non-Javadoc)
	 * @see com.happygo.dlc.biz.service.LogSourceService#updateLogSource(int)
	 */
	@Override
	public void updateLogSource(int id) {
        LogSource queryLogSource = logSourceMapper.selectBy(DlcConstants.DEFAULT);
        if (queryLogSource != null) {
            queryLogSource.setSelected("");
            logSourceMapper.updateByPrimaryKey(queryLogSource);
        }
		LogSource logSource = new LogSource();
		logSource.setId(id);
		logSource.setSelected(DlcConstants.DEFAULT);
		logSourceMapper.updateByPrimaryKey(logSource);
	}
}
