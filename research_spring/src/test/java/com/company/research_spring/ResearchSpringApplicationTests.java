package com.company.research_spring;

import com.company.research_spring.anno.RoutingInjected;
import com.company.research_spring.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
class ResearchSpringApplicationTests {

    @RoutingInjected
    private HelloService helloService;

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

    @Test
    void testMethod2() {
        helloService.sayHello();
        helloService.sayHi();
    }
}
