package com.bian.myRule;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bianpengfei
 * @create 2019-02-14 17:13
 **/

@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule(){
        return new MySelfRuleCon();
    }
}
