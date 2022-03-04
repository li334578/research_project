package com.company.micro_service_1.thread_test;

import com.company.micro_service_1.MicroService1Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.Semaphore;

/**
 * @Date 2/3/2022 0002 上午 9:49
 * @Description 线程测试类
 * @Version 1.0.0
 * @Author liwenbo
 */
@SpringBootTest(classes = MicroService1Application.class)
@ExtendWith(SpringExtension.class)
public class ThreadTestClass {

    @BeforeEach
    public void init() {
        System.out.println("before method launch");
    }


    @AfterEach
    public void destroy() {
        System.out.println("after method launch");
    }

    public static void main(String[] args) {
        MyRunnable runnable = new MyRunnable();
//        Thread thread1 = new Thread(runnable, "MyThread1");
//        System.out.println(Thread.currentThread().getName());
//        // 测试run方法和start方法
//        thread1.run();
//        thread1.start();

//        System.out.println("========================");
//        // 测试yield方法
//        Thread thread2 = new Thread(runnable, "MyThread2");
//        System.out.println(Thread.currentThread().getName()+" start");
//        thread2.start();
//        Thread.yield();
//        System.out.println(Thread.currentThread().getName()+" end");

//        System.out.println("========================");
//        // 测试 join方法
//        System.out.println(Thread.currentThread().getName() + " start");
//        Thread thread3 = new Thread(runnable, "MyThread3");
//        thread3.start();
//        try {
//            thread3.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(Thread.currentThread().getName() + " end");
        /*
         * 不join的结果
         * main start
         * main end
         * MyRunnable run method launch, current thread name is MyThread3
         * join的结果
         * main start
         * MyRunnable run method launch, current thread name is MyThread3
         * main end
         */

//        System.out.println("=========================");
//        // 理解join例子 三个线程 t1 t2 t3 按照 t3 t2 t1的顺序执行
//        Thread t3 = new Thread(() -> {
//            System.out.println(Thread.currentThread().getName() + " start");
//            System.out.println(Thread.currentThread().getName() + " end");
//        }, "t3");
//        Thread t2 = new Thread(() -> {
//            System.out.println(Thread.currentThread().getName() + " start");
//            System.out.println(Thread.currentThread().getName() + " end");
//        }, "t2");
//        Thread t1 = new Thread(() -> {
//            System.out.println(Thread.currentThread().getName() + " start");
//            System.out.println(Thread.currentThread().getName() + " end");
//        }, "t1");
//
//        try {
//            t3.start();
//            t3.join();
//            t2.start();
//            t2.join();
//            t1.start();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        System.out.println("==============================");
//        // 用countDown 实现 实际使用要声明为类变量
//        CountDownLatch countDownLatch1 = new CountDownLatch(1);
//        CountDownLatch countDownLatch2 = new CountDownLatch(1);
//        Thread t3 = new Thread(() -> {
//            System.out.println(Thread.currentThread().getName() + " start");
//            System.out.println(Thread.currentThread().getName() + " end");
//            countDownLatch2.countDown();
//        }, "t3");
//        Thread t2 = new Thread(() -> {
//            try {
//                countDownLatch2.await();
//                System.out.println(Thread.currentThread().getName() + " start");
//                System.out.println(Thread.currentThread().getName() + " end");
//                countDownLatch1.countDown();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "t2");
//        Thread t1 = new Thread(() -> {
//            try {
//                countDownLatch1.await();
//                System.out.println(Thread.currentThread().getName() + " start");
//                System.out.println(Thread.currentThread().getName() + " end");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }, "t1");
//        t3.start();
//        t2.start();
//        t1.start();

        System.out.println("==============================");
        // 使用Semaphore实现
        Semaphore s1 = new Semaphore(3);
        try {
            // 先占用两个
            s1.acquire(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t1 = new Thread(() -> {
            try {
                s1.acquire(3);
                System.out.println(Thread.currentThread().getName() + " start");
                System.out.println(Thread.currentThread().getName() + " end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            try {
                s1.acquire(2);
                System.out.println(Thread.currentThread().getName() + " start");
                System.out.println(Thread.currentThread().getName() + " end");
                s1.release(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2");
        Thread t3 = new Thread(() -> {
            try {
                s1.acquire(1);
                System.out.println(Thread.currentThread().getName() + " start");
                System.out.println(Thread.currentThread().getName() + " end");
                s1.release(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t3");
        t3.start();
        t2.start();
        t1.start();
    }
}
