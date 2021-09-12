package com.company.research_message_queue.roleConfig;

import com.company.research_message_queue.constant.Constant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-09-09 21:01:40
 */
@Component
public class SenderRabbitMQ implements Sender {

    @Resource
    private RabbitTemplate rabbitTemplate;


    /**
     * 发送消息到rabbitMQ
     */
    @Override
    public void sendMessage(String queueName, String message) {
        rabbitTemplate.convertAndSend(queueName, message);
    }

}
