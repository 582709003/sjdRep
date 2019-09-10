package com.bian.springcloud.service;

import com.bian.springcloud.entities.Dept;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author bianpengfei
 * @create 2019-02-14 18:05
 **/
@FeignClient(name = "microservicecloud-provider-dept",fallbackFactory = DeptClientServiceFallbackFactory.class)
public interface DeptClientService {

    @RequestMapping(value="/dept/add", method= RequestMethod.POST)
    boolean add(Dept dept);

    @RequestMapping(value="/dept/get/{id}", method=RequestMethod.GET)
    Dept get(@PathVariable("id") long id);

    @RequestMapping(value="/dept/get", method=RequestMethod.GET)
    List<Dept> findAll();
}
