package com.company.micro_service_1.config;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Classname MyThreadPoolExecutor
 * @Description TODO
 * @Version 1.0.0
 * @Date 21/5/2022 下午 7:33
 * @Created by 李文博
 */
@Slf4j
public class MyThreadPoolExecutor extends ThreadPoolExecutor {


    private MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }
}
