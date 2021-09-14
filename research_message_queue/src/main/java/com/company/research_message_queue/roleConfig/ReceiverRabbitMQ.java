package com.company.research_message_queue.roleConfig;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-09-09 21:16:52
 */
@Component
public class ReceiverRabbitMQ implements Receiver {

    private Logger logger = LoggerFactory.getLogger(ReceiverRabbitMQ.class);

    /**
     * 接收处理消息
     */
    @Override
    @RabbitListener(containerFactory = "myDemoRabbitListenerContainer", queues = "Li.Wen.Bo_queue")
    public void process1(String context, Message message, Channel channel) {
        System.out.println("Li.Wen.Bo_queue" + message);
        process(context, message, channel);
    }

    /**
     * 接收处理消息
     */
    @Override
    @RabbitListener(containerFactory = "myDemoRabbitListenerContainer", queues = "direct")
    public void process2(String context, Message message, Channel channel) {
        System.out.println("direct" + message);
        process(context, message, channel);
    }

    /**
     * 接收处理消息
     */
    @Override
    @RabbitListener(containerFactory = "myDemoRabbitListenerContainer", queues = "hello")
    public void process3(String context, Message message, Channel channel) {
        System.out.println("hello" + message);
        process(context, message, channel);
    }

    /**
     * 接收处理消息
     */
    @Override
    @RabbitListener(containerFactory = "myDemoRabbitListenerContainer", queues = "topic.message")
    public void process4(String context, Message message, Channel channel) {
        System.out.println("topic.message" + message);
        process(context, message, channel);
    }

    /**
     * 接收处理消息
     */
    @Override
    @RabbitListener(containerFactory = "myDemoRabbitListenerContainer", queues = "topic.message.s")
    public void process5(String context, Message message, Channel channel) {
        System.out.println("topic.message.s" + message);
        process(context, message, channel);
    }

    /**
     * 接收处理消息
     */
    @Override
    @RabbitListener(containerFactory = "myDemoRabbitListenerContainer", queues = "topic.ymq")
    public void process6(String context, Message message, Channel channel) {
        System.out.println("topic.ymq" + message);
        process(context, message, channel);
    }

    /**
     * 接收处理消息
     */
    @Override
    @RabbitListener(containerFactory = "myDemoRabbitListenerContainer", queues = "fanout.wenbo.net")
    public void process7(String context, Message message, Channel channel) {
        System.out.println("fanout.wenbo.net" + message);
        process(context, message, channel);
    }

    /**
     * 接收处理消息
     */
    @Override
    @RabbitListener(containerFactory = "myDemoRabbitListenerContainer", queues = "fanout.lwb.com")
    public void process8(String context, Message message, Channel channel) {
        System.out.println("fanout.lwb.com" + message);
        process(context, message, channel);
    }

    public void process(String context, Message message, Channel channel) {
        logger.info(" 监听到消息内容：{}", context);
        try {
            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            //消息确认  因为我在属性配置文件里面开启了ACK确认 所以如果代码没有执行ACK确认 你在RabbitMQ的后台会看到消息会一直留在队列里面未消费掉 只要程序一启动开始接受该队列消息的时候 又会收到
            logger.info(" 消息接收成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(" 消息接收失败");
            // ack返回false，并重新放回队列
            try {
                logger.info(" ack返回false，并重新放回队列");
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }


        }

    }
}
