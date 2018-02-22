/**
 * Copyright  2017
 *
 * All  right  reserved.
 *
 * Created  on  2017年7月30日 下午7:38:32
 *
 * @Package com.happygo.dlc.dal.mybatis.mapper
 * @Title: LogSourceMapper.java
 * @Description: LogSourceMapper.java
 * @author sxp (1378127237@qq.com)
 * @version 1.0.0
 */
package com.happygo.dlc.dal.mybatis.mapper;

import com.happygo.dlc.common.entity.LogSource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName:LogSourceMapper
 * @Description: LogSourceMapper.java
 * @author sxp (1378127237@qq.com) 
 * @date:2017年7月30日 下午7:38:32
 */
@Mapper
public interface LogSourceMapper {

    /**
    * @MethodName: insert
    * @Description: the insert
    * @param entity
    */
    void insert(LogSource entity);

    /**
    * @MethodName: selectBy
    * @Description: the selectBy
    * @param selected
    * @return LogSource
    */
    LogSource selectBy(String selected);

    /**
    * @MethodName: deleteByPrimaryKey
    * @Description: the deleteByPrimaryKey
    * @param id
    */
    void deleteByPrimaryKey(int id);

    /**
    * @MethodName: selectList
    * @Description: the selectList
    * @return List<LogSource>
    */
    List<LogSource> selectList();

    /**
    * @MethodName: updateByPrimaryKey
    * @Description: the updateByPrimaryKey
    * @param logSource
    */
    void updateByPrimaryKey(LogSource logSource);
}
