package com.company.micro_service_1.util;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
public class EsUtil<T> {
    @Resource
    private ElasticsearchClient elasticsearchClient;

    @Resource
    private ElasticsearchAsyncClient elasticsearchAsyncClient;


    /**
     * 判断索引是否存在
     *
     * @param indexName 索引名称
     * @return 是否存在
     */
    private Boolean exist(String indexName) {
        try {
            BooleanResponse existsResponse = elasticsearchClient.indices()
                    .exists(c -> c.index(indexName));
            // 存在返回true 不存在返回false
            return existsResponse.value();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
