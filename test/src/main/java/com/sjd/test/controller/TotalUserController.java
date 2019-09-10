package com.sjd.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @Auther sunjid
 * @Date 2019/9/4
 * @Date 获取登录总人数
 **/
@RestController
@RequestMapping("/totalUser/")
@Slf4j
public class TotalUserController {
    //这段代码相当于@Slf4j注解
    //private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("getTotalUser")
    public String getTotalUser(HttpServletRequest request, String name, HttpServletResponse response) {
        String name2 = URLDecoder.decode(request.getParameter("name"));
        System.out.println("name1"+request.getParameter("name"));
        System.out.println("name2:"+name2);
        log.debug("debuglog");
        log.info("debuglog");
        log.warn("debuglog");
        log.error("debuglog");
        System.out.println(System.getProperty("user.home"));
        Cookie cookie ;
        System.out.println("111:"+request.getParameter("name"));
        System.out.println("222:"+name);
        try{
            cookie = new Cookie("JSESSIONID", URLEncoder.encode(request.getSession().getId(),"utf-8"));
            cookie.setMaxAge(30 * 24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
            //double a = 1 / 0;
            Integer count = (Integer) request.getSession().getServletContext().getAttribute("count");
            return "当前在线人数：" + count;

        }catch(Exception e){
            throw new NullPointerException();
        }

    }

}
