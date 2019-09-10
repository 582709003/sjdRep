package com.sjd.test.controller;

import com.sjd.test.bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Auther sunjid
 * @Date 2019/8/28
 * @Date redis测试
 **/

@RestController
@RequestMapping("/redis/")
public class RedisController {

    @PostConstruct
    public void postConstruct(){
        System.out.println("PostConstruct");
    }

    @PreDestroy
    public void preDestroy (){
        System.out.println("PostConstruct");
    }

    @Autowired
    private Student s;

    @RequestMapping("test")
    public String test( String city){
//        stu.getCity();
        //Student a = new Student(null);
        s.getName();

        return city;
    }

}
