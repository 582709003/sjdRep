package cn.com.zhsz.eco.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @program: njdxqywx
 * @description: 安全拦截器(访问资源（即授权管理），访问url时，会通过AbstractSecurityInterceptor拦截器拦截，
 * 其中会调用FilterInvocationSecurityMetadataSource的方法来获取被拦截url所需的全部权限,
 * 在调用授权管理器AccessDecisionManager,
 * 这个授权管理器会通过spring的全局缓存SecurityContextHolder获取用户的权限信息 ,
 * 还会获取被拦截的url和被拦截url所需的全部权限 ， 然后根据所配的策略 （ 有 ： 一票决定 ， 一票否定 ， 少数服从多数等 ）,
 * 如果权限足够，则返回，权限不够则报错并调用权限不足页面
 * @author: Mr.Sun
 * @create: 2019-01-20 15:24
 **/
@Component
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
    @Autowired
    private FilterInvocationSecurityMetadataSource myInvocationSecurityMetadataSourceService;

    @Autowired
    public void setMyAccessDecisionManager(MyAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }


    public void invoke(FilterInvocation fi) throws IOException, ServletException {
//fi里面有一个被拦截的url
//里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi里的url对应的所有权限
//再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
//执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.myInvocationSecurityMetadataSourceService;
    }
}
