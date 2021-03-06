package com.bian.springcloud.service;

import com.bian.springcloud.entities.Dept;

import java.util.List;

public interface DeptService {

    boolean addDept(Dept dept);

    Dept findById(Long id);

    List<Dept> findAll();
	
}
