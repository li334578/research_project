package com.company.research_message_queue.config;

import com.company.research_message_queue.constant.Constant;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-09-09 20:57:07
 */
@Configuration
@EnableRabbit
public class RabbitmqConfig {

    // 创建一个队列
    @Bean
    public Queue wenQueue() {
        return new Queue("Li.Wen.Bo_queue");
    }
}
