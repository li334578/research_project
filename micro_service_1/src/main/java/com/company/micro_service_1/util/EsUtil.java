package com.company.micro_service_1.util;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class EsUtil<T> {
    @Resource
    private ElasticsearchClient elasticsearchClient;

    @Resource
    private ElasticsearchAsyncClient elasticsearchAsyncClient;
}
