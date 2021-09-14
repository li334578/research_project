package com.company.research_message_queue.roleConfig;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-09-09 21:10:29
 */
public interface Sender {

    /*
     * 发送消息到MQ的指定queue
     * */
    void sendMessage(String queueName, String message);

    /*
     * 发送消息到MQ的指定queue
     * */
    void sendMessage(String virtualHost, String queueName, String message);
}
