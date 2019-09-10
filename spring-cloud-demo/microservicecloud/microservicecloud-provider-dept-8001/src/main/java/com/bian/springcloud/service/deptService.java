package com.bian.springcloud.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bian.springcloud.entities.Dept;

public interface deptService {

    boolean addDept(Dept dept);

    Dept findById(Long id);

    List<Dept> findAll();
	
}
