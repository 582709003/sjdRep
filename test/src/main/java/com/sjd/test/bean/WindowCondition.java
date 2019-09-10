package com.sjd.test.bean;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Auther sunjid
 * @Date 2019/9/3
 * @Date @OnCondition测试
 **/
public class WindowCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        if(conditionContext.getEnvironment().getProperty("os.name").contains("linux")){
            System.out.println("11111");
            return true;
        }
        System.out.println("22222");
        return false;
    }
}
