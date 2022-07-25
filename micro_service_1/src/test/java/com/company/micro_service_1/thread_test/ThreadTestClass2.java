package com.company.micro_service_1.thread_test;

import com.company.micro_service_1.MicroService1Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.Phaser;

@SpringBootTest
@Slf4j
public class ThreadTestClass2 {

    @Test
    public void testMethod1() {
        Phaser phaser = new Phaser(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                log.info("execute ...");
                phaser.arrive();
            }).start();
        }

        phaser.awaitAdvance(5);
        log.info("end");
    }
}
