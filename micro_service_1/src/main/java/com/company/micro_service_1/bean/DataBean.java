package com.company.micro_service_1.bean;

import lombok.Getter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Classname DataBean
 * @Description TODO
 * @Version 1.0.0
 * @Date 6/5/2022 下午 2:49
 * @Created by 李文博
 */
public class DataBean {
    @Getter
    private static int counter = 0;
    private static final Lock lock = new ReentrantLock();

    public void add() {
        try {
            lock.lock();
            counter++;
        } finally {
            lock.unlock();
        }
    }
}
