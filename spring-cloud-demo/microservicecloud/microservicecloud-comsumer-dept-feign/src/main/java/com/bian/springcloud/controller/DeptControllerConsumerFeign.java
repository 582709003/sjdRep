package com.bian.springcloud.controller;

import com.bian.springcloud.entities.Dept;
import com.bian.springcloud.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author bianpengfei
 * @create 2019-02-14 18:30
 **/
@RestController
public class DeptControllerConsumerFeign {

    @Autowired
    private DeptClientService deptClientService;

    @RequestMapping(value="/consumer/dept/add")
    public boolean add(Dept dept) {
        return deptClientService.add(dept);
    }

    @RequestMapping(value="/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") long id) {
        System.out.println("进来了111");
        return deptClientService.get(id);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value="/consumer/dept/findAll")
    public List<Dept> get() {
        return deptClientService.findAll();
    }

}
