package com.company.research_message_queue.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-09-09 21:02:42
 */
public class Constant {

    // rabbitmq 队列名称
    @Value("${rabbitmq.queue.name}")
    public static String QUEUE_NAME;

}
