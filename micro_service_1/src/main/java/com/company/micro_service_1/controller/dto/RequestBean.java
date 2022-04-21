package com.company.micro_service_1.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestBean {

    private String serialNo;
    private CompletableFuture<Map<String, Object>> completableFuture;
    private String orderCode;
}