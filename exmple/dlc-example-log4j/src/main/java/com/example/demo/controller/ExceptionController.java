package com.example.demo.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: ExceptionController <br/>
 * Description: ExceptionController <br/>
 * Date: 2017/7/17 15:25 <br/>
 *
 * @author sxp(sxp 1378127237@qq.com)<br/>
 * @version 1.0 <br/>
 */
@RestController
@RequestMapping("/dlc")
public class ExceptionController {
    /**
     * The constant LOGGER.
     */
    private static final Logger LOGGER = LogManager.getLogger(ExceptionController.class);

    /**
     * Exception.
     */
    @GetMapping(value = "/example/exception")
    public String exception() {
        try {
            throw new IllegalArgumentException("illegal param!");
        } catch (Exception e) {
            LOGGER.error("|-------- exception message:[" + e.getMessage() + "] --------|", e);
        }
        return "exception message:illegal param";
    }
}
