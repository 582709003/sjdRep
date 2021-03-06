package com.bian.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author bianpengfei
 * @create 2019-02-13 16:22
 **/

@EnableEurekaServer  // eureka服务器启动类，接受其他微服务注册进来
@SpringBootApplication
public class EurekaServer7003_APP {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer7003_APP.class, args);
    }
}
