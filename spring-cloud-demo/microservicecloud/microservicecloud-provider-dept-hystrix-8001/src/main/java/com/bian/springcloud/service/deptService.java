package com.bian.springcloud.service;

import com.bian.springcloud.entities.Dept;

import java.util.List;

public interface deptService {

    boolean addDept(Dept dept);

    Dept findById(Long id);

    List<Dept> findAll();
	
}
