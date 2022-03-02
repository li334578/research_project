package com.company.micro_service_1;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.*;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import com.company.micro_service_1.util.EsUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
public class EsTestClass {
    @Resource
    private ElasticsearchClient elasticsearchClient;

    @Resource
    private ElasticsearchAsyncClient elasticsearchAsyncClient;

    @Resource
    private EsUtil esUtil;

    @Test
    public void testMethod1() throws IOException {
        // 创建索引
        CreateIndexResponse response = elasticsearchClient.indices().create(c -> c.index("product01"));
        System.out.println(response);
    }

    @Test
    public void testMethod1_2() throws IOException {
        // 创建索引
        Map<String, Property> map = new HashMap<>();
        Property keywordProperty = new Property(new KeywordProperty.Builder().build());
        Property integerNumberProperty = new Property(new IntegerNumberProperty.Builder().build());
        Property textProperty = new Property(new TextProperty.Builder().build());
        map.put("name", keywordProperty);
        map.put("age", integerNumberProperty);
        map.put("remark", textProperty);
        CreateIndexResponse response = elasticsearchClient.indices().create(c -> c.index("product02").mappings(type -> type.properties(map)));
        System.out.println(response);
    }

    @Test
    public void testMethod1_3() {
        // 创建索引
        Map<String, Property> map = new HashMap<>();
        Property keywordProperty = new Property(new KeywordProperty.Builder().build());
        Property integerProperty = new Property(new IntegerNumberProperty.Builder().build());
        Property textProperty = new Property(new TextProperty.Builder().build());
        Property doubleProperty = new Property(new DoubleNumberProperty.Builder().build());
        map.put("id", integerProperty);
        map.put("name", keywordProperty);
        map.put("age", integerProperty);
        map.put("score", doubleProperty);
        map.put("className", textProperty);
        map.put("remark", textProperty);
        esUtil.createIndex("product01", map);
    }
}
