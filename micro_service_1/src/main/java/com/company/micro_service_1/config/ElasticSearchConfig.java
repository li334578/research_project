package com.company.micro_service_1.config;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Date 18/1/2022 上午 10:54
 * @Description ElasticSearch 配置类
 * @Version 1.0.0
 * @Author liwenbo
 */
//@Configuration
@Slf4j
public class ElasticSearchConfig {

    @Value("${elasticsearch.properties.host}")
    private String host;

    @Value("${elasticsearch.properties.port}")
    private Integer port;

    @Bean
    public ElasticsearchClient elasticsearchClient(ElasticsearchTransport elasticsearchTransport) {
        log.info("33333");
        return new ElasticsearchClient(elasticsearchTransport);
    }

    @Bean
    public ElasticsearchAsyncClient elasticsearchAsyncClient(ElasticsearchTransport elasticsearchTransport) {
        log.info("44444");
        return new ElasticsearchAsyncClient(elasticsearchTransport);
    }

    @Bean
    public ElasticsearchTransport elasticsearchTransport(RestClient restClient) {
        log.info("22222");
        return new RestClientTransport(
                restClient, new JacksonJsonpMapper());
    }

    @Bean
    public RestClient restClient() {
        log.info("11111");
        return RestClient.builder(
                new HttpHost(host, port)).build();
    }


}
