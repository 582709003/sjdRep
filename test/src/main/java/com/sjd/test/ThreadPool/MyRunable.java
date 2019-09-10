package com.sjd.test.ThreadPool;

/**
 * @Auther sunjid
 * @Date 2019/9/5
 * @Date Runable实现线程的创建
 **/
public class MyRunable implements Runnable {

    private String name;

    public MyRunable(String name){
        this.name = name;
    }

    @Override
    public void run() {
        // TODO 自动生成的方法存根
        for(int i=0;i<3;i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            System.out.println(name+"---"+i);
        }
    }
}
