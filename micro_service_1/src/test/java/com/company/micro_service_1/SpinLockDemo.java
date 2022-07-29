package com.company.micro_service_1;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Date 13/6/2022 0013 下午 4:00
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Slf4j
public class SpinLockDemo {
    private static AtomicReference<Thread> atomicReference = new AtomicReference<>(null);

    AtomicStampedReference<Thread> atomicStampedReference = new AtomicStampedReference<>(null,1);

    public static void lock() {
        Thread thread = Thread.currentThread();
        // 自旋抢占
        log.info("{} come in", thread.getName());
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    public static void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        log.info("{} task over", thread.getName());
    }

    public static void main(String[] args) {

        new Thread(() -> {
            lock();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            unlock();
        }, "A").start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            lock();
            log.info("B 抢锁成功");
            unlock();
        }, "B").start();
    }
}
