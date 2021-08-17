package com.company.sharding_sphere.other;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-05-25 20:31:46
 */
public class ThreadTest {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread thread = new Thread(myThread);
        // 1 - 10
        thread.setPriority(10);
    }

    static class MyThread implements Runnable {
        @Override
        public void run() {

        }
    }
}
