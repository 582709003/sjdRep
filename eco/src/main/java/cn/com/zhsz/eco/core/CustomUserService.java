package cn.com.zhsz.eco.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: njdxqywx
 * @description: 自定义user服务类
 * @author: Mr.Sun
 * @create: 2019-01-20 14:30
 **/
@Component
public class CustomUserService implements UserDetailsService {

//    @Autowired
//    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) { //重写loadUserByUsername 方法获得 userdetails 类型用户

//        User user = userMapper.findByUserName(username);
//        if(user == null){
//            throw new UsernameNotFoundException("用户名不存在");
//        }
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        //用于添加用户的权限。只要把用户权限添加到authorities 就万事大吉。
//        for(Role role:user.getRoles())
//        {
//            authorities.add(new SimpleGrantedAuthority(role.getRname()));
//            System.out.println(role.getRname());
//        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(),
//                user.getPassword(), authorities);

         return null;//为了语法通过，后期根据业务删除
    }
}
