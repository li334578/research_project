package com.company.research_message_queue.roleConfig;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-09-09 21:01:40
 */
@Component
public class SenderRabbitMQForTopic implements Sender {

    @Resource(name = "rabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    @Resource(name = "myDemoRabbitTemplate")
    private RabbitTemplate myDemoRabbitTemplate;

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 发送消息到rabbitMQ
     */
    @Override
    public void sendMessage(String queueName, String message) {
        myDemoRabbitTemplate.convertAndSend(queueName, message);
    }

    @Override
    public void sendMessage(String virtualHost, String queueName, String message) {
        RabbitTemplate virtualHostRabbitTemplate = applicationContext.getBean(virtualHost, RabbitTemplate.class);
        sendMessage(virtualHostRabbitTemplate, queueName, message);
    }

    public void sendMessage(RabbitTemplate myRabbitTemplate, String queueName, String message) {
        myRabbitTemplate.convertAndSend("topicExchange", queueName, message);
//        myRabbitTemplate.convertAndSend("", "",queueName, message1 -> {
//            message1.getMessageProperties().setHeader("ta", "测试");
//            message1.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
//            return message1;
//        });
        // 消息发送失败返回到队列中, application.properties 配置 spring.rabbitmq.publisher-returns=true
        myRabbitTemplate.setMandatory(true);

        myRabbitTemplate.setReturnCallback((message1, replyCode, replyText, exchange, routingKey) -> {
            System.out.println(message1);
            System.out.println(replyCode);
            System.out.println(replyText);
            System.out.println(exchange);
            System.out.println(routingKey);
        });

        myRabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                System.out.println("HelloSender 发送失败：" + cause + correlationData.toString());
            } else {
                System.out.println("HelloSender 发送成功");
            }
        });
    }
}
