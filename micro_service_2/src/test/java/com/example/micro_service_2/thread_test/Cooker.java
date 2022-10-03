package com.example.micro_service_2.thread_test;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName Cooker
 * @Description TODO
 * @Author Wenbo Li
 * @Date 3/10/2022 上午 9:47
 * @Version 1.0
 */
@Slf4j
public class Cooker extends Thread {

    private final Bench bench;

    private int id;

    public Cooker(String name,Bench bench) {
        super(name);
        this.bench = bench;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(400L);
                String steamedBread = "steamedBread[" + nextId() + "]";
                bench.put(steamedBread);
                log.info(Thread.currentThread().getName() + "生产" + steamedBread);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized int nextId() {
        return id++;
    }
}
