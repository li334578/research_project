package com.example.micro_service_2.thread_test;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName Main
 * @Description TODO
 * @Author Wenbo Li
 * @Date 3/10/2022 上午 10:01
 * @Version 1.0
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        Bench bench = new Bench(10);
        Cooker cooker = new Cooker("厨师1", bench);

        Eater eater1 = new Eater("饭桶1", bench, 300L);
        Eater eater2 = new Eater("饭桶2", bench, 500L);
        cooker.start();
        eater1.start();
        eater2.start();
//        log.info(String.valueOf(10 + 1 % 5));
//        log.info(String.valueOf((10 + 1) % 5));
    }
}
