package com.bian.springcloud.service.impl;

import com.bian.springcloud.dao.DeptDao;
import com.bian.springcloud.entities.Dept;
import com.bian.springcloud.service.deptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class deptServiceImp implements deptService{

	@Autowired
	private DeptDao deptDao;
	
	@Override
	public boolean addDept(Dept dept) {
		return deptDao.addDept(dept);
	}

	@Override
	public Dept findById(Long id) {
		return deptDao.findById(id);
	}

	@Override
	public List<Dept> findAll() {
		return deptDao.findAll();
	}

 
	
}
