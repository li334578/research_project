package com.company.micro_service_1.controller;

import cn.hutool.core.lang.copier.Copier;
import cn.hutool.core.util.RandomUtil;
import com.company.micro_service_1.bean.Cdk;
import com.company.micro_service_1.controller.dto.GetCdk;
import com.company.micro_service_1.controller.dto.MyConstants;
import com.company.micro_service_1.service.CdkService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

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

    private static final String baseString = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    private static final Copier<Cdk> cdkCopier = () -> {
        Cdk cdk = new Cdk();
        cdk.setUseStatus(0);
        return cdk;
    };

    Map<String, Set<String>> cdkMap = new HashMap<>();

    LinkedBlockingDeque<GetCdk> queues = new LinkedBlockingDeque<>();

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
}
