package com.company.micro_service_1.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Classname CompareBean
 * @Description 比较累加类
 * @Version 1.0.0
 * @Date 6/5/2022 下午 2:10
 * @Created by 李文博
 */
@Data
@Slf4j
public class CompareBean {
    private volatile int a;
    private volatile int b;

    public void add() {
        log.warn("add method is launch");
        for (int i = 0; i < 1000000; i++) {
            a++;
            b++;
        }
        log.warn("add method is end");
    }

    public void compare() {
        log.warn("compare method is launch");
        for (int i = 0; i < 1000000; i++) {
            // a<b这种操作在字节码层面是先拿到a的值再拿到b的值再进行比较三步操作
            // 中间可能会穿插a++或者b++的语句执行
            if (a < b) {
                log.warn("a is {} b is {} a > b is {}", a, b, a > b);
            }
        }
        log.warn("compare method is end");
    }

}
