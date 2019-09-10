package com.bian.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author bianpengfei
 * @create 2019-02-15 15:47
 **/

@SpringBootApplication
@EnableZuulProxy
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class ZuulGetWay9527_APP {

    public static void main(String[] args) {
        SpringApplication.run(ZuulGetWay9527_APP.class, args);
    }
}
