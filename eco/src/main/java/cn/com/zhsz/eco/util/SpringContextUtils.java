package cn.com.zhsz.eco.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Author：Mr.X
 * Date：2017/11/8 10:00
 * Description：Spring容器会检测容器中的所有Bean，如果发现某个Bean实现了ApplicationContextAware接口，
 * Spring容器会在创建该Bean之后，自动调用该Bean的setApplicationContextAware()方法，
 * 调用该方法时，会将容器本身作为参数传给该方法——该方法中的实现部分将Spring传入的参数（容器本身）
 * 赋给该类对象的applicationContext实例变量，
 * 因此接下来可以通过该applicationContext实例变量来访问容器本身。
 * --------------------
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
 
    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;
 
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }
 
    /**
     * 获取applicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
 
    /**
     * 通过name获取 Bean.
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }
 
    /**
     * 通过class获取Bean.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }
 
    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}