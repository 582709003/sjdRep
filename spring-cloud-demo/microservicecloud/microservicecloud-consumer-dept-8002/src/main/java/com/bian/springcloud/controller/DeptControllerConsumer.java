package com.bian.springcloud.controller;

import com.bian.springcloud.entities.Dept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author bianpengfei
 * @create 2019-02-13 3:42
 **/
@RestController
@Slf4j
public class DeptControllerConsumer {
    //RestTemplate访问Rest接口非常的简单粗暴无脑
    //（url，requestMap，ResponseBean.class）这个三个参数分别代表的是 REST请求地址、请求参数、HTTP相应转换被转换成的对象类型。

    @Autowired
    private RestTemplate restTemplate;

//    private static final String REST_URL_PREFIX = "http://localhost:8001";
    private static final String REST_URL_PREFIX = "http://MICROSERVICECLOUD-PROVIDER-DEPT";

    @RequestMapping(value="/consumer/dept/add")
    public boolean add(Dept dept) {
        return restTemplate.postForObject(REST_URL_PREFIX+ "/dept/add", dept, Boolean.class);
    }

    @RequestMapping(value="/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") long id) {
        System.out.println("进来了111");
        return restTemplate.getForObject(REST_URL_PREFIX+ "/dept/get/"+id, Dept.class);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value="/consumer/dept/findAll")
    public List<Dept> get() {
        return restTemplate.getForObject(REST_URL_PREFIX+ "/dept/get", List.class);
    }


    @RequestMapping(value="/consumer/dept/discovery")
    public Object discovery() {
        System.out.println("6666");
        return restTemplate.getForObject(REST_URL_PREFIX+"/dept/discovery", Object.class);
    }

}
