package com.company.micro_service_1;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.company.micro_service_1.bean.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
class MicroService1ApplicationTests {
    @Resource
    private Redisson redisson;

    @Test
    void contextLoads() {
    }

    @Test
    public void testMethod1() {
        Snowflake snowflake = IdUtil.createSnowflake(1L, 1L);
        System.out.println(snowflake.nextId());
    }

    @Test
    public void testMethod2() {
        RBucket<String> nameRBucket = redisson.getBucket("name");
        // 只设置value，key不过期
        nameRBucket.set("四哥");
        // 设置value和key的有效期
        nameRBucket.set("四哥", 30, TimeUnit.SECONDS);
        // 通过key获取value
        System.out.println(redisson.getBucket("name").get());
    }

    @Test
    public void testMethod3() {
        // 理解join例子 三个线程 t1 t2 t3 按照 t3 t2 t1的顺序执行
        Thread t3 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            System.out.println(Thread.currentThread().getName() + " end");
        }, "t3");
        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            System.out.println(Thread.currentThread().getName() + " end");
        }, "t2");
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            System.out.println(Thread.currentThread().getName() + " end");
        }, "t1");

        try {
            t3.start();
            t2.start();
            t1.start();
            t3.join();
            t2.join();
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("请输入数字");
        Scanner scanner = new Scanner(System.in);

        String inputValue = scanner.nextLine();
        String[] split = inputValue.split("");
        Map<Integer, Integer> map = Arrays.stream(split).reduce(new HashMap<>(), (h1, v1) -> {
            Integer intV1 = Integer.valueOf(v1);
            Integer nextValue = h1.getOrDefault(intV1, 0) + 1;
            h1.put(intV1, nextValue);
            return h1;
        }, (k1, k2) -> k1);
        for (int i = 0; i < 10; i++) {
            System.out.println(i + ":" + map.getOrDefault(i, 0));
        }
    }

    @Test
    public void testMethod4() {
        RBucket<Product> myValue = redisson.getBucket("myValue");
        if (Objects.isNull(myValue.get())) {
            Product product = new Product(1L, "test1", 10, "备注备注");
            myValue.set(product);
        }
        RBucket<Product> myValue2 = redisson.getBucket("myValue");
        myValue.set(null);
        log.info("1231");
    }
}
