package com.company.research_message_queue.controller;

import com.company.research_message_queue.roleConfig.Sender;
import org.springframework.amqp.core.Queue;
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
@RequestMapping("/fanout")
public class FanoutSendController {
    @Resource(name = "senderRabbitMQForFanout")
    private Sender sender;

    @Resource(name = "queueWenbo")
    private Queue queueWenbo;

    @Resource(name = "queueLWB")
    private Queue queueLWB;

    private static final String virtualHost = "myDemoRabbitTemplate";


    @RequestMapping(value = "/send1", method = RequestMethod.GET)
    public String send2(@RequestParam("msg") String msg) {
        sender.sendMessage(virtualHost, queueWenbo.getName(), msg);
        return "消息：" + msg + ",已发送";
    }


    @RequestMapping(value = "/send2", method = RequestMethod.GET)
    public String send3(@RequestParam("msg") String msg) {
        sender.sendMessage(virtualHost, queueLWB.getName(), msg);
        return "消息：" + msg + ",已发送";
    }

}
