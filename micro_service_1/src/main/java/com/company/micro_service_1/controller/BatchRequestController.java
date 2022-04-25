package com.company.micro_service_1.controller;

import cn.hutool.core.util.IdUtil;
import com.company.micro_service_1.controller.dto.RequestBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;

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

    public List<Map<String, Object>> batch(List<Map<String, Object>> mapArrayList) {
        log.info("模拟批量处理");
        return mapArrayList;
    }
}
