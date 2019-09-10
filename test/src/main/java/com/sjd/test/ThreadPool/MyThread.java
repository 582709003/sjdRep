package com.sjd.test.ThreadPool;

/**
 * @Auther sunjid
 * @Date 2019/9/5
 * @Date 继承Thread 创建线程
 **/
public class MyThread extends Thread {

    public MyThread(String name){
        super(name);
    }

    @Override
    public void run() {
        for(int i=0;i<3;i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            System.out.println(this.getName()+"---"+i);
        }
    }
}
