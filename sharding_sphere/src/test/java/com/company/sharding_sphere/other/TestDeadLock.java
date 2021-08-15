package com.company.sharding_sphere.other;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description : 死锁
 * @date : 2021-05-17 20:59:09
 */
public class TestDeadLock {
    public static void main(String[] args) {
        TestDeadLock testDeadLock = new TestDeadLock();
        testDeadLock.run();
    }

    public void run() {
        MyThread m1 = new MyThread();
        new Thread(m1, "张三").start();
        new Thread(m1, "李四").start();
    }

    class MyThread implements Runnable {
        private final Object o1 = new Object();
        private final Object o2 = new Object();
        private boolean flag = true;

        @Override
        public void run() {
            if (flag) {
                flag = false;
                synchronized (o1) {
                    System.out.println(Thread.currentThread().getName() + "have o1");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o2) {
                        System.out.println(Thread.currentThread().getName() + "have o2");
                    }
                }
            } else {
                flag = true;
                synchronized (o2) {
                    System.out.println(Thread.currentThread().getName() + "have o2");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o1) {
                        System.out.println(Thread.currentThread().getName() + "have o1");
                    }
                }
            }
        }
    }
}
