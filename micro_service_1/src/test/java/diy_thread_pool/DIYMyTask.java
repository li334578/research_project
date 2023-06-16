package diy_thread_pool;

import lombok.extern.slf4j.Slf4j;

/**
 * @Date 15/6/2023 0015 上午 11:09
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Slf4j
public class DIYMyTask implements Runnable {
    private Integer num;

    public DIYMyTask(Integer num) {
        this.num = num;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("{} 任务执行了...", num);
    }
}
