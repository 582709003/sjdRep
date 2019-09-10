package com.sjd.test.ThreadPool;

import java.util.Calendar;
import java.util.concurrent.*;

/**
 * @Auther sunjid
 * @Date 2019/9/5
 * @Date 线程测试类
 **/
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        Thread myThread = new MyThread("线程1");
//        myThread.start();
//
//        Thread myThread2 = new MyThread("线程2");
//        myThread2.start();

        MyRunable myRunable1 = new MyRunable("线程1");
        Thread myThread = new Thread(myRunable1);
//        myThread.start();
//        MyRunable myRunable2 = new MyRunable("线程2");
//        Thread myThread2 = new Thread(myRunable2);
//        myThread2.start();
//        System.out.println("main");

        //固定线程 池
        //ExecutorService executorservice = Executors.newFixedThreadPool(3);

        //创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。
        //对于执行很多短期异步任务的程序而言，这些线程池通常可提高程序性能。
        //ExecutorService executorservice = Executors.newCachedThreadPool();

        //ExecutorService executorservice = Executors.newSingleThreadExecutor();

       // ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
//        for(int i = 0;i < 5;i ++){
////            final int taskID = i;
////            executorService.schedule(new Runnable() {
////                @Override
////                public void run() {
////                    for(int k = 0;k < 5;k++){
////                        try{
////                           Thread.sleep(500);
////
////                        }catch (Exception e){
////                            e.printStackTrace();
////                        }
////                        System.out.println("第" + taskID + " 个任务的第" + k + " 次执行");
////                    }
////                }
////            },5,TimeUnit.SECONDS);
////            executorService.scheduleAtFixedRate(new Runnable() {
////                @Override
////                public void run() {
////                    System.out.print(Calendar.getInstance().getTime());
////
////                    System.out.println(" ---爆炸！！");
////                }
////            },5,2,TimeUnit.SECONDS);
////        }
////        //executorservice.shutdown();
////        System.out.println("主线程执行结束");

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Long totalCap = 0l;
        for(int i = 0;i < 2 ;i++){
            Future<Long> cap = executorService.submit(new MyCallable(i));
            totalCap += cap.get();
        }
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(
//                5,  //核心池的大小（即线程池中的线程数目大于这个参数时，提交的任务会被放进任务缓存队列）
//                10, //线程池最大能容忍的线程数
//                200, //线程存活时间
//                TimeUnit.MILLISECONDS, //参数keepAliveTime的时间单位
//                new ArrayBlockingQueue<Runnable>(5),new ThreadPoolExecutor.AbortPolicy() //任务缓存队列，用来存放等待执行的任务
//        );
        System.out.println("硬盘总容量为：" + totalCap / 1024 / 1024 / 1024 +"G");
    }
}
