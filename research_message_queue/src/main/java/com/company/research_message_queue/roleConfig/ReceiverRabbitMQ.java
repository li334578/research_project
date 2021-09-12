package com.company.research_message_queue.roleConfig;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-09-09 21:16:52
 */
@Component

public class ReceiverRabbitMQ implements Receiver {

    /**
     * 接收处理消息
     */
    @Override
    @RabbitHandler
    public void process(String message) {
        System.out.println(message);
    }

    /**
     * 接收处理消息
     */
    @Override
    @RabbitListener(queues = "Li.Wen.Bo_queue")
    public void process(Message message) {
        System.out.println(message);
    }
}
