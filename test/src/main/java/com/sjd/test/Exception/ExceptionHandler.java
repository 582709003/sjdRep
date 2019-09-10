package com.sjd.test.Exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther sunjid
 * @Date 2019/8/28
 * @Date 全局处理
 **/
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NullPointerException.class)
    public  void exceptionHandler(NullPointerException nx){
        System.out.println(nx.getMessage());
    }

    @ModelAttribute//注释在controller中
    public void addAttributes(Model model){
        System.out.println("ModelAttribute");

    }

    @InitBinder
    public void initBinder(WebDataBinder wd){
        System.out.println("initBinder--------");
        wd.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));

    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("PostConstruct");
    }

    @PreDestroy
    public void preConstruct(){
        System.out.println("PreConstruct");

    }
}
