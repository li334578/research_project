package com.company.micro_service_1.config;

import com.company.micro_service_1.controller.dto.MyConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Date 8/3/2022 0008 下午 4:28
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Slf4j
//@Configuration
public class MyProducer {
    /**
     * 创建普通消息发送者实例
     */
//    @Bean
//    public DefaultMQProducer defaultProducer() throws MQClientException {
//        log.info("defaultProducer 正在创建-------------------" + MyConstants.GROUP_NAME + "--------------------");
//        /*
//         * DefaultMQProducer 参数说明
//         * @param producerGroup 生产组聚合所有生产者进程，对于非事务生产消息，可在多个进程定义相同生产组；然而事务消息必须与非事务消息生产组区分。
//         * @param createTopicKey 自动创建测试的topic名称；broker必须开启isAutoCreateTopicEnable
//         * @param defaultTopicQueueNums 创建默认topic的queue数量。默认4
//         * @param sendMsgTimeout 发送消息超时时间(ms)，默认3000ms
//         * @param compressMsgBodyOverHowmuch 消息体压缩阈值，默认为4k。
//         * @param retryTimesWhenSendFailed 同步模式，返回发送消息失败前内部重试发送的最大次数。可能导致消息重复。默认2
//         * @param retryTimesWhenSendAsyncFailed 异步模式，返回发送消息失败前内部重试发送的最大次数。可能导致消息重复。默认2
//         * @param retryAnotherBrokerWhenNotStoreOK 声明发送失败时，下次是否投递给其他Broker，默认false
//         * @param maxMessageSize 最大消息大小。默认4M
//         * @param traceDispatcher 消息追踪器，定义了异步传输数据接口。使用rcpHook来追踪消息
//         * */
//        DefaultMQProducer producer = new DefaultMQProducer(MyConstants.GROUP_NAME);
//        producer.setNamesrvAddr(MyConstants.NAME_SRV_ADDRESS);
//        producer.setVipChannelEnabled(false);
//        producer.setRetryTimesWhenSendAsyncFailed(10);
//        producer.start();
//        log.info("rocketmq producer server开启成功---------------------------------.");
//        return producer;
//    }

}
