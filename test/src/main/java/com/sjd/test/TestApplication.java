package com.sjd.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@MapperScan 扫描dao层的接口包
@EnableTransactionManagement
public class TestApplication extends SpringBootServletInitializer {

    //打war包的时候不要注释掉
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    //SpringBootServletInitializer的执行过程，简单来说就是通过SpringApplicationBuilder构建并封装SpringApplication对象，
    // 并最终调用SpringApplication的run方法的过程
    //为了打包springboot项目
//    @Override
//    protected SpringApplicationBuilder configure(
//            SpringApplicationBuilder builder) {
//        return builder.sources(this.getClass());
//    }
}
