package com.company.micro_service_1.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.company.micro_service_1.controller.dto.RequestBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Classname BatchRequestController
 * @Description 批量请求controller
 * @Version 1.0.0
 * @Date 20/4/2022 下午 8:17
 * @Created by 李文博
 */
@RestController
@Slf4j
public class BatchRequestController {

    LinkedBlockingDeque<RequestBean> queue = new LinkedBlockingDeque<>();

    public Map<String, Object> queryOrderInfo(String orderCode) throws Exception {
        String serialNo = IdUtil.fastSimpleUUID();
        CompletableFuture<Map<String, Object>> completableFuture = new CompletableFuture<>();

        RequestBean requestBean = new RequestBean(serialNo, completableFuture, orderCode);
        queue.add(requestBean);
        return completableFuture.get();
    }

    @PostConstruct
    public void init() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            if (CollUtil.isEmpty(queue)) {
                return;
            }
            int size = queue.size();
            List<Map<String, Object>> mapArrayList = new ArrayList<>();
            List<RequestBean> requestBeanList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                RequestBean requestBean = queue.poll();
                if (Objects.isNull(requestBean)) {
                    continue;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("orderCode", requestBean.getOrderCode());
                map.put("serialNo", requestBean.getSerialNo());
                mapArrayList.add(map);
                requestBeanList.add(requestBean);
            }
            List<Map<String, Object>> responses = batch(mapArrayList);
            for (RequestBean requestBean : requestBeanList) {
                String serialNo = requestBean.getSerialNo();
                for (Map<String, Object> response : responses) {
                    if (Objects.equals(serialNo, response.get("serialNo"))) {
                        // 通知对应线程
                        CompletableFuture<Map<String, Object>> completableFuture = requestBean.getCompletableFuture();
                        completableFuture.complete(response);
                        break;
                    }
                }
            }
        }, 0, 50, TimeUnit.MILLISECONDS);
    }
    public List<Map<String, Object>> batch(List<Map<String, Object>> mapArrayList) {
        log.info("模拟批量处理");
        return mapArrayList;
    }
}
