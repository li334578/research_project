package com.company.micro_service_1.controller;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * @Classname ConcurrentHashMapController
 * @Description TODO
 * @Version 1.0.0
 * @Date 2/6/2022 下午 9:31
 * @Created by 李文博
 */
public class ConcurrentHashMapController {

    // 线程数
    private static final int THREAD_COUNT = 10;

    // 元素个数
    private static final int ITEM_COUNT = 1000;

    //循环次数
    private static final int LOOP_COUNT = 10000000;


    private ConcurrentHashMap<String, Long> getData(int count) {
        return LongStream.rangeClosed(1, count).boxed().collect(Collectors.toConcurrentMap(
                i -> UUID.randomUUID().toString(),
                Function.identity(),
                ((o1, o2) -> o1),
                ConcurrentHashMap::new
        ));
    }
}
