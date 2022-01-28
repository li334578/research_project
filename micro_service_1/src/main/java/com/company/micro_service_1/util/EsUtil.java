package com.company.micro_service_1.util;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Component
public class EsUtil<T> {
    @Resource
    private ElasticsearchClient elasticsearchClient;

    @Resource
    private ElasticsearchAsyncClient elasticsearchAsyncClient;

    /**
     * 创建索引 不指定mapping
     *
     * @param indexName 索引名称
     * @return 是否创建成功
     */
    public Boolean createIndex(String indexName) {
        // 检查索引是否存在
        try {
            if (exist(indexName)) {
                return false;
            }
            // 创建索引
            CreateIndexResponse response = elasticsearchClient.indices().create(c -> c.index(indexName));
            return response.acknowledged();
        } catch (IOException e) {
            // 访问es失败 返回false
            e.printStackTrace();
            return false;
        }
    }



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



    /***
     * 创建索引 执行类型
     * 常用类型
     * keyword Property keywordProperty = new Property(new KeywordProperty.Builder().build());
     * integer Property integerNumberProperty = new Property(new IntegerNumberProperty.Builder().build());
     * text    Property textProperty = new Property(new TextProperty.Builder().build());
     * @param indexName 索引名称
     * @param map property map
     * @return 是否创建成功
     */
    public Boolean createIndex(String indexName, Map<String, Property> map) {
        // 检查索引是否存在
        try {
            if (exist(indexName)) {
                return false;
            }
            // 创建索引
            CreateIndexResponse response = elasticsearchClient.indices()
                    .create(c -> c.index(indexName).mappings(type -> type.properties(map)));
            return response.acknowledged();
        } catch (IOException e) {
            // 访问es失败 返回false
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 获取索引信息
     *
     * @param indexName 索引名称
     * @return GetIndexResponse 索引信息
     */
    public GetIndexResponse getIndex(String indexName) {
        try {
            // 索引存在才能获取索引
            if (exist(indexName)) {
                return elasticsearchClient.indices().get(c -> c.index(indexName));
            } else {
                // 索引不存在
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
