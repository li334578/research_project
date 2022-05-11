package com.company.micro_service_1.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.copier.Copier;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.company.micro_service_1.bean.Cdk;
import com.company.micro_service_1.controller.dto.GetCdk;
import com.company.micro_service_1.controller.dto.MyConstants;
import com.company.micro_service_1.service.CdkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @Classname MainController
 * @Description TODO
 * @Version 1.0.0
 * @Date 27/4/2022 下午 8:07
 * @Created by 李文博
 */
@RestController
@RequestMapping("/main/")
@Slf4j
public class MainController {

    @Resource
    private ExecutorService executorService;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private CdkService cdkService;

    @Resource
    private DefaultMQProducer producer;

    private static final String baseString = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    private static final Copier<Cdk> cdkCopier = () -> {
        Cdk cdk = new Cdk();
        cdk.setUseStatus(0);
        return cdk;
    };

    Map<String, Set<String>> cdkMap = new HashMap<>();

    LinkedBlockingDeque<GetCdk> queues = new LinkedBlockingDeque<>();

    @PostConstruct
    public void init() {
        // 四个活动 一共两千CDK
        // at1 100 at2 500 at3 1000 at4 400
        // 生成两千CDK
        cdkMap = generateCode();
        List<Cdk> cdkArrayList = new ArrayList<>();
        // 放入Redis中
        cdkMap.forEach((k, v) -> {
            RList<String> list = redissonClient.getList(k);
            list.addAll(v);
            List<Cdk> collect = v.stream().map(item -> {
                Cdk copy = cdkCopier.copy();
                copy.setActiveName(k);
                copy.setCdk(item);
                return copy;
            }).collect(Collectors.toList());
            cdkArrayList.addAll(collect);
        });
        executorService.submit(() -> {
            cdkService.saveBatch(cdkArrayList);
        });

        // 定时任务

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            int size = queues.size();
            if (CollUtil.isEmpty(queues)) {
                return;
            }
            log.error("定时任务本此获取 " + size);
            List<Message> messages = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                GetCdk poll = queues.poll();
                if (poll == null) {
                    continue;
                }
                Message message = new Message(MyConstants.TOPIC_NAME, poll.getActiveName(), JSON.toJSONString(poll).getBytes(StandardCharsets.UTF_8));
                messages.add(message);
            }
            try {
                producer.send(messages);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 80, TimeUnit.MILLISECONDS);
    }

    private Map<String, Set<String>> generateCode() {
        Map<String, Set<String>> cdkMap = new HashMap<>();
        CopyOnWriteArrayList<String> cpAll = new CopyOnWriteArrayList<>();
        Future<Set<String>> submit1 = executorService.submit(() -> {
            Set<String> cdkSet = new HashSet<>();
            while (cdkSet.size() != MyConstants.AT1_COUNT) {
                String cdk = RandomUtil.randomString(baseString, 8);
                if (!cpAll.contains(cdk)) {
                    cpAll.add(cdk);
                    cdkSet.add(cdk);
                }
            }
            return cdkSet;
        });
        Future<Set<String>> submit2 = executorService.submit(() -> {
            Set<String> cdkSet = new HashSet<>();
            while (cdkSet.size() != MyConstants.AT2_COUNT) {
                String cdk = RandomUtil.randomString(baseString, 8);
                if (!cpAll.contains(cdk)) {
                    cpAll.add(cdk);
                    cdkSet.add(cdk);
                }
            }
            return cdkSet;
        });
        Future<Set<String>> submit3 = executorService.submit(() -> {
            Set<String> cdkSet = new HashSet<>();
            while (cdkSet.size() != MyConstants.AT3_COUNT) {
                String cdk = RandomUtil.randomString(baseString, 8);
                if (!cpAll.contains(cdk)) {
                    cpAll.add(cdk);
                    cdkSet.add(cdk);
                }
            }
            return cdkSet;
        });
        Future<Set<String>> submit4 = executorService.submit(() -> {
            Set<String> cdkSet = new HashSet<>();
            while (cdkSet.size() != MyConstants.AT4_COUNT) {
                String cdk = RandomUtil.randomString(baseString, 8);
                if (!cpAll.contains(cdk)) {
                    cpAll.add(cdk);
                    cdkSet.add(cdk);
                }
            }
            return cdkSet;
        });
        try {
            Set<String> at1 = submit1.get();
            cdkMap.put(MyConstants.TAG_1, at1);
            Set<String> at2 = submit2.get();
            cdkMap.put(MyConstants.TAG_2, at2);
            Set<String> at3 = submit3.get();
            cdkMap.put(MyConstants.TAG_3, at3);
            Set<String> at4 = submit4.get();
            cdkMap.put(MyConstants.TAG_4, at4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cdkMap;
    }

    @PostMapping("exchange")
    public DeferredResult<String> exchangeCodes(@RequestBody GetCdk getCdk) {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        if (!MyConstants.TAGS.contains(getCdk.getActiveName())) {
            // 直接拦截
            deferredResult.setResult("Fail");
            return deferredResult;
        }
        String cdk;
        RLock lock = redissonClient.getLock(getCdk.getActiveName() + "lock");
        try {
            lock.lock();
            // 秒杀cdk
            RList<String> list = redissonClient.getList(getCdk.getActiveName());
            if (CollUtil.isEmpty(list)) {
                deferredResult.setResult("Fail");
                return deferredResult;
            }
            int index = list.size() - 1;
            cdk = list.get(index);
            list.fastRemove(index);
            // 投递到MQ成功就可以
            System.out.println(cdk);
            getCdk.setCdk(cdk);
            getCdk.setCreateTime(DateUtil.now());
            // 放到队列 队列去批量投递到MQ
            log.info(getCdk.getCdk() + "放入队列");
            queues.add(getCdk);
        } finally {
            lock.unlock();
        }
        deferredResult.setResult(cdk);
        return deferredResult;
    }
}
