package com.company.micro_service_1.bean;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Classname ProductItem
 * @Description 商品item
 * @Version 1.0.0
 * @Date 6/5/2022 下午 2:57
 * @Created by 李文博
 */
@Data
@RequiredArgsConstructor
@Accessors(chain = true)
public class ProductItem {
    // 商品名
    private String name;
    // 库存
    private int remaining = 1000;
    // 每个商品都有一把锁来确保自己的库存不会出现并发问题
    @ToString.Exclude
    private Lock lock = new ReentrantLock();
}
