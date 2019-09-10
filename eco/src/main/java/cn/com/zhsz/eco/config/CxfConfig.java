package cn.com.zhsz.eco.config;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @program: njdxqywx
 * @description: cxf配置类
 * @author: Mr.Sun
 * @create: 2018-12-23 12:55
 **/

@Configuration
public class CxfConfig {

    @Bean
    public ServletRegistrationBean disServlet() {
        /**
         * // 发布服务名称 localhost:8090/test
         */
        return new ServletRegistrationBean(new CXFServlet(), "/webservice/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }


    /**
     * @Description:
     * @params []
     * @Return: javax.xml.ws.Endpoint
     * @Author: itastro
     * @Date: 2018/12/10 13:01
     */
    @Bean
    public HelloWsImpl demoService() {
        return new HelloWsImpl();
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), demoService());
        endpoint.publish("/helloWs");

        return endpoint;
    }


}
