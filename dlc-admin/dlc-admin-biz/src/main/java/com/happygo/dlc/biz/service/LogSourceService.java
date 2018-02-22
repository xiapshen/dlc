/**
 * Copyright  2017
 * <p>
 * All  right  reserved.
 * <p>
 * Created  on  2017年7月30日 下午7:36:29
 *
 * @Package com.happygo.dlc.biz.service
 * @Title: LogSourceService.java
 * @Description: LogSourceService.java
 * @author sxp (1378127237@qq.com)
 * @version 1.0.0
 */
package com.happygo.dlc.biz.service;

import com.happygo.dlc.common.entity.LogSource;

import java.util.List;

/**
 * ClassName:LogSourceService
 * @Description: LogSourceService.java
 * @author sxp (1378127237@qq.com) 
 * @date:2017年7月30日 下午7:36:29
 */
public interface LogSourceService {

    /**
    * @MethodName: selectList
    * @Description: the selectList
    * @return List<LogSource>
    */
    List<LogSource> selectList();

    /**
    * @MethodName: selectDefault
    * @Description: the selectDefault
    * @param selected
    * @return LogSource
    */
    LogSource selectDefault(String selected);

    /**
    * @MethodName: deleteLogSource
    * @Description: the deleteLogSource
    * @param id
    */
    void deleteLogSource(int id);

    /**
    * @MethodName: saveLogSource
    * @Description: the saveLogSource
    * @param logSource
    */
    void saveLogSource(LogSource logSource);
    
    /**
    * @MethodName: updateLogSource
    * @Description: the updateLogSource
    * @param id
    */
    void updateLogSource(int id);
}
