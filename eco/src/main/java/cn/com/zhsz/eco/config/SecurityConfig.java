package cn.com.zhsz.eco.config;

import cn.com.zhsz.eco.core.CustomUserService;
import cn.com.zhsz.eco.core.MyFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @program: njdxqywx
 * @description: 安全认证类
 * @author: Mr.Sun
 * @create: 2019-01-19 14:29
 **/
/*@EnableWebSecurity注解和 @Configuration 注解一起使用, 注解 WebSecurityConfigurer 类型的类，
或者利用@EnableWebSecurity注解继承 WebSecurityConfigurerAdapter的类，这样就构成了 Spring Security 的配置类*/
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)//开启security注解
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Autowired
    private CustomUserService customUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
////                .anyRequest().authenticated() //任何请求需要认证
////                .and()
////                .formLogin()
////                .loginPage("/vmuser")
////                .permitAll()
////                .and()//登录页面用户任意访问
////                .logout().permitAll().and() //设置表单登录，创建UsernamePasswordAuthenticationFilter
////                .httpBasic().and().rememberMe();

        http.authorizeRequests()
                .anyRequest().permitAll();


        //注销行为任意访问
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        http.csrf().disable();
    }

    /*AuthenticationManagerBuilder 用于创建一个 AuthenticationManager**/
    @Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //使用自定的认证类
        auth.userDetailsService(customUserService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/css/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);

    }
}
