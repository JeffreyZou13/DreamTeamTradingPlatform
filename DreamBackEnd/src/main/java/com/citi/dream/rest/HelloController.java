package com.citi.dream.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin
public class HelloController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public String getHello() {
        logger.info("Hello world info log");
        return "Hello world from REST";
    }
}
