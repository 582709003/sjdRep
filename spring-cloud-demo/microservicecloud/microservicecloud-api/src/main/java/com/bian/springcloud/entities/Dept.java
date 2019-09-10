package com.bian.springcloud.entities;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class Dept implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long deptNo;  // 主键
	
	private String deptName;	// 部门名称
	
	private String dbSource; // 来自哪个数据库，因为微服务架构可以一个服务对应着一个数据库，同一个信息被存储到不同的数据库
	
	
	
	public static void main(String[] args) {
		Dept dept = new Dept();
		dept.setDeptNo((long) 1);
	}
}
