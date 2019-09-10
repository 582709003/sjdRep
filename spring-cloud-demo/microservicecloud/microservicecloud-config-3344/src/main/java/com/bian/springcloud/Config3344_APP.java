package com.bian.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author bianpengfei
 * @create 2019-02-15 19:33
 **/
@SpringBootApplication
@EnableConfigServer
public class Config3344_APP {
    public static void main(String[] args) {
        SpringApplication.run(Config3344_APP.class, args);
    }
}
