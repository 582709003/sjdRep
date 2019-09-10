package com.bian.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient  // 服务启动后会自动注入到eureka容器中
@EnableDiscoveryClient // 服务发现
@SpringBootApplication
public class DeptProvider8003_App {
	
	public static void main(String[] args) {
		SpringApplication.run(DeptProvider8003_App.class, args);
	}
	
}
