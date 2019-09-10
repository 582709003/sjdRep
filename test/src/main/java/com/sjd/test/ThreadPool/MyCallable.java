package com.sjd.test.ThreadPool;

import java.io.File;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @Auther sunjid
 * @Date 2019/9/6
 * @Date callable实现线程
 **/
public class MyCallable implements Callable<Long> {

    private Integer num;

    private static Map<Integer,String> map = new HashMap<Integer,String>();

    public MyCallable(Integer num){
        this.num = num;
    }

    @Override
    public Long call() throws Exception {
        map.put(0,"C:");
        map.put(1,"D:");
        String path = map.get(num);
        File file = new File(path);
        Long cap = file.getTotalSpace();
        System.out.println(path + " 总空间大小 : " + cap / 1024 / 1024 / 1024 + "G");
        return cap;
    }
}
