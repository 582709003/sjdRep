package com.sjd.test.listener;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.logging.Filter;

/**
 * @Auther sunjid
 * @Date 2019/9/4
 * @Date session监听器
 **/

@Component
public class MyHttpSessionListener implements HttpSessionListener {

    /**
     * 记录在线的用户数量
     */
    public Integer count = 0;

    /**
     * 创建session
     * @author sunjid
     * @date 2019/9/4
     * @param [httpSessionEvent]
     * @return void
    */
    @Override
    public synchronized void sessionCreated(HttpSessionEvent httpSessionEvent){
        count++;
        httpSessionEvent.getSession().getServletContext().setAttribute("count",count);
    }
    /**
     *  销毁session
     * @author sunjid
     * @date 2019/9/4
     * @param [httpSessionEvent]
     * @return void
    */
    @Override
    public synchronized void sessionDestroyed(HttpSessionEvent httpSessionEvent){
        count--;
        httpSessionEvent.getSession().getServletContext().setAttribute("count",count);
    }

}
