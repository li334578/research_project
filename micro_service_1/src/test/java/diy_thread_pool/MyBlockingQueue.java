package diy_thread_pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Date 15/6/2023 0015 上午 10:30
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Slf4j
public class MyBlockingQueue<R extends Runnable> extends LinkedBlockingDeque<Runnable> {

    private MyThreadPool myThreadPool;

    /**
     * 队列中堆积的任务超过该值后 会创建非核心线程执行任务用于降低系统负载
     */
    private Integer criticalValue;

    public void setCriticalValue(int criticalValue) {
        this.criticalValue = criticalValue;
    }

    public void setMyThreadPool(MyThreadPool myThreadPool) {
        this.myThreadPool = myThreadPool;
    }

    public MyBlockingQueue(int capacity) {
        super(capacity);
    }

    @Override
    public boolean offer(Runnable runnable) {
        if (myThreadPool == null) {
            throw new NullPointerException("threadPool is null");
        }
        // 线程池的当前线程数
        int currentPoolThreadSize = myThreadPool.getPoolSize();
        if (myThreadPool.getSubmittedTaskCount() < currentPoolThreadSize) {
            // 已提交的任务数量小于当前线程数，意味着线程池中有空闲线程，直接扔进队列里，让线程去处理
            return super.offer(runnable);
        }

        // 可以考虑判断当前任务数大于 criticalValue 再去创建新的非核心线程处理任务
        if (criticalValue != null && myThreadPool.getSubmittedTaskCount() < criticalValue) {
            // 已提交任务数量小于临界值 扔进队列交给核心线程处理
            return super.offer(runnable);
        }

        // return false to let executor create new worker.
        if (currentPoolThreadSize < myThreadPool.getMaximumPoolSize()) {
            // 重点： 当前线程数小于 最大线程数 ，返回false，暗含入队失败，让线程池去创建新的线程
            log.error("当前线程数{}", currentPoolThreadSize);
            return false;
        }
        // 重点: 代码运行到此处，说明当前线程数 >= 最大线程数，需要真正的提交到队列中
        return super.offer(runnable);
    }

    public boolean retryOffer(Runnable runnable, long timeout, TimeUnit unit) throws InterruptedException {
        if (myThreadPool.isShutdown()) {
            throw new RejectedExecutionException("Executor is shutdown!");
        }
        return super.offer(runnable, timeout, unit);
    }
}
