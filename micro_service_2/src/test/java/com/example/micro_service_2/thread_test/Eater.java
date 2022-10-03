package com.example.micro_service_2.thread_test;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @ClassName EaterThread
 * @Description TODO
 * @Author Wenbo Li
 * @Date 3/10/2022 上午 9:55
 * @Version 1.0
 */
@Slf4j
public class Eater extends Thread {


    private final Bench bench;
    private final Random random;

    public Eater(String name,Bench bench, long seed) {
        super(name);
        this.bench = bench;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(random.nextInt(1000));
                String steamedBread = bench.take();
                log.info(Thread.currentThread().getName() + "取出" + steamedBread);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
