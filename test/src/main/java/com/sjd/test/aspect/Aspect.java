package com.sjd.test.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @Auther sunjid
 * @Date 2019/9/4
 * @Date 通知测试类
 **/
@org.aspectj.lang.annotation.Aspect
@Component
public class Aspect {

    //切点是指所有的连接点的集合
    //连接点其实就是方法
    @Pointcut("execution(* com.sjd.test.controller..*.*(..)))")
    public void pointCut(){

    }

    @Before("execution(* com.sjd.test.controller..*.*(..)))")
    public void log(JoinPoint joinPoint){
        //目标对象
        Object ob = joinPoint.getTarget();
        //目标对象的类的全名
        String targetClassName = ob.getClass().getName();

        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        //连接点的方法名
        String methodName = signature.getMethod().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("正在访问类名为"+targetClassName+"方法名为"+methodName);

    }
    //@After  抛异常也走
    //抛异常不走
    @AfterReturning("execution(* com.sjd.test.controller..*.*(..)))")
    public void log2(JoinPoint joinPoint){
        System.out.println("正在访问类名22222为");
        //目标对象
        Object ob = joinPoint.getTarget();
        //目标对象的类的全名
        String targetClassName = ob.getClass().getName();

        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        //连接点的方法名
        String methodName = signature.getMethod().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("正在访问类名22222为"+targetClassName+"方法名为"+methodName);

    }

}
