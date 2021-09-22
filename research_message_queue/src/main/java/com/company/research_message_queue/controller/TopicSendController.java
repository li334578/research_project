package com.company.research_message_queue.controller;

import com.company.research_message_queue.roleConfig.Sender;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-09-12 14:31:00
 */
@RestController
@RequestMapping("/topic")
public class TopicSendController {
    @Resource(name = "senderRabbitMQForTopic")
    private Sender sender;

    @Resource(name = "queueMessage")
    private Queue queueMessage;

    @Resource(name = "queueMessages")
    private Queue queueMessages;

    @Resource(name = "queueYmq")
    private Queue queueYmq;

    private static final String virtualHost = "myDemoRabbitTemplate";

    @RequestMapping(value = "/send1", method = RequestMethod.GET)
    public String send1(@RequestParam("msg") String msg) {
        sender.sendMessage(virtualHost, queueYmq.getName(), msg);
        return "消息：" + msg + ",已发送";
    }


    @RequestMapping(value = "/send2", method = RequestMethod.GET)
    public String send2(@RequestParam("msg") String msg) {
        sender.sendMessage(virtualHost, queueMessage.getName(), msg);
        return "消息：" + msg + ",已发送";
    }


    @RequestMapping(value = "/send3", method = RequestMethod.GET)
    public String send3(@RequestParam("msg") String msg) {
        sender.sendMessage(virtualHost, queueMessages.getName(), msg);
        return "消息：" + msg + ",已发送";
    }

}
