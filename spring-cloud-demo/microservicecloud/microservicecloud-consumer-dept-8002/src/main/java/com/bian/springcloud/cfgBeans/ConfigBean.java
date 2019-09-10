package com.bian.springcloud.cfgBeans;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author bianpengfei
 * @create 2019-02-13 3:42
 **/
@Configuration
public class ConfigBean {  //boot -> spring -> applicationContext.xml -> @Configuration配置的 ConfigBean

    @Bean
    @LoadBalanced  // springCloud Ribbon基于Netflix Ribbon实现的一套客户端    负载均衡工具
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


//    @Bean
//    public IRule myRule() {
//        return new RandomRule(); // 达到的目的，用我们重新选择的随机算法替代默认的轮询
//    }
}
