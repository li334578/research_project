package com.company.research_message_queue.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:配置广播模式或者订阅模式队列
 * <p>
 * Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。
 *
 * @author hekuangsheng
 * @create 2017-10-16 16:47
 **/
@Configuration
public class RabbitFanoutConfig {

    final static String WENBO = "fanout.wenbo.net";

    final static String LWB = "fanout.lwb.com";

    @Bean
    public Queue queueWenbo() {
        return new Queue(RabbitFanoutConfig.WENBO);
    }

    @Bean
    public Queue queueLWB() {
        return new Queue(RabbitFanoutConfig.LWB);
    }

    /**
     * 任何发送到Fanout Exchange的消息都会被转发到与该Exchange绑定(Binding)的所有队列上。
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingExchangeQueueWenBo(@Qualifier("queueWenbo") Queue queueWenbo,
                                      @Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueWenbo).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeQueueLWB(@Qualifier("queueLWB") Queue queueLWB,
                                    @Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueLWB).to(fanoutExchange);
    }

}
