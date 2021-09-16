package com.company.research_message_queue.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 描述: 配置默认的交换机模式
 * <p>
 * Direct Exchange是RabbitMQ默认的交换机模式，也是最简单的模式，根据key全文匹配去寻找队列。
 **/
@Configuration
public class RabbitDirectConfig {

    @Resource(name = "myDemoRabbitAdmin")
    private RabbitAdmin rabbitAdmin;

    @Bean
    public Queue helloQueue() {
        Queue hello = new Queue("hello");
        rabbitAdmin.declareQueue(hello);
        return hello;
    }

    @Bean
    public Queue directQueue() {
        Queue direct = new Queue("direct");
        rabbitAdmin.declareQueue(direct);
        return direct;
    }

    // 创建一个队列
    @Bean
    public Queue wenQueue() {
        Queue queue = new Queue("Li.Wen.Bo_queue");
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    //-------------------配置默认的交换机模式，可以不需要配置以下-----------------------------------
    /*@Bean
    DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    //绑定一个key "direct"，当消息匹配到就会放到这个队列中
    @Bean
    Binding bindingExchangeDirectQueue(Queue directQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue).to(directExchange).with("direct");
    }*/
    // 推荐使用 helloQueue（） 方法写法，这种方式在 Direct Exchange 模式 多此一举，没必要这样写
    //---------------------------------------------------------------------------------------------
}
