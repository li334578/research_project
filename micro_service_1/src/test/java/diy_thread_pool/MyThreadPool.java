package diy_thread_pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Date 15/6/2023 0015 上午 10:27
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Slf4j
public class MyThreadPool extends ThreadPoolExecutor {

    public MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                        BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                        RejectedExecutionHandler rejectedExecutionHandler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, rejectedExecutionHandler);
    }


    /**
     * @param corePoolSize    核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime   非核心线程存活时间 数值
     * @param unit            非核心线程存活时间 单位
     * @param workQueue       任务队列
     *                        <p>
     *                        正常线程池任务队列 新任务被提交到线程池中
     *                        1.核心线程数量未达到最大值 先创建核心线程用于执行任务
     *                        2.核心线程池已满 会将任务丢入任务队列中排队
     *                        3.任务队列未满 等待核心线程池处理
     *                        4.任务队列已满 创建非核心线程处理
     *                        5.非核心线程已达到最大值 执行拒绝策略
     *                        <p>
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
    public static MyThreadPool getInstance(int corePoolSize, int maximumPoolSize,
                                           long keepAliveTime, TimeUnit unit,
                                           MyBlockingQueue<Runnable> workQueue,
                                           ThreadFactory threadFactory,
                                           RejectedExecutionHandler handler) {
        MyThreadPool myThreadPool = new MyThreadPool(corePoolSize, maximumPoolSize,
                keepAliveTime, unit, workQueue, threadFactory, handler);
        workQueue.setMyThreadPool(myThreadPool);
        return myThreadPool;
    }

    /**
     * 定义一个成员变量，用于记录当前线程池中已提交的任务数量
     */
    private final AtomicInteger submittedTaskCount = new AtomicInteger(0);

    public int getSubmittedTaskCount() {
        return submittedTaskCount.get();
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        // 执行任务之后执行的方法 当前线程池中的任务数减一
        submittedTaskCount.decrementAndGet();
    }

    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }
        // 已提交任务数加一
        submittedTaskCount.incrementAndGet();
        try {
            super.execute(command);
        } catch (RejectedExecutionException e) {
            log.warn("reject {}", submittedTaskCount.get());
            // 执行拒绝策略
            final MyBlockingQueue queue = (MyBlockingQueue) super.getQueue();
            try {
                if (!queue.retryOffer(command, 0, TimeUnit.MILLISECONDS)) {
                    submittedTaskCount.decrementAndGet();
                    throw new RejectedExecutionException("Queue capacity is full.", e);
                }
            } catch (InterruptedException x) {
                submittedTaskCount.decrementAndGet();
                throw new RejectedExecutionException(x);
            }
        }
    }
}
