package com.company.micro_service_1.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch.core.CreateRequest;
import co.elastic.clients.elasticsearch.core.CreateResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

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


    /**
     * 删除指定索引
     *
     * @param indexName 索引名称
     * @return 是否删除成功
     */
    public Boolean delIndex(String indexName) {
        try {
            if (exist(indexName)) {
                DeleteIndexResponse deleteIndexResponse = elasticsearchClient.indices()
                        .delete(c -> c.index(indexName));
                return deleteIndexResponse.acknowledged();
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断索引下的指定id是否存在数据
     *
     * @param indexName 索引名称
     * @param id        id
     * @return 存在返回true
     */
    public Boolean exist(String indexName, String id) {
        try {
            BooleanResponse exists = elasticsearchClient
                    .exists(request -> request.id(id).index(indexName));
            return exists.value();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 向指定索引位置 添加数据
     *
     * @param indexName 索引名称
     * @param data      数据
     * @param id        id
     * @return 是否添加成功
     */
    public Boolean add(String indexName, String id, T data) {
        // 数据为空 不允许添加
        if (data == null || StrUtil.isEmpty(id)) {
            return false;
        }
        try {
            // 判断id是否存在
            if (exist(indexName, id)) {
                // 存在
                return false;
            }
            CreateResponse createResponse = elasticsearchClient.create(CreateRequest
                    .of(c -> c.index(indexName).id(id).document(data)));
            return Objects.equals(createResponse.result(), Result.Created);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 批量添加数据到es中
     *
     * @param indexName 索引名称
     * @param idList    id列表
     * @param dataList  数据列表
     * @return 是否成功
     * @deprecated 使用add方法
     */
    @Deprecated
    public Boolean batchAdd(String indexName, List<String> idList, List<T> dataList) {
        // 数据为空 不允许添加
        if (CollUtil.isEmpty(idList)
                || CollUtil.isEmpty(dataList)
                || idList.size() != dataList.size()) {
            return false;
        }
        Set<Boolean> resultSet = new HashSet<>();
        for (int i = 0; i < dataList.size(); i++) {
            resultSet.add(add(indexName, idList.get(i), dataList.get(i)));
        }
        return resultSet.size() == 1 && resultSet.contains(true);
    }

}
