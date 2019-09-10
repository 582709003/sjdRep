package com.sjd.test.bean;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther sunjid
 * @Date 2019/9/2
 * @Date @value用法
 **/

@Component
public class Person {
    //@Value("${person.last-name}") // 从配置文件中获取值
    private String lastName;

    @Value("#{2*8}")  // spring表达式
    private Integer age;

    @Value("true") // boolean值
    private Boolean boss;
    private Date birth;

    private Map<String,Object> maps;
    private List<Object> lists;

    @Override
    public String toString() {
        return "Person{" +
                "lastName='" + lastName + '\'' +
                ", age=" + age +
                ", boss=" + boss +
                ", birth=" + birth +
                ", maps=" + maps +
                ", lists=" + lists +
                '}';
    }
}
