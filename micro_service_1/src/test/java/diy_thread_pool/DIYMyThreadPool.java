package diy_thread_pool;

import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Date 15/6/2023 0015 上午 10:36
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Slf4j
public class DIYMyThreadPool extends ThreadPoolExecutor {
    public DIYMyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, @NotNull TimeUnit unit,
                           @NotNull DIYMyBlockingQueue<Runnable> workQueue, @NotNull ThreadFactory threadFactory,
                           @NotNull RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public DIYMyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, @NotNull TimeUnit unit,
                           @NotNull DIYMyBlockingQueue<Runnable> workQueue, @NotNull RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, Executors.defaultThreadFactory(), handler);
    }

    public DIYMyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, @NotNull TimeUnit unit,
                           @NotNull DIYMyBlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * 定义一个成员变量，用于记录当前线程池中已提交的任务数量
     */
    private final AtomicInteger submittedTaskCount = new AtomicInteger(0);

    public int getSubmittedTaskCountValue() {
        return submittedTaskCount.intValue();
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        // 执行任务之后执行的方法 当前线程池中的任务数减一
        submittedTaskCount.decrementAndGet();
        super.afterExecute(r, t);
    }

    @Override
    public void execute(@NotNull Runnable command) {
        // 提交任务 计数+1
        submittedTaskCount.incrementAndGet();
        try {
            super.execute(command);
        } catch (RejectedExecutionException e) {
            log.info("拒绝策略执行");
            // 执行拒绝策略
            DIYMyBlockingQueue<Runnable> queue = (DIYMyBlockingQueue) super.getQueue();
            try {
                // 尝试重试入队
                if (!queue.retryOffer(command, 0, TimeUnit.MILLISECONDS)) {
                    submittedTaskCount.decrementAndGet();
                    throw new RejectedExecutionException("Queue capacity is full.", e);
                }
            } catch (InterruptedException x) {
                submittedTaskCount.decrementAndGet();
                throw new RejectedExecutionException(x);
            }
            throw new RuntimeException(e);
        }
    }
}
