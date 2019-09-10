package com.bian.springcloud.service;


import com.bian.springcloud.entities.Dept;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author bianpengfei
 * @create 2019-02-14 21:09
 **/
@Component // 这个千万不能丢
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService> {

    @Override
    public DeptClientService create(Throwable throwable) {
        return new DeptClientService() {
            @Override
            public boolean add(Dept dept) {
                return false;
            }

            @Override
            public Dept get(long id) {
                return new Dept().setDeptNo(id).setDeptName("该ID："+id+"没有对应的信息，Null--@HystrixCommand")
                        .setDbSource("No this database in MySQL");
            }

            @Override
            public List<Dept> findAll() {
                return null;
            }
        };
    }
}
