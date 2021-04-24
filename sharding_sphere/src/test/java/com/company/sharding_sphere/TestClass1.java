package com.company.sharding_sphere;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-04-21 21:08:28
 */
@SpringBootTest
@Slf4j
public class TestClass1 {

    @Test
    public void testMethod1() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 10,
                TimeUnit.MICROSECONDS, new LinkedBlockingQueue<Runnable>());
        Integer sum = 0;
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 1; i < 10000; i += 10) {
            Future<Integer> future
                    = threadPoolExecutor.submit(new MyTask1(i, countDownLatch));
            try {
                sum += future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("result -> " + sum);
    }
}


class MyTask1 implements Callable<Integer> {

    private Integer startNum;
    private CountDownLatch countDownLatch;

    public MyTask1(Integer startNum, CountDownLatch countDownLatch) {
        this.startNum = startNum;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public Integer call() {
        System.out.println(Thread.currentThread().getName()
                + String.format("开始计算%d---->%d", startNum, startNum + 1000));
        int sum = 0;
        for (int i = startNum; i < startNum + 10; i++) {
            sum += i;
        }
        countDownLatch.countDown();
        return sum;
    }
}