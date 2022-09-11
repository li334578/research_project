package com.company.micro_service_1.thread_test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
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

        phaser.awaitAdvance(0);
        log.info("end");
    }

    @Test
    public void testMethod2() {
        Phaser phaser = new Phaser();
        for (int i = 0; i < 5; i++) {
            phaser.register();
            new Thread(() -> {
                phaser.arriveAndAwaitAdvance();
                log.info("register");
            }).start();
        }
        log.info(" go go go ");
    }

    @Test
    public void testMethod3() {
        Phaser phaser = new Phaser(2);

        phaser.bulkRegister(3);

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                phaser.arriveAndAwaitAdvance();
                log.info("111");
            });
        }

        log.info("" + phaser.getArrivedParties());
        log.info("" + phaser.getUnarrivedParties());
    }
}
