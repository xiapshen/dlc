/**
 * Copyright  2017
 *
 * All  right  reserved.
 *
 * Created  on  2017年7月27日 下午18:56:22
 *
 * @Package LogSource
 * @Title: LogSource.java
 * @Description: LogSource.java
 * @author sxp (1378127237@qq.com)
 * @version 1.0.0
 */
package com.happygo.dlc.common.entity;

import lombok.Data;

/**
 * ClassName:LogSource
 * @Description: LogSource.java
 * @author sxp (1378127237@qq.com) 
 * @date:2017年7月30日 下午7:23:26
 */
@Data
public class LogSource {

    /**
     * int the id 
     */
    private int id;

    /**
     * String the appName 
     */
    private String appName;

    /**
     * String the selected 
     */
    private String selected;
}
