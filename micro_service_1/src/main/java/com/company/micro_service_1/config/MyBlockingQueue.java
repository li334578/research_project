package com.company.micro_service_1.config;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Classname MyBlockingQueue
 * @Description 自定义队列实现
 * @Version 1.0.0
 * @Date 18/5/2022 下午 8:18
 * @Created by 李文博
 */
@Slf4j
public class MyBlockingQueue <R extends Runnable> extends LinkedBlockingDeque<Runnable> {

    private MyThreadPoolExecutor myThreadPoolExecutor;


    /**
     * 队列中堆积的任务超过该值后 会创建非核心线程执行任务用于降低系统负载
     */
    private Integer criticalValue;

    public void setCriticalValue(int criticalValue) {
        this.criticalValue = criticalValue;
    }

    public void setMyThreadPoolExecutor(MyThreadPoolExecutor myThreadPoolExecutor) {
        this.myThreadPoolExecutor = myThreadPoolExecutor;
    }

    public MyBlockingQueue(int capacity) {
        super(capacity);
    }
}
