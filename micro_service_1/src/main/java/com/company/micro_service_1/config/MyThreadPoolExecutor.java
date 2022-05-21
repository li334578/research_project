package com.company.micro_service_1.config;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Classname MyThreadPoolExecutor
 * @Description TODO
 * @Version 1.0.0
 * @Date 21/5/2022 下午 7:33
 * @Created by 李文博
 */
@Slf4j
public class MyThreadPoolExecutor extends ThreadPoolExecutor {


    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
}
