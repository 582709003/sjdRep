package com.bian.springcloud.controller;

import com.bian.springcloud.entities.Dept;
import com.bian.springcloud.service.deptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 卞鹏飞
 */
@RestController
public class DeptController {
	
	@Autowired
	private deptService deptService;

	@Autowired
	private DiscoveryClient client;
	
	@RequestMapping(value="/dept/add", method=RequestMethod.POST)
	public boolean add(@RequestBody Dept dept) {
		return deptService.addDept(dept);
	}
	
	
	@RequestMapping(value="/dept/get/{id}", method=RequestMethod.GET)
	public Dept get(@PathVariable("id") long id) {
		return deptService.findById(id);
	}
	
	@RequestMapping(value="/dept/get", method=RequestMethod.GET)
	public List<Dept> findAll() {
		return deptService.findAll();
	}

	@RequestMapping(value="/dept/discovery", method = RequestMethod.GET)
	public Object discovery() {
		List<String> list = client.getServices();
		System.out.println("*****************"+ list);

		List<ServiceInstance> servList = client.getInstances("microservicecloud-provider-dept-8001");
		for (ServiceInstance element : servList ) {
			System.out.println(element.getServiceId()+"\t"+element.getHost()+"\t"+element.getPort()+"\t"+element.getUri());
		}
		return this.client;
	}
}