package com.company.research_spring;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ResearchSpringApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testMethod1() {
        Object o = new Object();
        log.info(ClassLayout.parseInstance(o).toPrintable());
        synchronized (o) {
            log.info(ClassLayout.parseInstance(o).toPrintable());
        }
    }
}
