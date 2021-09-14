package com.company.research_message_queue.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-09-09 20:57:07
 */
@Configuration
@EnableRabbit
public class RabbitmqConfig {

    /**
     * {@link org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration}
     * 自动发送JSON结构或消费时自动将JSON转成相应的对象
     *
     * @return mq 消息序列化工具
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 默认的RabbitTemplate，vhost:/
     */
    @Bean("defaultConnectionFactory")
    @Primary
    public ConnectionFactory defaultConnectionFactory(MyRabbitProperties myRabbitProperties) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(myRabbitProperties.getHost());
        cachingConnectionFactory.setPort(myRabbitProperties.getPort());
        cachingConnectionFactory.setUsername(myRabbitProperties.getUsername());
        cachingConnectionFactory.setPassword(myRabbitProperties.getPassword());
        cachingConnectionFactory.setVirtualHost("/");
        return cachingConnectionFactory;
    }


    /**
     * RabbitTemplate，vhost:/wenbo09
     */
    @Bean("myDemoConnectionFactory")
    public ConnectionFactory myDemoConnectionFactory(MyRabbitProperties myRabbitProperties) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(myRabbitProperties.getHost());
        cachingConnectionFactory.setPort(myRabbitProperties.getPort());
        cachingConnectionFactory.setUsername(myRabbitProperties.getUsername());
        cachingConnectionFactory.setPassword(myRabbitProperties.getPassword());
        cachingConnectionFactory.setVirtualHost("wenbo09");
        return cachingConnectionFactory;
    }


    @Bean("rabbitTemplate")
    @Primary
    public RabbitTemplate rabbitTemplate(@Qualifier("defaultConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }


    @Bean("myDemoRabbitTemplate")
    public RabbitTemplate myDemoRabbitTemplate(@Qualifier("myDemoConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }


    /**
     * 默认(v-host:/)的ListenerContainer
     */
    @Bean(name = "defaultRabbitListenerContainer")
    public SimpleRabbitListenerContainerFactory defaultRabbitListenerContainer(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                                               @Qualifier("defaultConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }


    /**
     * /wenbo09 v-host的ListenerContainer
     *
     * @RabbitListener(containerFactory = "myDemoRabbitListenerContainer") 使用
     */
    @Bean("myDemoRabbitListenerContainer")
    public SimpleRabbitListenerContainerFactory myDemoRabbitListenerContainer(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                                              @Qualifier("myDemoConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public RabbitAdmin defaultRabbitAdmin(@Qualifier("rabbitTemplate") RabbitTemplate rabbitTemplate) {
        //需要传入
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }


    @Bean
    public RabbitAdmin myDemoRabbitAdmin(@Qualifier("myDemoRabbitTemplate") RabbitTemplate rabbitTemplate) {
        //需要传入
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }
}
