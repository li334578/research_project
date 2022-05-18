package com.company.micro_service_1.bean;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Classname MyBlockingQueue
 * @Description 自定义队列实现
 * @Version 1.0.0
 * @Date 18/5/2022 下午 8:18
 * @Created by 李文博
 */
@Slf4j
public class MyBlockingQueue <R extends Runnable> extends LinkedBlockingDeque<Runnable> {
}
