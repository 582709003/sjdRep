package cn.com.zhsz.eco.config;

import com.njdx.njdxqywx.fileter.MyFilter;
import com.njdx.njdxqywx.interceptor.MyInterceptor;
import com.njdx.njdxqywx.listener.MyHttpSessionListener;
import com.njdx.njdxqywx.listener.MyRequestListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: njdxqywx
 * @description: mvc配置类
 * @author: Mr.Sun
 * @create: 2018-12-02 16:14
 **/
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private MyInterceptor MyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则, 这里假设拦截 /url 后面的全部链接
        // excludePathPatterns 用户排除拦截（可以添加多个拦截器）
       //registry.addInterceptor(MyInterceptor).addPathPatterns("/");
    }

    @Bean
    public FilterRegistrationBean filterRegist() {
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        frBean.setFilter(new MyFilter());
        frBean.addUrlPatterns("/");
        System.out.println("filter");
        return frBean;
    }
    @Bean
    public ServletListenerRegistrationBean listenerRegist() {
        ServletListenerRegistrationBean srb = new ServletListenerRegistrationBean();
        srb.setListener(new MyRequestListener());
        srb.setListener(new MyHttpSessionListener());
        System.out.println("listener");
        return srb;
    }

}
