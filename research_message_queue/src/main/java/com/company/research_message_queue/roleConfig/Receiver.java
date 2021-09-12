package com.company.research_message_queue.roleConfig;

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
    void process(String message);

    /**
     * 接收处理消息
     */
    void process(Message message);
}
