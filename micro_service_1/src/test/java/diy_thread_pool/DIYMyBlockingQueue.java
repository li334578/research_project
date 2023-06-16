package diy_thread_pool;

import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Date 15/6/2023 0015 上午 10:37
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Slf4j
public class DIYMyBlockingQueue<R extends Runnable> extends LinkedBlockingDeque<Runnable> {

    private DIYMyBlockingQueue(int capacity) {
        super(capacity);
    }

    public static DIYMyBlockingQueue getInstance(Integer criticalValue, Integer capacity) {
        DIYMyBlockingQueue<Runnable> queue = new DIYMyBlockingQueue<>(capacity);
        queue.setCriticalValue(criticalValue);
        return queue;
    }

    private DIYMyThreadPool diyMyThreadPool;

    /**
     * 队列中堆积的任务超过该值后 会创建非核心线程执行任务用于降低系统负载
     */
    private Integer criticalValue;

    public void setCriticalValue(int criticalValue) {
        this.criticalValue = criticalValue;
    }

    public void setDiyMyThreadPool(DIYMyThreadPool diyMyThreadPool) {
        this.diyMyThreadPool = diyMyThreadPool;
    }


    @Override
    public boolean offer(@NotNull Runnable runnable) {
        if (Objects.isNull(diyMyThreadPool)) throw new NullPointerException("thread pool is null");
        if (diyMyThreadPool.getSubmittedTaskCountValue() < diyMyThreadPool.getCorePoolSize()) {
            // 有空闲线程
            log.info("有空闲线程...");
            return super.offer(runnable);
        }
        // 没有空闲线程了
        log.info("没有空闲线程");
        if (diyMyThreadPool.getSubmittedTaskCountValue() - diyMyThreadPool.getCorePoolSize() < criticalValue) {
            // 任务没有堆积 入队处理
            log.info("任务没有堆积");
            return super.offer(runnable);
        }
        // 任务堆积了
        log.info("任务堆积...");
        if (diyMyThreadPool.getPoolSize() < diyMyThreadPool.getMaximumPoolSize()) {
            // 还未达到最大线程
            log.info("创建线程");
            return false;
        }
        // 任务也堆积了 线程也创建满了 入队排队处理
        log.info("线程已创建满,入队排队处理");
        return super.offer(runnable);
    }

    public boolean retryOffer(Runnable runnable, long timeout, TimeUnit unit) throws InterruptedException {
        log.info("retryOffer 执行了 ...");
        if (diyMyThreadPool.isShutdown()) {
            throw new RejectedExecutionException("Executor is shutdown!");
        }
        return super.offer(runnable, timeout, unit);
    }
}
