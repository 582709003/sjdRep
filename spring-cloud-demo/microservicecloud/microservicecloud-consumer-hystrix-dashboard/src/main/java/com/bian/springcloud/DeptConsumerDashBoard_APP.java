package com.bian.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author bianpengfei
 * @create 2019-02-15 14:32
 **/

@SpringBootApplication
@EnableHystrixDashboard   // 开启熔断监控
public class DeptConsumerDashBoard_APP {

    public static void main(String[] args) {
        SpringApplication.run(DeptConsumerDashBoard_APP.class, args);
    }
}
