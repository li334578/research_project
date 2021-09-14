package com.company.research_message_queue.roleConfig;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description : 消息接收者
 * @date : 2021-09-09 21:14:10
 */
public interface Receiver {

    /**
     * 接收处理消息
     */
    void process1(String context, Message message, Channel channel);

    /**
     * 接收处理消息
     */
    void process2(String context, Message message, Channel channel);


    /**
     * 接收处理消息
     */
    void process3(String context, Message message, Channel channel);

    /**
     * 接收处理消息
     */
    void process4(String context, Message message, Channel channel);


    /**
     * 接收处理消息
     */
    void process5(String context, Message message, Channel channel);

    /**
     * 接收处理消息
     */
    void process6(String context, Message message, Channel channel);

    /**
     * 接收处理消息
     */
    void process7(String context, Message message, Channel channel);

    /**
     * 接收处理消息
     */
    void process8(String context, Message message, Channel channel);
}
