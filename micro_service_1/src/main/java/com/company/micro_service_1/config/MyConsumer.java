package com.company.micro_service_1.config;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.company.micro_service_1.bean.Cdk;
import com.company.micro_service_1.bean.Receive;
import com.company.micro_service_1.controller.dto.GetCdk;
import com.company.micro_service_1.controller.dto.MyConstants;
import com.company.micro_service_1.service.CdkService;
import com.company.micro_service_1.service.ReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * @Date 8/3/2022 0008 下午 4:28
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Slf4j
@Configuration
public class MyConsumer implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    private ExecutorService executorService;
    @Resource
    private CdkService cdkService;
    @Resource
    private ReceiveService receiveService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            System.out.println(event.getSource());
            listener(MyConstants.TOPIC_NAME, StringUtils.join(MyConstants.TAGS, "||"));
        } catch (MQClientException e) {
            log.error("消费者监听器启动失败", e);
        }
    }

    // 开启消费者监听服务
    public void listener(String topic, String tag) throws MQClientException {
        log.info("开启" + topic + ":" + tag + "消费者-------------------");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(MyConstants.GROUP_NAME);
        consumer.setNamesrvAddr(MyConstants.NAME_SRV_ADDRESS);
        consumer.subscribe(topic, tag);
        //从消息队列头部开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //设置集群消费模式
        consumer.setMessageModel(MessageModel.CLUSTERING);
        // 设置批量消费最大消息数
        consumer.setConsumeMessageBatchMaxSize(30);
        // 开启内部类实现监听
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
                return dealBody(messages);
            }
        });
        consumer.start();
        log.info("rocketmq Consumer Listener 启动成功---------------------------------------");
    }

    public ConsumeConcurrentlyStatus dealBody(List<MessageExt> messages) {
        if (CollectionUtils.isEmpty(messages)) {
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        List<GetCdk> allGetCdk = new ArrayList<>();
        // 批量消费
        for (MessageExt message : messages) {
            // TAG
            String tags = message.getTags();
            // 收到的消息
            String receiveMessageStr = new String(message.getBody(), StandardCharsets.UTF_8);
            // 处理成List
            allGetCdk.add(JSON.parseObject(receiveMessageStr, GetCdk.class));
            // 消息处理
        }
        CountDownLatch countDownLatch = new CountDownLatch(2);
        // cdk更新为已使用
        executorService.submit(() -> {
            QueryWrapper<Cdk> queryWrapper = new QueryWrapper<>();
            Cdk copy = MyConstants.CDK_COPIER.copy();
            queryWrapper.in("cdk", allGetCdk.stream().map(GetCdk::getCdk).collect(Collectors.toList()));
            cdkService.update(copy, queryWrapper);
            countDownLatch.countDown();
        });
        // 将领取人信息登记到表中
        executorService.submit(() -> {
            List<Receive> receiveList = allGetCdk.stream().map(item -> {
                Receive receive = new Receive();
                receive.setActiveName(item.getActiveName());
                receive.setUserName(item.getName());
                receive.setCdk(item.getCdk());
                receive.setReceiveTime(item.getCreateTime());
                return receive;
            }).collect(Collectors.toList());
            log.info("保存数量为：" + receiveList.size());
            receiveService.saveBatch(receiveList);
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

    }
}