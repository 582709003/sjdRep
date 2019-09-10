package com.sjd.test.configration;

import com.sjd.practise.bean.Student;
import com.sjd.practise.bean.WindowCondition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Auther sunjid
 * @Date 2019/9/1
 * @Date 配置类
 **/
@Configuration//这个注解里面就有@Component注解
//@ConfigurationProperties(prefix="connection")
//@PropertySource(value = "classpath:/application.properties")
public class Configration {
    @Bean
    //@Conditional(WindowCondition.class)//这个修改完需要重新启动服务器才能生效
    public Student student(){
        return new Student();
    }



}
