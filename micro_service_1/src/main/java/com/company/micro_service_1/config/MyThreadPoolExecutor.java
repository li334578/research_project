package com.company.micro_service_1.config;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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

    /**
     * @param corePoolSize    核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime   非核心线程存活时间 数值
     * @param unit            非核心线程存活时间 单位
     * @param workQueue       任务队列
     *
     *                        正常线程池任务队列 新任务被提交到线程池中
     *                        1.核心线程数量未达到最大值 先创建核心线程用于执行任务
     *                        2.核心线程池已满 会将任务丢入任务队列中排队
     *                        3.任务队列未满 等待核心线程池处理
     *                        4.任务队列已满 创建非核心线程处理
     *                        5.非核心线程已达到最大值 执行拒绝策略
     *
     *                        本线程池队列  新任务被提交到线程池中
     *                        1.核心线程数未达到最大值 先创建核心线程用于执行任务
     *                        2.核心线程池已满 非核心线程池未满 会直接创建非核心线程执行任务
     *                        3.核心线程池已满 非核心线程池已满 将任务丢入任务队列
     *                        4.任务队列未满 等待核心线程池处理
     *                        5.任务队列已满 执行拒绝策略
     * @param threadFactory   线程工厂
     * @param handler         拒绝策略
     * @return 线程池Executor对象
     */
    public static MyThreadPoolExecutor getInstance(int corePoolSize, int maximumPoolSize,
                                                   long keepAliveTime, TimeUnit unit,
                                                   MyBlockingQueue<Runnable> workQueue,
                                                   ThreadFactory threadFactory,
                                                   RejectedExecutionHandler handler) {
        MyThreadPoolExecutor myThreadPoolExecutor = new MyThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, unit, workQueue, threadFactory, handler);
        workQueue.setMyThreadPoolExecutor(myThreadPoolExecutor);
        return myThreadPoolExecutor;
    }

    /**
     * 定义一个成员变量，用于记录当前线程池中已提交的任务数量
     */
    private final AtomicInteger submittedTaskCount = new AtomicInteger(0);
}
