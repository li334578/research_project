package com.company.micro_service_1.thread_test;

/**
 * @Date 2/3/2022 0002 上午 9:49
 * @Description 线程实现类
 * @Version 1.0.0
 * @Author liwenbo
 */
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("MyRunnable run method launch, current thread name is " + Thread.currentThread().getName());
    }
}
