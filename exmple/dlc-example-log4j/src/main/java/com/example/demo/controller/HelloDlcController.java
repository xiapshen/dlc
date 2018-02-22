package com.example.demo.controller;

import com.example.demo.service.HelloDlcService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: HelloDlcController <br/>
 * Description: HelloDlcController <br/>
 * Date: 2017/7/17 13:54 <br/>
 *
 * @author sxp(sxp 1378127237@qq.com)<br >
 * @version 1.0 <br/>
 */
@RestController
@RequestMapping("/dlc")
public class HelloDlcController {

    /**
     * The constant LOGGER.
     */
    private static final Logger LOGGER = LogManager.getLogger(HelloDlcController.class);

    /**
     * The Hello dlc service.
     */
    @Autowired
    private transient HelloDlcService helloDlcService;

    /**
     * Hello dlc.
     *
     * @param name the name
     * @return the string
     */
    @GetMapping(value = "/example/hello/{name}/{forNum}")
    public String helloDlc(@PathVariable("name") String name, @PathVariable("forNum") int forNum) {
        for (int i=0; i<forNum; i++) {
            LOGGER.info("|--------- 查询开始，入参：name[" + name + "] ---------|");
            String response = helloDlcService.helloDlc(name);
            LOGGER.info("|--------- 查询结束，返回结果Response：" + response + " ---------|");
        }
        return "helloDlc run end!";
    }
}
