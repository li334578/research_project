package com.company.micro_service_1;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.company.micro_service_1.util.EsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class EsTestClass {
    @Resource
    private ElasticsearchClient elasticsearchClient;

    @Resource
    private ElasticsearchAsyncClient elasticsearchAsyncClient;

    @Resource
    private EsUtil esUtil;
}
