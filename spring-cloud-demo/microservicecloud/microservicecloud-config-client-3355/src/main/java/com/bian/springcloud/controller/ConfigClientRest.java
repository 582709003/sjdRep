package com.bian.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bianpengfei
 * @create 2019-02-15 21:08
 **/
@RestController
public class ConfigClientRest {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${eureka.client.service-url.defaultZone}")
    private String eurekaServers;

    @Value("${server.port}")
    private String port;

    @RequestMapping("/config")
    private String getConfig() {
        String str = "applicationName: " + applicationName+ "\t eurekaServers:" +eurekaServers;
        System.out.println("*********str:"+ str);
        return "applicationName:" + "applicationName+"+"\t eurekaServers:" + eurekaServers;
    }

}
