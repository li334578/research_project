package com.example.micro_service_2.thread_test;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName Bench
 * @Description TODO
 * @Author Wenbo Li
 * @Date 3/10/2022 上午 9:38
 * @Version 1.0
 */
@Slf4j
public class Bench {
    private int count;
    private int head;
    private int tail;
    private final String[] buffer;

    public Bench(int capacity) {
        this.count = 0;
        this.head = 0;
        this.tail = 0;
        this.buffer = new String[capacity];
    }

    public synchronized void put(String steamedBread) throws InterruptedException {
        while (count >= buffer.length) {
            wait();
        }
        buffer[tail] = steamedBread;
        this.tail = (tail + 1) % (buffer.length);
        count++;
        notifyAll();
    }

    public synchronized String take() throws InterruptedException {
        // 此处使用if判断会存在虚假唤醒问题
        while (count <= 0) {
            wait();
        }
        String steamedBread = buffer[head];
        head = (head + 1) % buffer.length;
        count--;
        return steamedBread;
    }
}
