package com.happygo.dlc.api.controller;

import com.happygo.dlc.common.entity.DlcHealthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

/**
 * ClassName: DlcHealthController <br/>
 * Description: DlcHealthController <br/>
 * Date: 2017/10/19 14:09 <br/>
 *
 * @version 1.0 <br/>
 */
@RestController
@RequestMapping("/dlc")
public class DlcHealthController {

    /**
     * The Rest template.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Health info model and view.
     *
     * @return the model and view
     */
    @GetMapping(value = "/health")
    public ModelAndView healthInfo() {
        ModelAndView modelAndView = new ModelAndView("health");
        DlcHealthInfo healthInfo = restTemplate.getForObject("http://localhost:9090/health", DlcHealthInfo.class);
        modelAndView.addObject("healthInfo", healthInfo);
        return modelAndView;
    }
}