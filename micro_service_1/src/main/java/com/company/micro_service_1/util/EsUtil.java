package com.company.micro_service_1.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.CreateOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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



    /**
     * 批量添加数据到es中 使用随机的uuid
     *
     * @param indexName 索引名称
     * @param dataList  数据列表
     * @return 是否成功
     * @deprecated 使用add方法
     */
    @Deprecated
    public Boolean batchAdd(String indexName, List<T> dataList) {
        // 数据为空 不允许添加
        if (CollUtil.isEmpty(dataList)) {
            return false;
        }
        Set<Boolean> resultSet = new HashSet<>();
        for (T t : dataList) {
            resultSet.add(add(indexName, IdUtil.fastSimpleUUID(), t));
        }
        return resultSet.size() == 1 && resultSet.contains(true);
    }


    /**
     * 批量添加数据到es中
     *
     * @param indexName 索引名称
     * @param idList    id列表
     * @param dataList  数据列表
     * @return 是否成功
     */
    public Boolean add(String indexName, List<String> idList, List<T> dataList) {
        List<BulkOperation> bulkOperations = new LinkedList<>();
        for (int i = 0; i < dataList.size(); i++) {
            int finalI = i;
            bulkOperations.add(new BulkOperation(CreateOperation.of(item -> item.id(idList.get(finalI)).document(dataList.get(finalI)))));
        }
        try {
            return !elasticsearchClient.bulk(req -> req.operations(bulkOperations).index(indexName)).errors();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * 批量添加数据到es中 使用随机的uuid
     *
     * @param indexName 索引名称
     * @param dataList  数据列表
     * @return 是否成功
     */
    public Boolean add(String indexName, List<T> dataList) {
        List<BulkOperation> collect = dataList.stream()
                .map(item -> CreateOperation.of(item2 -> item2.id(IdUtil.fastSimpleUUID()).document(item)))
                .map(BulkOperation::new).collect(Collectors.toList());
        try {
            return !elasticsearchClient.bulk(req -> req.operations(collect).index(indexName)).errors();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删除索引下指定id的数据
     *
     * @param indexName 索引名称
     * @param id        id
     * @return 是否删除成功
     */
    public Boolean delete(String indexName, String id) {
        try {
            DeleteResponse delete = elasticsearchClient.delete(req -> req.index(indexName).id(id));
            return Objects.equals(delete.result(), Result.Deleted);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 更新数据
     *
     * @param indexName 索引名称
     * @param id        id
     * @param data      数据
     * @return 是否成功 不存在返回false
     */
    public Boolean update(String indexName, String id, T data) {
        try {
            if (exist(indexName, id)) {
                UpdateResponse<?> update = elasticsearchClient
                        .update(req -> req.index(indexName).id(id).doc(data), data.getClass());
                return Objects.equals(update.result(), Result.Updated);
            } else {
                // 数据不存在
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 更新数据
     *
     * @param indexName  索引名称
     * @param id         id
     * @param data       数据
     * @param detectNoop 是否需要重建索引
     * @return 是否成功 不存在返回false
     */
    public Boolean update(String indexName, String id, T data, Boolean detectNoop) {
        try {
            if (exist(indexName, id)) {
                UpdateResponse<?> update = elasticsearchClient
                        .update(req -> req.index(indexName).id(id).doc(data).detectNoop(detectNoop), data.getClass());
                return Objects.equals(update.result(), Result.Updated);
            } else {
                // 数据不存在
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * 更新数据
     *
     * @param indexName 索引名称
     * @param id        id
     * @param data      数据
     * @return 是否成功 不存在执行insert
     */
    public Boolean upsert(String indexName, String id, T data, Class<T> clazz) {
        try {
            UpdateResponse<T> update = elasticsearchClient
                    .update(req -> req.index(indexName).id(id).upsert(data), clazz);
            return Objects.equals(update.result(), Result.Updated);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 更新数据
     *
     * @param indexName  索引名称
     * @param id         id
     * @param data       数据
     * @param detectNoop 是否需要重建索引
     * @return 是否成功 不存在执行insert
     */
    public Boolean upsert(String indexName, String id, T data, Class<T> clazz, Boolean detectNoop) {
        try {
            UpdateResponse<T> update = elasticsearchClient
                    .update(req -> req.index(indexName).id(id).upsert(data).detectNoop(detectNoop), clazz);
            return Objects.equals(update.result(), Result.Updated);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据id查询数据
     *
     * @param indexName 索引名称
     * @param id        id
     * @param clazz     clazz
     * @return 存在返回数据 不存在返回null
     */
    public T get(String indexName, String id, Class<T> clazz) {
        // 根据id查询
        try {
            GetResponse<T> product01 = elasticsearchClient.get(q -> q.index(indexName).id(id), clazz);
            return product01.source();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * 根据id查询数据
     *
     * @param indexName 索引名称
     * @param idList    idList
     * @param clazz     clazz
     * @return 存在返回数据 emptyList
     */
    public List<Hit<T>> get(String indexName, List<String> idList, Class<T> clazz) {
        try {
            SearchResponse<T> search = elasticsearchClient.search(req -> req.query(q -> q.ids(v -> v.values(idList))).index(indexName), clazz);
            return search.hits().hits();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
