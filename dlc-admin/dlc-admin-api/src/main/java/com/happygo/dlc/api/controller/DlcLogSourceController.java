/**
 * Copyright  2017
 * <p>
 * All  right  reserved.
 * <p>
 * Created  on  2017年7月30日 下午7:35:00
 *
 * @Package com.happygo.dlc.api.controller
 * @Title: DlcLogSourceController.java
 * @Description: DlcLogSourceController.java
 * @author sxp (1378127237@qq.com)
 * @version 1.0.0
 */
package com.happygo.dlc.api.controller;

import com.happygo.dlc.biz.service.LogSourceService;
import com.happygo.dlc.common.entity.LogSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: DlcLogSourceController <br/>
 * Description: DlcLogSourceController <br/>
 * Date: 2017/7/30 10:36 <br/>
 *
 * @author sxp(sxp 1378127237@qq.com) <br/>
 * @version 1.0 <br/>
 */
@RestController
@RequestMapping("/dlc")
public class DlcLogSourceController {

    /**
     * LogSourceService the logSourceService 
     */
    @Autowired
    private LogSourceService logSourceService;

    /**
    * @MethodName: insert
    * @Description: the insert
    * @param logSource
    * @return ModelAndView
    */
    @PostMapping(value = "/logsource/insert")
    public ModelAndView insert(LogSource logSource) {
        logSourceService.saveLogSource(logSource);
        ModelAndView modelAndView = new ModelAndView("add_logsource");
        modelAndView.addObject("message", "success");
        return modelAndView;
    }

    /**
    * @MethodName: selectList
    * @Description: the selectList
    * @return ModelAndView
    */
    @GetMapping(value = "/logsource/select/list")
    public ModelAndView selectList() {
        ModelAndView modelAndView = new ModelAndView("logsource_list");
        List<LogSource> logSourceList = logSourceService.selectList();
        if (logSourceList == null) {
            modelAndView.addObject("logSourceList", new ArrayList<>(0));
        }
        modelAndView.addObject("logSourceList", logSourceList);
        modelAndView.addObject("message", "success");
        return modelAndView;
    }

    /**
    * @MethodName: deleteLogSource
    * @Description: the deleteLogSource
    * @param id
    * @return ModelAndView
    */
    @PostMapping(value = "/logsource/delete")
    public ModelAndView deleteLogSource(@RequestParam("id") int id) {
        logSourceService.deleteLogSource(id);
        return selectList();
    }
    
    /**
    * @MethodName: updateLogSource
    * @Description: the updateLogSource
    * @param id
    * @return ModelAndView
    */
    @PostMapping(value = "/logsource/update")
    public ModelAndView updateLogSource(@RequestParam("id") int id) {
        logSourceService.updateLogSource(id);
        return selectList();
    }
}
