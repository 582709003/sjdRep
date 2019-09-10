

# SpringCloud学习

#一、前提

1、熟悉以前的学习内容和知识，SpringMVC+Spring/SpringBoot+Mybatis+Maven+git.....

2、SpringBoot大概技术21中技术之多，就像比如中国字典，没有一个人敢说能够完全精通

3、SpringCloud 五大神兽

> 服务注册与发现  eureka
>
> 负载均衡  Netflix ribbon
>
> 熔断器 Netflix Hystrix
>
> 服务配置  springCloudConfig
>
> 智能路由  zuul



# 二、面试题

1、什么是微服务？

通常而言，它是一种架构模式或者说是一种架构风格，它关注的是一个点，它提倡的是将单一运用程序划分成一组小的服务，每个服务运行在其独立的自己进程中，也可以有自己的独立的数据库。一般是根据业务进行拆分一个一个的服务。



2、什么是微服务架构？

微服务架构是一种架构模式，他提倡



3、dubbo和SpringCloud有什么区别？

dubbo是基于RPC风格的，而SpringCloud是基于RESTful 风格



4、微服务技术栈有哪些？

| 微服务条目                           | 落地技术                               |
| ------------------------------------ | -------------------------------------- |
| 服务开发                             | SpringBoot、Spring、SpringMVC          |
| 服务配置与管理                       | Netflix公司的Archaius、阿里的Diamond等 |
| 服务注册与发现                       | Eureka、Consul、Zookeeper等            |
| 服务调用                             | Rest、RPC、gRPC                        |
| 服务熔断器                           | Hystrix、Envoy等                       |
| 负载均衡                             | Ribbon、Nginx等                        |
| 服务接口（客户端调用服务的简化工具） | Feign等                                |
| 消息队列                             | Kafka、RabbitMQ、ActiveMQ等            |
| 服务配置中心管理                     | SpringCloudConfig、Chef等              |





# 三、SpringCloud入门概述

## 1、简介

SpringCloud，是基于SpringBoot提供了一整套微服务解决方案，**包括服务注册与发现、配置中心、负载均衡、全链路监控、服务网关、熔断器等组件**，除了基于NetFlix的开源组件做高度抽象封装之外，还有一些选型中立的开源组件。它是各个微服务架构落地技术的集合体，俗称微服务全家桶。



springCloud利用了SpringBoot的开发便利性巧妙的简化了分布式系统的基础设施的开发，SpringCloud为开发人员提供了快速构建分布式系统的一些工具，包括配置管理、服务发现、熔断器、路由、微代理、事件总线、全局锁、决策竞选、分布式会话等等，它们都可以用SpringBoot的开发风格做到一键启动和部署



## 2、SpringCloud和SpringBoot是什么关系？

springBoot专注于快速方便的开发单个个体微服务

SpringCloud是关注与全局的微服务协调整体治理框架，它将SpringBoot开发的一个个单体微服务整个并管理起来，为各个微服务之间提供，配置管理、服务发现、断路器、微代理、事件总线、全局锁、决策竞选、分布式会话等等集成服务

SpringBoot可以离开SpringCloud独立开发项目，但是**==SpringCloud离不开SpringBoot==**，属于依赖关系



## 3、SpringCloud和Dubbo区别？

**两个都是分布式架构解决方案**

|              | Dubbo         | SpringCloud                |
| ------------ | ------------- | -------------------------- |
| 服务注册中心 | Zookeeper     | SpringCloud Netflix Eureka |
| 服务调用方式 | RPC           | REST API                   |
| 服务监控     | Dubbo-monitor | Spring Boot Admin          |
| 断路器       | 不完善        | SpringCloud Hystrix        |
| 服务网关     | 无            | Spring Cloud Netflix Zuul  |
| 分布式配置   | 无            | Spring Cloud Config        |
| 服务跟踪     | 无            | Spring Cloud Sleuth        |
| 消息总线     | 无            | Spring Cloud Bus           |
| 数据流       | 无            | Spring Cloud Stream        |
| 批量任务     | 无            | Spring Cloud Task          |
| ...          | ...           | ....                       |

最大的区别：SpringCloud 抛弃了Dubbo的RPC通信，采用的是HTTP的REST方式

严格的来说，两种方式各有优劣，虽然从一定的程度来说，后者牺牲了服务调用的性能，但也避免了上面提到的原生RPC带来的问题。而且REST相比RPC更为灵活，服务提供和调用方的依赖只依靠一纸契约，不存在代码级别的强依赖，这在强调快速演化的微服务环境下，显得更加合适。

SpringCloud是微服务架构一站式解决方案

图示：dubbo-zookeeper

![](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/d01373f082025aaf111c708cfbedab64034f1a4e.jpg)



springCloud



## 4、创建SpringCloud工程（Eclipse版本）

它主要是由四个模块构成  

> 整体父工程	microservicecloud
>
> 公共子模块Module	microservicecloud-api
>
> 部门微服务提供者Module		microservicecloud-provider-dept-8001
>
> 部门微服务消费者Module        microservicecloud-consumer-dept-80	



1、先创建一个Work set 方便我们进行 视图观看

![1549966212602](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1549966212602.png)



2、创建一个父项目Maven工程： microservicecloud （整体父工程Project）

![1549966357830](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1549966357830.png)

修改里面的pom.xml文件，添加相关依赖

主要是定义pom文件，将后续的各个子模块公共的jar包等统一提取出来，类似一个抽象父类

~~~xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.bian.springcloud</groupId>
	<artifactId>microservicecloud</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>pom</packaging>
	
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<maven.complier.source>1.8</maven.complier.source>
		<maven.complier.target>1.8</maven.complier.target>
		<junit.version>4.12</junit.version>
		<log4j.version>1.2.17</log4j.version>
		<lombok.version>1.16.18</lombok.version>
	</properties>
	
	<dependencyManagement>
		<dependencies>
			<!-- 导入spring cloud 依赖 -->
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>Dalston.SR1</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
			
			<!-- 导入spring boot 依赖 -->
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-dependencies</artifactId>
				<version>1.5.9.RELEASE</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		
			<!-- 添加mysql驱动包  -->
			<dependency>
				<groupId>mysql</groupId>
				<artifactId>mysql-connector-java</artifactId>
				<version>5.0.4</version>
			</dependency>
			
			<!-- 添加阿里连接池 -->
		    <dependency>
				<groupId>com.alibaba</groupId>
				<artifactId>druid</artifactId>
				<version>1.0.31</version>
			</dependency>
			
			<!-- mybatis和springBoot整合  -->
			<dependency>
				<groupId>org.mybatis.spring.boot</groupId>
				<artifactId>mybatis-spring-boot-starter</artifactId>
				<version>1.3.2</version>
			</dependency>
			
			<!-- 日志 logback -->
			<dependency>
				<groupId>ch.qos.logback</groupId>
				<artifactId>logback-core</artifactId>
				<version>1.2.3</version>
			</dependency>
			
			<!-- 测试junit -->
			<dependency>
				<groupId>junit</groupId>
				<artifactId>junit</artifactId>
				<version>${junit.version}</version>
				<scope>test</scope>
			</dependency>
			
			<dependency>
				<groupId>log4j</groupId>
				<artifactId>log4j</artifactId>
				<version>${log4j.version}</version>
			</dependency>
		
		</dependencies>
	</dependencyManagement>
</project>
~~~

3、创建 公共子模块Module   microservicecloud-api

这时候要注意，创建不是maven.project了，而是maven.module了，看图：

![1549967016024](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1549967016024.png)



![1549967071812](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1549967071812.png)



![1549967111691](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1549967111691.png)



当我们的子模块创建完成后，父项目中pom文件会自动产生这一段

![1549967215984](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1549967215984.png)



修改 microservicecloud-api子模块的pom.xml文件：

~~~xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	
	<!--子类里面显示声明才能明确的继承表现，可以看成我们java中的继承多态性的感觉  -->
	<parent>
		<groupId>com.bian.springcloud</groupId>
		<artifactId>microservicecloud</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</parent>

	<!-- 当前的Module模块的坐标 -->
	<artifactId>microservicecloud-api</artifactId>
	
	<!-- 定义当前Module模块需要用到的jar包，按自己添加，如果父类已经包含了，可以不用写自己的版本号 -->
	<dependencies>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
		</dependency>
	</dependencies>
	

</project>
~~~



部门实体：Dept

```java
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
   
   private String deptName;   // 部门名称
   
   private String dbSource; // 来自哪个数据库，因为微服务架构可以一个服务对应着一个数据库，同一个信息被存储到不同的数据库
   
   
   
   public static void main(String[] args) {
      Dept dept = new Dept();
      dept.setDeptNo((long) 1);
   }
}
```



4、同理，创建部门微服务提供者Module     microservicecloud-provider-dept-8001 

约定 > 配置 > 编码

和上面步骤一样，

pom.xml文件

~~~xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>com.bian.springcloud</groupId>
		<artifactId>microservicecloud</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</parent>

	<artifactId>microservicecloud-provider-dept-8001</artifactId>


	<!--引入相关的依赖 -->
	<dependencies>
		<!-- 引入自己定义的api通用包，可以使用Dept部门Entity -->
		<dependency>
			<groupId>com.bian.springcloud</groupId>
			<artifactId>microservicecloud-api</artifactId>
			<version>${project.version}</version>
		</dependency>

		<!-- 引入父pom文件中的junit -->
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
		</dependency>

		<!-- 引入父pom文件中的mysql -->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
		</dependency>

		<!-- 引入父pom文件中的阿里连接池 -->
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>druid</artifactId>
		</dependency>



		<!-- 引入父pom文件中的logback -->
		<dependency>
			<groupId>ch.qos.logback</groupId>
			<artifactId>logback-core</artifactId>
		</dependency>

		<!-- 引入父pom文件中mybatis-spring-boot-starter -->
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
		</dependency>


		<!-- 引入jetty -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jetty</artifactId>
		</dependency>

		<!-- 引入springboot-web场景启动器 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<!-- 引入测试场景启动器 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
		</dependency>

		<!-- 修改后立即生效，热部署 -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>springloaded</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
		</dependency>


	</dependencies>
</project>
~~~



application.properties

~~~properties
server.port=8001


# 配置mybatis配置文件所在的路径
#mybatis.config-location=classpath:mybatis/mybatisConfig.xml

# 设置所有Entities别名类所在的包
mybatis.type-aliases-package=com.bian.springcloud.entities

mybatis.configuration.cache-enabled=true

mybatis.configuration.map-underscore-to-camel-case=true

# mapper映射文件
mybatis.mapper-locations=classpath:mybatis/mapper/**.xml

logging.level.com.bian.springcloud.dao=debug

# 设置应用程序名字（其实就是我们对外暴露的微服务名字）
spring.application.name=microservicecloud-provider-dept-8001

# 数据库连接名字
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://192.168.1.5:3306/cloud01?useUnicode=true&characterEncoding=utf-8&useSSL=false
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource

# 数据库连接池的最小维持连接数
spring.datasource.dbcp2.min-idle=5

# 数据库连接池的最大维持连接数
spring.datasource.dbcp2.max-idle=5

# 数据库连接池的初始化连接数
spring.datasource.dbcp2.initial-size=5

# 数据库连接池等待连接获取最大的超时时间
spring.datasource.dbcp2.max-wait-millis=5000

~~~

dao层：

```java
package com.bian.springcloud.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bian.springcloud.entities.Dept;

@Mapper
public interface DeptDao {

    boolean addDept(Dept dept);

    Dept findById(Long id);

    List<Dept> findAll();
   
}
```



deptMapper.xml:

```java
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.bian.springcloud.dao.DeptDao">

    <select id="findById" resultType="com.bian.springcloud.entities.Dept">
        select dept_no,dept_name,db_source from dept where dept_no=#{id};
    </select>
    
    <select id="findAll" resultType="com.bian.springcloud.entities.Dept">
        select dept_no,dept_name,db_source from dept;
    </select>
    
    <insert id="addDept" >
        INSERT INTO dept(dept_name,db_source) VALUES(#{deptName},DATABASE());
    </insert>
    
</mapper>
```



service层：

```java
package com.bian.springcloud.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bian.springcloud.entities.Dept;

public interface deptService {

    boolean addDept(Dept dept);

    Dept findById(Long id);

    List<Dept> findAll();
   
}
```



service实现层：

```java
package com.bian.springcloud.service.impl;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Service;

import com.bian.springcloud.dao.DeptDao;
import com.bian.springcloud.entities.Dept;
import com.bian.springcloud.service.deptService;

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
```



controller层：

```java
package com.bian.springcloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bian.springcloud.entities.Dept;
import com.bian.springcloud.service.deptService;

@RestController
public class DeptController {
   
   @Autowired
   private deptService deptService;
   
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
}
```





5、创建部门微服务消费者Module     microservicecloudconsumer-dept-8002

RestTemplate访问Rest接口非常的简单粗暴无脑

（url，requestMap，ResponseBean.class）这个三个参数分别代表的是 REST请求地址、请求参数、HTTP相应转换被转换成的对象类型。

pom.xml文件：

~~~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud</artifactId>
        <groupId>com.bian.springcloud</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-consumer-dept-8002</artifactId>
    <description>部门微服务的消费者</description>

    <dependencies>
        <dependency>
            <groupId>com.bian.springcloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>${project.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!--修改后立即去生效-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>springloaded</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
    </dependencies>
</project>
~~~~~



controller：

```java
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

    private static final String REST_URL_PREFIX = "http://localhost:8001";

    @RequestMapping(value="/consumer/dept/add")
    public boolean add(Dept dept) {
        return restTemplate.postForObject(REST_URL_PREFIX+ "/dept/add", dept, Boolean.class);
    }

    @RequestMapping(value="/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") long id) {
        System.out.println("进来了");
        return restTemplate.getForObject(REST_URL_PREFIX+ "/dept/get/"+id, Dept.class);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value="/consumer/dept/findAll")
    public List<Dept> get() {
        return restTemplate.getForObject(REST_URL_PREFIX+ "/dept/get", List.class);
    }


}
```



config：

```java
package com.bian.springcloud.cfgBeans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author bianpengfei
 * @create 2019-02-13 3:42
 **/
@Configuration
public class ConfigBean {  //boot -> spring -> applicationContext.xml -> @Configuration配置的 ConfigBean

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
```





# 四、服务的注册与发现（IDEA）

Eureka包含两个组件： Eureka Server 和 Eureka Client

>eureka服务注册中心Module   microservicecloud-eureka-7001
>
>

NetFlix在设计Eureka是遵守的就是AP原则

Zookeeper保证 CP 原则

RDBMS（mysql/oracle/sqlserver）  ====> ACID 原子性、一致性、隔离性、持久性

NOSQL (redis/mongdb)  ====> CAP  强一致性、高可用性、分区容错性





关于CAP理论，可以去看看阮一峰的文章[<http://www.ruanyifeng.com/blog/2018/07/cap.html>]
C(一致性)A(可用性)P(分区容错性)

ZooKeeper：
zookeeper保证了cp(一致性、分区容错性)，但是作为服务注册中心，我们可以容忍注册中心返回的是几分钟以前的注册信息。但是服务中心却必须保证可用性，
即服务注册中心对于高可用性的需求高于一致性。对于可用性，zookeeper有一个leader选举方案。当master主节点宕机与其他节点失去联系时，其他节点会重
新进行Leader选举，选出新的master节点。然而选举耗时过长，一般为30~120S，并且整个选举期间，整个zookeeper集群是无法使用的。

Eureka：
eureka保证了ap(可用性、分区容错性)，eureka每一个节点都是平等的，几个节点宕机不会影响正常节点的工作。剩余的正常节点依旧可以提供服务注册和查询。
并且，当客户端向某节点注册服务时，注册失败或者超时，则会自动切换到其他节点。只要有一台eureka节点还正常工作，就能保证注册服务的可用。但是对于服
务信息的同步则不能保证一致性(不能保证强一致性，但是最终一致)。除此之外，Eureka还有一种自我保护机制，如果在15分钟内85%的节点都没有正常心跳(不可用)
那么Eureka就认为客户端与注册中心之间出现了网络故障，此时会出现以下几种情况：
1、Eureka不再从注册列表中移除因为长时间没收到心跳而应该过期的服务
2、Eureka仍然能够接收新服务的注册和查询请求，但是不会被同步到其他节点上(保证当前节点的可用性)
3、当网络稳定后，当前实例新注册的服务会被同步到其他节点

因此,Eureka能够保证注册中心的高可用性，而不会像zookeeper一样直接集群瘫痪



一般来说，我需要引入cloud中的一个新技术组件

基本上是有两步骤

1、 新增一个相关的maven

~~~XML
	<!--eureka-server服务端-->
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifacted>spring-cloud-starter-eureka-server</artifacted>
	</dependency>

~~~



2、 在启动类上添加该组件技术相关的注解标签

3、java业务逻辑编码



开始创建工程

## 1、eureka服务注册中心 Module 

 microservicecloud-eureka-7001 

pom.xml文件

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud</artifactId>
        <groupId>com.bian.springcloud</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-eureka-7001</artifactId>
    <description>eureka服务注册中心Module</description>

    <dependencies>
        <!--注入 eureka 依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka-server</artifactId>
        </dependency>

        <!--修改后立即生效，热部署-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>springloaded</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>


    </dependencies>
</project>
~~~



application.xml文件

~~~properties
server.port=7001

spring.application.name=microservicecloud-eureka-7001

# eureka服务端实例的名字
eureka.instance.hostname=localhost

# false 表示自己就是注册中心，我的职责就是维护服务实例并不需要去检索服务
eureka.client.fetch-registry=false

# flase 表示不向注册中心注册自己，表示我自己注册中心
eureka.client.register-with-eureka=false

# 设置与Eureka server 交互的地址查询服务和注册服务，对外暴露的eureka地址
eureka.client.service-url.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/


~~~



启动类上：添加注解**==@EnableEurekaServer==** 

~~~java
package com.bian.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author bianpengfei
 * @create 2019-02-13 16:22
 **/

@EnableEurekaServer  // eureka服务器启动类，接受其他微服务注册进来
@SpringBootApplication
public class EurekaServer7001_APP {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer7001_APP.class, args);
    }
}

~~~

启动测试：

![1550048895174](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550048895174.png)



成功！



## 2、将已有的部门微服务注册进入eureka服务中心

microservicecloud-provider-dept-8001

修改项目部门微服务提供者  microservicecloud-provider-dept-8001里面pom文件

~~~xml
		<!--将微服务provider注册到eureka中，这个表示client-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
		</dependency>

~~~

application.properites

~~~properties
server.port=8001


# 配置mybatis配置文件所在的路径
#mybatis.config-location=classpath:mybatis/mybatisConfig.xml

# 设置所有Entities别名类所在的包
mybatis.type-aliases-package=com.bian.springcloud.entities

mybatis.configuration.cache-enabled=true

mybatis.configuration.map-underscore-to-camel-case=true

# mapper映射文件
mybatis.mapper-locations=classpath:mybatis/mapper/**.xml

logging.level.com.bian.springcloud.dao=debug

# 设置应用程序名字（其实就是我们对外暴露的微服务名字）
spring.application.name=microservicecloud-provider-dept-8001

# 数据库连接名字
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://192.168.1.5:3306/cloud01?useUnicode=true&characterEncoding=utf-8&useSSL=false
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource

# 数据库连接池的最小维持连接数
spring.datasource.dbcp2.min-idle=5

# 数据库连接池的最大维持连接数
spring.datasource.dbcp2.max-idle=5

# 数据库连接池的初始化连接数
spring.datasource.dbcp2.initial-size=5

# 数据库连接池等待连接获取最大的超时时间
spring.datasource.dbcp2.max-wait-millis=5000

# true 表示显示ip地址
eureka.instance.prefer-ip-address=true

# 这个是自己自定义ip访问地址
#eureka.instance.ip-address=192.168.1.12

# 自定义服务名称实例化名字
#eureka.instance.instance-id=hello
        
# 就是将客户端注册进入eureka服务列表内
eureka.client.service-url.defaultZone=http://localhost:7001/eureka/
~~~



入口类：

~~~java
package com.bian.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient  // 服务启动后会自动注入到eureka容器中
@SpringBootApplication
public class DeptProvider8001_App {
	
	public static void main(String[] args) {
		SpringApplication.run(DeptProvider8001_App.class, args);
	}
	
}

~~~



## 3、eureka自我保护

故障演示：

![1550051383095](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550051383095.png)

### 1）主机名称：服务名称修改

![1550052523576](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550052523576.png)

测试结果：

![1550052585855](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550052585855.png)



### 2） 访问信息有IP信息提示

![1550053128005](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550053128005.png)



### 3）	微服务info内容详细信息

示范：

![1550053273991](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550053273991.png)

​	1、修改microservicecloud-provider-dept-8001  pom文件 添加监控信息依赖actuator

```xml
<!--添加监控管理的依赖actuator,监控信息完善-->
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```



​	2、总的父工程microservicecloud修改pom.xml文件添加构建build信息

~~~xml
	<!--表示扫描src/main/resources下文件中的出现$$结尾的占位符，并进行解析-->
	<build>
		<finalName>microservicecloud</finalName>
		<resources>
			<resource>
				<directory>src/main/resources</directory>
				<filtering>true</filtering>
			</resource>
		</resources>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-resources-plugin</artifactId>
				<configuration>
					<delimiters>
						<delimiter>$</delimiter>
					</delimiters>
				</configuration>
			</plugin>
		</plugins>
	</build>

~~~



​	3、修改microservicecloud-provider-dept-8001中的applicaton.properties

~~~properties
info.app.name=bian-microservicecloud
info.company.name=www.bian.com

# $ $  自动会扫描带$开头和结尾
info.build.artifactId=$project.artifactId$   
info.build.version=$project.version$

~~~

​	

### 	4）eureka自我保护机制原因

​	好死不如赖活着

​	意思： 一句话，某个时刻一个微服务不可用了，eureka不会立刻清理，依旧会对该服务的信息进行保存。

​	默认情况下，如果EurekaServer在一定的时间内没有接收到某个微服务实例的心跳，EurekaServer将会注销该实例（默认是90秒），但是当在网络分区故障发生时，微服务与EurekaServer之间无法正常的通信，以上行为可能变得非常危险了——因为微服务本身其实就是健康的，==此时本不应该注销该微服务==。Eureka通过“自我保护模式”来解决这个问题——当EurekaServer节点在短时间内丢失过多客户端时（可能发生了网络分区故障），那么这个节点就会进入自我保护模式。一旦进入该模式，EurekaServer就会保护注册表中的信息，不再删除服务注册表中的数据（也就是不会注销任何微服务）。当网络故障恢复后，该Eureka Server节点会自动退出自我保护模式。

​	

​	==在自我保护模式中，Eureka Server会保护服务注册中的信息，不再注销任何服务实例，当它收到的心跳重新恢复到阈值以上时，该Eureka Server节点会自动退出自我保护模式。它的设计哲学就是宁可保留错误的服务注册信息，也不盲目注销任何健康的服务实例。所以一句话，好死不如耐活。==



在springCloud中，可以通过eureka.server.enable-self-preservation=false 禁用自我保护模式

​	

## 4、 服务发现Discovery

部门消费微服务Module    microservicecloud-provider-dept-8001

1、修改microservicecloud-provider-dept-8001中的DeptController

```java
package com.bian.springcloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bian.springcloud.entities.Dept;
import com.bian.springcloud.service.deptService;

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

		List<ServiceInstance> servList = client.getInstances("MICROSERVICECLOUD-PROVIDER-DEPT-8001");
		for (ServiceInstance element : servList ) {
			System.out.println(element.getServiceId()+"\t"+element.getHost()+"\t"+element.getPort()+"\t"+element.getUri());
		}
		return this.client;
	}
}
```



程序入口：添加注解@EnableDiscoveryClient

```java
@EnableEurekaClient  // 服务启动后会自动注入到eureka容器中
@EnableDiscoveryClient // 服务发现
@SpringBootApplication
public class DeptProvider8001_App {
   
   public static void main(String[] args) {
      SpringApplication.run(DeptProvider8001_App.class, args);
   }
   
}
```



测试结果：

![1550059026193](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550059026193.png)	



自娱自乐成功！但是我们要想让consumer层 能够访问provider，这时候需要我们自己测试一下

在DeptControllerConsumer中添加代码：

~~~java
   @RequestMapping(value="/consumer/dept/discovery")
    public Object discovery() {
        return restTemplate.getForObject(REST_URL_PREFIX+"/dept/discovery", Object.class);
    }

~~~

测试结果如下：

![1550060469471](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550060469471.png)



# 五、eureka集群配置

1、寻找C:\Windows\System32\drivers\etc路径下的HOSTS文件

![1550061352097](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550061352097.png)



2、域名映射

在HOSTS文件中添加

~~~properties
127.0.0.1   eureka7001.com
127.0.0.1   eureka7002.com
127.0.0.1   eureka7003.com
~~~



3、application.properties配置

```properties
server.port=7001

spring.application.name=microservicecloud-eureka-7001

# eureka服务端实例的名字
eureka.instance.hostname=eureka7001.com

# false 表示自己就是注册中心，我的职责就是维护服务实例并不需要去检索服务
eureka.client.fetch-registry=false

# flase 表示不向注册中心注册自己，表示我自己注册中心
eureka.client.register-with-eureka=false

# 设置与Eureka server 交互的地址查询服务和注册服务，对外暴露的eureka地址
# 单机版
#eureka.client.service-url.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/

# 集群
eureka.client.service-url.defaultZone=http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/
```

4、修改microservicecloud-provider-dept-8001中的application.properties

```properties
# 就是将客户端注册进入eureka服务列表内
eureka.client.service-url.defaultZone=http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/
```

5、结果：

![1550068668980](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550068668980.png)





![1550068717087](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550068717087.png)



![1550068739505](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550068739505.png)





# 六、负载均衡Ribbon

码神》码龙》码工》码农》码畜》 码渣

面试高大上，面试造飞机，工作拧螺丝

Spring Cloud Ribbon是基于NetFlix Ribbon实现的一套 **==客户端		负载均衡的工具==**

简单的来说，Ribbo是NetFlix发布的开源的项目，主要的功能实提供==客户端的软件负载均衡的算法==，将NetFlix的中间服务连接在一起，Ribbo的客户端提供了一系列的完善的配置项如连接超时，重试等。简单的来说，就是在配置文件中列出的Load Balance（简称LB）后面所有的机器，Ribbon会自动的帮助你基于某种规则（如简单轮询，随机连接等）去连接这些机器，我们也很容易使用Ribbon实现自定义的负载均衡算法。



LB，即负载均衡（Load Balance）,在微服务或者分布式集群中经常用到的一种应用

负载均衡简单的来说就是将用户的请求平摊的分配到多个服务器上，从而达到系统的HA

常见的负载均衡的软件Nginx、LVS、硬件F5等

相应的中间件，例如：dubbo和SpringCloud中均给我们提供了负载均衡，==SpringCloud的负载均衡算法可以自定义。==



集中式的LB  偏硬件

进程式的LB  偏软件  



## 1、Ribbon的初步配置

注意Ribbon是给客户单提供软件的负载均衡算法，所以我们要给客户端pom.xml文件添加Ribbon相关依赖，比如是consumer层

```xml
<!--引入Ribbon相关的依赖-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-ribbon</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

修改Application.properties文件

```properties
server.port=8002

spring.application.name=microservicecloudconsumer-dept-8002

spring.output.ansi.enabled=always

eureka.client.register-with-eureka=false
eureka.client.service-url.defaultZone=http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/
```



添加注解@LoadBalance

```java
/**
 * @author bianpengfei
 * @create 2019-02-13 3:42
 **/
@Configuration
public class ConfigBean {  //boot -> spring -> applicationContext.xml -> @Configuration配置的 ConfigBean

    @Bean
    @LoadBalanced  // springCloud Ribbon基于Netflix Ribbon实现的一套客户端    负载均衡工具
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
```



将我们之前的在DeptController中修改：

```java
//    private static final String REST_URL_PREFIX = "http://localhost:8001";

// 我们不在需要去主动书写具体地址了，我们只需要服务名对应即可
private static final String REST_URL_PREFIX = "http://MICROSERVICECLOUD-PROVIDER-DEPT-8001";
```



测试结果:

![1550068921760](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550068921760.png)

成功！



## 2、Ribbon工作的原理

Ribbon在工作的时候分为两步：

第一步选择EurekaServer，它优先在同一个区域内负载较少的server

第二步再根据用户指定的策略，在从server取到的服务注册中选择一个地址



我们检测一下 负载均衡是什么样子的

1、再多创建两个 部门提供微服务Module	microservicecloud-provider-8003、microservicecloud-provider-8004，并修改各自的pom文件和applicaiton.properties

pom.xml

```properties
server.port=8003


# 配置mybatis配置文件所在的路径
#mybatis.config-location=classpath:mybatis/mybatisConfig.xml

# 设置所有Entities别名类所在的包
mybatis.type-aliases-package=com.bian.springcloud.entities

mybatis.configuration.cache-enabled=true

mybatis.configuration.map-underscore-to-camel-case=true

# mapper映射文件
mybatis.mapper-locations=classpath:mybatis/mapper/**.xml

logging.level.com.bian.springcloud.dao=debug

# 设置应用程序名字（其实就是我们对外暴露的微服务名字），这里为了负载均衡测试，不要改
spring.application.name=microservicecloud-provider-dep

# 数据库连接名字
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://192.168.1.5:3306/cloud03?useUnicode=true&characterEncoding=utf-8&useSSL=false
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource

# 数据库连接池的最小维持连接数
spring.datasource.dbcp2.min-idle=5

# 数据库连接池的最大维持连接数
spring.datasource.dbcp2.max-idle=5

# 数据库连接池的初始化连接数
spring.datasource.dbcp2.initial-size=5

# 数据库连接池等待连接获取最大的超时时间
spring.datasource.dbcp2.max-wait-millis=5000

# true 表示访问路径显示ip地址
eureka.instance.prefer-ip-address=true

# 这个是自己自定义ip访问地址
#eureka.instance.ip-address=192.168.1.12

# 自定义注册实例化名字
#eureka.instance.instance-id=micro-provider-dept-8001

# 就是将客户端注册进入eureka服务列表内
eureka.client.service-url.defaultZone=http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/

info.app.name=bian-microservicecloud
info.company.name=www.bian.com

# $ $  自动会扫描带$开头和结尾
info.build.artifactId=$project.artifactId$
info.build.version=$project.version$
```



pom.xml文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud</artifactId>
        <groupId>com.bian.springcloud</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-provider-dept-8003</artifactId>

    <!--引入相关的依赖 -->
    <dependencies>
        <!-- 引入自己定义的api通用包，可以使用Dept部门Entity -->
        <dependency>
            <groupId>com.bian.springcloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>${project.version}</version>
        </dependency>


        <!--将微服务provider注册到eureka中-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>

        <!--添加监控管理的依赖actuator,监控信息完善-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- 引入父pom文件中的junit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </dependency>

        <!-- 引入父pom文件中的mysql -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <!-- 引入父pom文件中的阿里连接池 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
        </dependency>

        <!-- 引入父pom文件中的logback -->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
        </dependency>

        <!-- 引入父pom文件中mybatis-spring-boot-starter -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>

        <!-- 引入jetty -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jetty</artifactId>
        </dependency>

        <!-- 引入springboot-web场景启动器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 引入测试场景启动器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>

        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>springloaded</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>

    </dependencies>
</project>
```

**备注一下：**

​	端口：需要更改

​	数据库连接：更改成自己的连接数据库

​	对外暴露的统一的服务实例名：==不能修改，都是同一个==



比较吃电脑内存，额...

![1550072178488](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550072178488.png)

先暂时开了6个，三个eureka注册中心、三个provider微服务

2、自测：启动三个DeptProvider微服务并且各自测试通过

- http://locahost:8081/dept/get/1
- http://locahost:8083/dept/get/1
- http://locahost:8084/dept/get/1

![1550072666059](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550072666059.png)



![1550072716270](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550072716270.png)



![1550072753448](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550072753448.png)

3、再启动部门消费层微服务 consumer

![1550073406950](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550073406950.png)

ribbon 里面算法：

1. 随机 (Random)
2. 轮询 (RoundRobin)    （默认算法）
3. 一致性哈希 (ConsistentHash)
4. 哈希 (Hash)
5. 加权（Weighted）



## 3、ribbon的核心组件IRule

IRule：根据特定算法中从服务列表中选取一个要访问的服务

```java
package com.bian.springcloud.cfgBeans;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author bianpengfei
 * @create 2019-02-13 3:42
 **/
@Configuration
public class ConfigBean {  //boot -> spring -> applicationContext.xml -> @Configuration配置的 ConfigBean

    @Bean
    @LoadBalanced  // springCloud Ribbon基于Netflix Ribbon实现的一套客户端    负载均衡工具
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public IRule myRule() {
        return new RandomRule(); // 达到的目的，用我们重新选择的随机算法替代默认的轮询
    }
}
```

![1550074942813](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550074942813.png)





##4、自定义IRule规则

在启动该微服务的时候就能去加载我们自定义的Ribbon的配置类，从而是配置生效。形如：

@RibbonClient(name="MICROSERVICE-DEPT",configuration===MySelfRule==.class)

警告：

这个自定义配置类不能放在@ComponentScan所扫描的当前包以及子包下，否则我们自定义的这个配置类就会被所有的Ribbon的客户端所共享，也就是说我们达不到特殊定制的目的了。

![1550133978569](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550133978569.png)

MySelfRuleCon类：

```java
package com.bian.myRule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;

import java.util.List;

/**
 * @author bianpengfei
 * @create 2019-02-14 16:35
 **/

public class MySelfRuleCon extends AbstractLoadBalancerRule{

    private int total = 0;    // 总共被调用的次数，目前要求每台被调用5次
    private int currentIndex = 0;   // 当前提供服务的机器号

    /**
     * Randomly choose from all living servers
     */
    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        Server server = null;

        while (server == null) {
            if (Thread.interrupted()) {
                return null;
            }
            List<Server> upList = lb.getReachableServers();
            List<Server> allList = lb.getAllServers();
            System.out.println(666666666);
            int serverCount = allList.size();
            if (serverCount == 0) {
                /*
                 * No servers. End regardless of pass, because subsequent passes
                 * only get more restrictive.
                 */
                return null;
            }

            if (total < 5) {
                server = upList.get(currentIndex);
                total++;
            } else {
                total = 0;
                currentIndex++;
                if (currentIndex >= upList.size()) {
                    currentIndex=0;
                }
            }
            if (server == null) {
                /*
                 * The only time this should happen is if the server list were
                 * somehow trimmed. This is a transient condition. Retry after
                 * yielding.
                 */
                Thread.yield();
                continue;
            }

            if (server.isAlive()) {
                return (server);
            }

            // Shouldn't actually happen.. but must be transient or a bug.
            server = null;
            Thread.yield();
        }

        return server;

    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        // TODO Auto-generated method stub

    }
}
```



MySelfRule配置类：

```java
@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule(){
        return new MySelfRuleCon();
    }
}
```





# 七、Feign负载均衡

Feign是一个声明式的负载均衡。使用Feign能让编写的Web Service客户端更加简单，他的使用方法是定义一个接口，然后在上满添加注解，同时也支持JAX-RS标准的注解。Feign也支持可拔式的编码器和解码器。Spring Cloud对Feign进行了封装，使其支持了Spring MVC标准注解和HttpMessageConverters。==Feign可以与Eureka和Ribbon组合使用以支持负载均衡。==



Feign是一个声明式的Web服务客户端，使得编写Web客户端变得非常容易

**==只要创建一个接口，然后在上面添加注解即可。==**



我们习惯面向接口编程。比如WebService接口，比如我们的DAO接口，这个已经是大家的规范

1）根据未付名字调用地址

2） 通过接口+注解，获得我们的调用服务



参考之前的microservicecloud-consumer-dept-8002

将之前的src/main/java的类全部复制过来，并且同时复制application.properties文件，与pom.xml依赖

同时给也添加上对Feign的依赖：

~~~xml
<!--添加Feign依赖-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-feign</artifactId>
		</dependency>
~~~



在microservicecloud-api上controller层创建一个DeptClientService类：

```java
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
@FeignClient(name = "microservicecloud-provider-dept")
public interface DeptClientService {

    @RequestMapping(value="/dept/add", method= RequestMethod.POST)
    boolean add(Dept dept);

    @RequestMapping(value="/dept/get/{id}", method=RequestMethod.GET)
    Dept get(@PathVariable("id") long id);

    @RequestMapping(value="/dept/get", method=RequestMethod.GET)
    List<Dept> findAll();
}
```

在microservicecloud-consumer-dept-8001     application.properites中添加：

~~~properties
feign.hystrix.enabled=true    //开启器熔断器机制
~~~



microservicecloud-consumer-dept-feign-8001

中开启@EnableFeignClients()    @ComponentScan

以前的我们是

Feign通过调用接口的方法调用Rest服务（之前是Ribbon+RestTemplate）

Feign也支持负载均衡，它融入了Ribbon中的轮询，所以也支持负载均衡作用





# 八、 Netflix  Hystrix熔断器 

##1、熔断器简介

分布式系统面临的问题：

**服务雪崩：**

多个微服务之间调用的时候，假设微服务A调用微服务B和微服务C，微服务B和微服务C又调用其他的微服务。这既是所谓的“**扇出**”。如果扇出的链路上某个微服务的调动响应时间过长或者是不可用，对微服务A的调用就会占用越来越多的资源，进而引起系统雪崩，这就是“**雪崩效应**”

Hystrix是一个用于处理分布式系统的延迟和容错的开源库，在分布式系统中，许多依赖不避免的会调用失败，比如超时，异常等等，**Hystrix能够保证一个依赖出现的问题的情况下，不会导致服务失败， 避免出现级联故障，以提高分布式的弹性。**

“断路器”本身就是一中开关的装置，当某个服务单元发生故障之后，等待断路器的故障监控（类似熔断保险丝），**向调用方返回一个符合预期的、可处理的备选相应（FallBack），而不是长时间的等待或者抛出调出方无法处理的异常**，这样保证了服务调用方法的线程不会长时间、不必要地占用，从而避免了故障在分布式系统中的蔓延，乃至雪崩。



**服务熔断：**

熔断机制是应对雪崩效应的一种微服务链路保护机制

当扇出的某个微服务不可用或者相应时间太长时，会进行服务的降级**。进而熔断该节点对微服务的调用，快速返回“错误”的相应信息**。当检测到该节点微服务调用响应正常后恢复调用链路。在SpringCloud框架里熔断机制通过Hystrix实现的。Hystrix会监控微服务之间调用的状况，当失败的调用一定阈值，缺省是==**5秒内20次**==调用失败就会启动熔断机制，熔断机制注解是**@HystrixCommand**



## 2、创建熔断器

创建工程  microservicecloud-provider-dept-hystrix-8001

根据之前的工程microservicecloud-provider-dept-8001中的 pom文件 application.properties文件复制过来

pom.xml文件：复制microservicecloud-provider-dept-8001中pom文件，并添加熔断器Hystrix依赖：

~~~xml
        <!--引入springCloud熔断器-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-hystrix</artifactId>
        </dependency>

~~~



比如远程调用时候出错了，我们用@HystrixCommand中的fallback=【方法名】的方式进行异常回调

```java
package com.bian.springcloud.controller;

import com.bian.springcloud.entities.Dept;
import com.bian.springcloud.service.deptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

   // 一旦调动了该服务方法并抛出异常信息后，会自动调用@HystrixCommand标注好的fallbackMethod调用类中的指定方法
   @HystrixCommand(fallbackMethod = "processHystrixGet")
   @RequestMapping(value="/dept/get/{id}", method=RequestMethod.GET)
   public Dept get(@PathVariable("id") long id) {
      Dept dept = deptService.findById(id);
      if (dept == null ) {
         throw new RuntimeException("该Id："+id+"没有对应的信息");
      }
      return dept;
   }

   public Dept processHystrixGet(@PathVariable("id") long id){
      return new Dept().setDeptNo(id).setDeptName("该ID："+id+"没有对应的信息，Null--@HystrixCommand")
            .setDbSource("No this database in MySQL");
   }
   
```



开启注解：

```java
@EnableEurekaClient  // 服务启动后会自动注入到eureka容器中
@EnableDiscoveryClient // 服务发现
@SpringBootApplication
@EnableHystrix
public class DeptProviderHystrix8005_App {
   
   public static void main(String[] args) {
      SpringApplication.run(DeptProviderHystrix8005_App.class, args);
   }
   
}
```

![1550157281605](C:\Users\ADMINI~1\AppData\Local\Temp\1550157281605.png)



## 3、服务降级

服务降级是：整体资源快不够了，忍痛将某些服务先关掉，待度过难关，再开启回来。



服务的降级处理是在客户端实现完成的，与服务端是没有关系的



比如说每次请求的时候，分布式开发每个方法上面都有要进行@HystrixCommand 中的fallbackmethod，这样岂不是显示很冗余，我们学会运用aop的面相切面编程的思想，统一的配置及返回错误信息

修改microservicecloud-api工程

根据已有的DeptClientService接口新建一个实现了FallBackFactory接口的类DeptClientServiceFall

![1550204272010](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550204272010.png)



```java
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
```



在Chrome上进行测试：

![1550208957199](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550208957199.png)

其实主要是让我们知道，让客户端在服务端不可用时也会获的提示信息而不会让挂起服务器



## 4、服务熔断

一般是某个服务故障或者异常引起，类似于现实世界中的“保险丝”，当某个异常条件被触发的时候，直接熔断整个服务，而不是一直等到此服务超时。



所谓的降级，一般是从整体符合考虑。就是当某个服务熔断之后，服务器将不再调用。



此时客户端可以有自己的准备一个本地的fallback回调，返回一个缺省值



这样做，虽然服务水平下降，但好歹可用，比直接挂要强。



## 5、服务监控hystrixDashboard

概述

除了隔离依赖服务的调用以外，Hystrix还提供饿了**准实时的调用监控（Hystrix Dashboard）**，Hystrix会持续地记录所有通过Hystrix发起的请求的执行信息，并以统计报表和图形的形式展示给用户，包括每秒多少请求多少成功，多少失败等。

Netflix通过hystrix-metrics-event-stream项目实现了对以上指标的监控。Spring cloud也提供了Hystrix Dashboard的整合，对监控的内容转化成可视化界面。



创建一个microservicecloud-consumer-hystrix-dashboard工程，

pom.xml文件

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud</artifactId>
        <groupId>com.bian.springcloud</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-consumer-hystrix-dashboard</artifactId>
    <dependencies>
        <dependency>
            <groupId>com.bian.springcloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>${project.version}</version>
        </dependency>

        <!--导入hystrix dashboard相关的依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-hystrix</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
        </dependency>

        <!--web场景启动器-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!--修改后立即去生效-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>springloaded</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
    </dependencies>

</project>
~~~



application.properties

```properties
server.port=9001
```



添加注解： @EnableHystrixDashBoard  

```java
package com.bian.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author bianpengfei
 * @create 2019-02-15 14:32
 **/

@SpringBootApplication
@EnableHystrixDashboard   // 开启熔断监控
public class DeptConsumerDashBoard_APP {

    public static void main(String[] args) {
        SpringApplication.run(DeptConsumerDashBoard_APP.class, args);
    }
}
```



监控的客户端的同时，前提是客户端的pom文件中引入了actuator这个springboot专门用来监控的依赖；

```xml
<!--添加监控管理的依赖actuator,监控信息完善-->
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```



开始测试：启动我们的项目 microservicecloud-consumer-hystrix-dashboard工程，访问端口

http://localhost:9001/hystrix

![1550212811651](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550212811651.png)



![1550212829199](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550212829199.png)

出现上面的图标，就说明我们的项目访问成功

开始监控：在我们浏览器地址访问栏上输入： http://localhost:9001/hystrix.stream

出现：

![1550213639631](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550213639631.png)



如果出现 一直显示ping的话，说明你没有在代码方法中添加注解@@HystrixCommand(fallbackMethod = "xxx")



文字观看有点着急，看的很难受，不是很直观，这时候我们可以通过图形化的方式

![1550214046668](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550214046668.png)



弹出图形化界面：

![1550214088002](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550214088002.png)

那我们如何看年？

里面有个实心圈： 共有两种含义。它是通过颜色的变化代表了实例的健康程度。它的健康度从绿色<黄色<橙色<红色递减。

该实心圆除了颜色的变化之外，它的大小也会根据实例的请求流量发生变化，流浪越大实心圆越大。所以通过该实心圆的展示，就可以在大量的实例中快速的发现==**故障实例和高压力实例。**==

![](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/360截图18040407297235.png)





# 九、zuul路由网关

##1、概述

zuul包含了对请求的路由和过滤两个最重要的功能：

其中路由功能负责将外部请求转发到具体的微服务实例上，是实现外部访问统一入口的基础而过滤器功能则负责对请求的处理过程进行干预，是实现请求校验、服务聚合等功能的基础上Zuul和Eureka进行整合，将Zuul自身注册为Eureka中获的其他微服务消息，也即以后的访问微服务都是通过Zuul跳转后获得。



注意：Zuul服务最终还是会注册到Eureka中

![](https://spring.io/img/homepage/diagram-distributed-systems.svg)



**==提供=代理+路由+过滤三大功能==**



## 2、路由的基本配置



github上详细源码：https://github.com/Netflix/zuul/wiki

新增加系统映射域名

[Windows](https://www.baidu.com/s?wd=Windows&tn=24004469_oem_dg&rsv_dl=gh_pl_sl_csd)对应的位置是：[C:](https://www.baidu.com/s?wd=C%3A&tn=24004469_oem_dg&rsv_dl=gh_pl_sl_csd)\Windows\System32\drivers\etc\hosts

Linux对应的是：/etc/hosts

127.0.0.1      myzuul.com



1、创建microservicecloud-zuul-geteway-9527  工程

2、修改pom.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud</artifactId>
        <groupId>com.bian.springcloud</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-zuul-geteway-9527</artifactId>

    <dependencies>
        <!-- 引入自己定义的api通用包，可以使用Dept部门Entity -->
        <dependency>
            <groupId>com.bian.springcloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>${project.version}</version>
        </dependency>
        <!-- 引入父pom文件中mybatis-spring-boot-starter -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>

        <!-- 引入jetty -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jetty</artifactId>
        </dependency>

        <!--引入eureka和zuul，因为zuul要注入到eureka中-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zuul</artifactId>
        </dependency>

        <!--actuator监控-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!--hystrix熔断器-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-hystrix</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>

        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>springloaded</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>

    </dependencies>

</project>
```



3、修改application.properties

```properties
server.port=9527

spring.application.name=microservicecloud-zuul-geteway-9527

eureka.instance.prefer-ip-address=true
eureka.instance.instance-id=www.geteway-9527.com

eureka.client.service-url.defaultZone=http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/

# $ $  自动会扫描带$开头和结尾
info.name=人帅多精
info.age=25
info.build.artifactId=$project.artifactId$
info.build.version=$project.version$
```



4、测试：启动 三个 集群eureka-server服务器，再分别启动microservicecloud-provider-dept-8001和microservicecloud-zuul-geteway-9527

![1550220955920](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550220955920.png)

红色的表示 只要我进行 zuul地址再加上  /microservicecloud-provider-dept/  就可以访问 微服务 名为microservicecloud-provider-dept下的路径和资源



查看到结果  已经注册到我们eureka 服务器上了

![1550220699563](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550220699563.png)



分别访问：

~~~
使用路由：http://localhost:8001/dept/get/1
 
启用路由：http://myzuul.com:9527/microservicecloud-provider-dept/dept/get/1
~~~



![1550220901927](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550220901927.png)



![1550220818721](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550220818721.png)



访问成功



## 3、路由访问映射规则

1、直接在之前的工程microservicecloud-zuul-gateway-9527上修改application.propeties

添加如下代码：

```properties
zuul.routes.mydept.serviceId=microservicecloud-provider-dept
zuul.routes.mydept.path=/mydept/**
```



2、还是老规矩，启动三个集群eureka服务器，和microservicecloud-provider-dept-8001项目与microservicecloud-zuul-gateway-9527

测试：

![1550222094213](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550222094213.png)

这表明我只要 在 zuul地址 + /mydept/   就可以访问微服务 名为 microservicecloud-provider-dept下的所有资源

还可以通过  zuul地址 + /microservicecloud-provider-dept/  也可以

这样就完美的保证了我的微服务名字不会担心 暴露出去 

~~~java
// 在浏览器上输入 访问地址
http://myzuul.com:9527/mydept/info
~~~



![1550222255044](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550222255044.png)

不过为了保证真正的微服务 不能暴露出去，保证安全性

在microservicecloud-zuul-gateway-9527的application.propetits添加

隐藏真实=服务名，隐藏所有的微服务名字：

~~~properties
zuul.ignored-services=microservicecloud-provider-dept
~~~

访问不了，访问失败了

![1550223576241](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550223576241.png)



隐藏真实服务名，忽略所有的微服务名字：

```properties
zuul.ignored-services=*
```



设置统一的公共前缀，表示所有的访问前缀都要加/bian

```properties
zuul.prefix=/bian
```

![1550224288667](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550224288667.png)

pom.xml文件全部内容：

```properties
server.port=9527

spring.application.name=microservicecloud-zuul-gateway-9527

eureka.instance.prefer-ip-address=true
eureka.instance.instance-id=www.geteway-9527.com

eureka.client.service-url.defaultZone=http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/

# $ $  自动会扫描带$开头和结尾
info.name=人帅多精
info.age=25
info.build.artifactId=$project.artifactId$
info.build.version=$project.version$

# 进行基于zuul理由端口映射，serviceId针对于是哪个微服务项目，path表示我的访问地址
zuul.routes.mydept.serviceId=microservicecloud-provider-dept
zuul.routes.mydept.path=/mydept/**

# 忽略原先的微服务名字
zuul.ignored-services=*

# 表示所有的访问前缀都要加/bian
zuul.prefix=/bian
```



# 十、SpringCloud Config 配置中心

## 1、简介

springcloud config 分为服务端和客户端两部分



服务端也成为**分布式配置中心，它是一个独立的微服务应用**，用来连接配置服务器并为客户端提供获取配置信息，加密/解密信息等访问接口。



客户端则是通过制定的配置中心来管理应用资源，以及与业务相关的配置内容，并在启动的时候从配置中心获取和加载配置信息。



## 2、SpringCloud Config服务端配置

1、用自己的GitHub账号在GitHub上新建一个名为microservicecloud-config的新Repository



![1550232131715](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550232131715.png)



2、在本地磁盘下新建一个git仓库，添加远程remote，并将自己编写的三个application.properties、application-dev.properties、application-test.properties上传到gitHub的microservicecloud-config仓库中

application.properties内容:

~~~properties
spring.profiles.actvie=dev
~~~



application-dev.properties内容:

~~~properties
spring.applcation.name=microservicecloud-config-3344-dev
~~~



application-test.properties内容:

~~~properties
spring.application.name=microservicecloud-config-3344-test
~~~





![1550232266003](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550232266003.png)

![1550232275864](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550232275864.png)

提交成功：github上显示

![1550232318124](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550232318124.png)

3、创建一个microservicecloud-config-3344工程，并在里面添加pom.xml相关依赖，以及修改application.xml和在程序的入口添加注解@EnableConfigServer

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud</artifactId>
        <groupId>com.bian.springcloud</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-config-3344</artifactId>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>

        <!--导入hystrix dashboard相关的依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-hystrix</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>springloaded</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
    </dependencies>

</project>
```

application.properties:

```properties
server.port=3344

spring.application.name=microservicecloud-config-3344

# github上的地址,表示我的springcloud的服务端配置和github进行链接
spring.cloud.config.server.git.uri=https://github.com/bianloveting/microservicecloud-config.git
```

程序入口代码：

```java
package com.bian.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author bianpengfei
 * @create 2019-02-15 19:33
 **/
@SpringBootApplication
@EnableConfigServer
public class Config3344_APP {
    public static void main(String[] args) {
        SpringApplication.run(Config3344_APP.class, args);
    }
}
```



5、进行域名的映射：

~~~java
Windows对应的位置是：C:\Windows\System32\drivers\etc\hosts

Linux对应的是：/etc/hosts

127.0.0.1      config3344.com

~~~



4、测试：

~~~java
// 在浏览器上输入
http://config3344.com:3344/application-test.properties
~~~

![1550233209522](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550233209522.png)

~~~java
// 在浏览器上输入
http://config3344.com:3344/application-dev.properties
~~~

![1550233295957](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550233295957.png)



~~~java
// 在浏览器上按照格式 application-xxx    这三个xxx是随便填写的内容
http://config3344.com:3344/application-sdhfkshfhf.properties
~~~

![1550233436575](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550233436575.png)

服务器会在远程上去找，他没有找到只是显示了默认的配置信息



但是如果你是这样的：随便输入一个地址

~~~java
http://config3344.com:3344/applicatsdsif.properties
~~~

![1550233639803](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550233639803.png)

提示404错误，访问不到,因为配置文件信息根本不存在



通过上面的我们可以发现：

springcloud config通过远程调用github自动识别我们的配置文件，按照一定的访问配置文件的格式。

配置读取规则：

1、/{application}/{profile}[/{label}]

例子：

~~~http
http://config3344.com:3344/application/dev/master
~~~

![1550234022215](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550234022215.png)



2、/{application}/{profile}    

（略）因为我这里配置的是properties文件，并不是yaml文件，有兴趣的读者，你可以自己尝试一下



3、/{label}/{application}-{profile}.properties

~~~http
http://config3344.com:3344/master/application-dev.properties
~~~

![1550234252000](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550234252000.png)



## 3、SpringCloud Config客户端配置与测试

1、在我们上一个项目的本地仓库上继续创建三个propeties文件：

microservicecloud-config-client.properties内容：

~~~properties
spring.profiles.active=test
~~~



microservicecloud-config-client-dev.properties内容：

~~~properties
spring.application.name=microservicecloud-config-client-3355
server.port=8201
eureka.client.service-url.defaultZone=http://eureka-dev.com:7001/eureka/
~~~



microservicecloud-config-client-test.properties内容：

~~~properties
spring.application.name=microservicecloud-config-3355-test
server.port=8202
eureka.client.service-url.defaultZone=http://eureka-test.com:8001/eureka/
~~~



并将他们三个properties文件上传到我的github中

![1550238501886](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550238501886.png)

上传的步骤我就不写了，这个是基本功！



2、进行环境搭配

创建新的工程——config客户端   microservicecloud-config-client-3355

参考之前的config服务端的项目microservicecloud-config-3344 中的pom.xml文件

 将config服务端的项目microservicecloud-config-3344 中的pom.xml中的

![1550234800635](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550234800635.png)

修改成客户端的

![1550234853316](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550234853316.png)



整个pom.xml文件内容如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud</artifactId>
        <groupId>com.bian.springcloud</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-config-client-3344</artifactId>

    <dependencies>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>

        <!--导入hystrix dashboard相关的依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-hystrix</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- 修改后立即生效，热部署 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>springloaded</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
    </dependencies>
</project>
```









2、添加boostrap.properties

==注意：==

**application.properties 是用户级资配置项**

**bootstrap.properites是系统级别的，==优先级更高==**



SpringClou会创建一个‘Bootstrap Context’上下文，是作为Spring应用的‘Application Context’的**父上下文，**

初始化的时候，‘Bootstarp Context'负责从外部源加载配置属性并解析配置。这两个上下文共享一个从外部获取的‘Environment’，



Bootstrap属性拥有高优先级，默认情况下，他们不会被本地配置覆盖。‘Bootstrap Context’和‘Application Context’有着不同的约定。

所以新增了一个‘bootstrap.yml，文件，保证了‘BootStrap Context'和’Application Context‘有着不同的约定，所以新增了一个’bootstrap.yml‘文件，保证了’Boostrap Context‘和’Application Context‘配置的分离。



bootstrap.properties内容：

```properties
# 表示需要从github上读取资源文件名称
spring.cloud.config.name=microservicecloud-config-client

# 本次访问的配置项,这个决定我们profile是什么，我们就从github上读取什么
spring.cloud.config.profile=dev

spring.cloud.config.label=master

# 本次服务启动后先去找3344号服务，通过SpringCloudConfig获取GitHub的服务地址，这个3344那个服务配置中心
spring.cloud.config.uri=http://config3344.com:3344
```



application.properties:

```properties
spring.application.name=microservicecloud-config-client
```



创建controller层里面的ConfigClientRest.class，这个类主要是帮我们观察里面的配置信息，在浏览器上打印出来

~~~java
package com.bian.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bianpengfei
 * @create 2019-02-15 21:08
 **/
@RestController
public class ConfigClientRest {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${eureka.client.service-url.defaultZone}")
    private String eurekaServers;

    @Value("${server.port}")
    private String port;

    @RequestMapping("/config")
    private String getConfig() {
        String str = "applicationName: " + applicationName+ "\t eurekaServers:" +eurekaServers;
        System.out.println("*********str:"+ str);
        return "applicationName:" + "applicationName+"+"\t eurekaServers:" + eurekaServers;
    }

}

~~~



入口配置类：（好像啥都没有变）

```java
package com.bian.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author bianpengfei
 * @create 2019-02-15 21:19
 **/
@SpringBootApplication
public class ConfigClient3355_APP {

    public static void main(String[] args) {
        SpringApplication.run(ConfigClient3355_APP.class, args);
    }
}
```



3、开始测试：

分别启动config服务端和客户端，记住是先启动服务端，然后在启动客户端，

即先启动microservicecloud-config-3344项目，后启动microservicecloud-config-client-3355项目

![1550238754216](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550238754216.png)



看到控制台显示的是 8201端口，在回头看一下我们的之前在bootstrap.properties配置文件中的spring.cloud.config.profile=dev,它指定了dev，说明我们从github上读取配置文件的时候，也是根据profile=dev的配置

![1550238912955](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550238912955.png)



我们在测试一下我们的之前写的ConfigClientRest.class

在控制台上测试一下：

~~~java
// 在浏览器地址栏上写入：
http://localhost:8201/config
~~~



![1550239102700](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550239102700.png)



如果是将我的bootstrap.properties中的spring.cloud.profile=test看看是什么样的？

![1550239346818](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550239346818.png)



控制台显示：8202端口

![1550239394121](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550239394121.png)



浏览器地址上输入

![1550239432027](https://gitee.com/bianpengfei/spring-cloud-demo/raw/master/images/1550239432027.png)



一点没错  没毛病







