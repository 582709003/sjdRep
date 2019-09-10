package com.bian.springcloud;

import com.bian.myRule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author bianpengfei
 * @create 2019-02-13 4:16
 **/
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name="MICROSERVICECLOUD-PROVIDER-DEPT",configuration = MySelfRule.class)
public class DeptConsumer8002_App {

    public static void main(String[] args) {
        SpringApplication.run(DeptConsumer8002_App.class, args);
    }
}
