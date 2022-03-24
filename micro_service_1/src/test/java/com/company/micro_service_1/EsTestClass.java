package com.company.micro_service_1;

import cn.hutool.core.util.IdUtil;
import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.*;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.company.micro_service_1.bean.Goods;
import com.company.micro_service_1.bean.Person;
import com.company.micro_service_1.util.EsUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Test
    public void testMethod2() throws IOException {
        // 获取索引
        GetIndexResponse product01 = elasticsearchClient.indices().get(c -> c.index("product01"));
        System.out.println(product01);
        BooleanResponse product021 = elasticsearchClient.indices().exists(c -> c.index("product02"));
        // 存在返回true 不存在返回false
        System.out.println(product021.value());
        // 不存在  no such index [product02]
//        GetIndexResponse product02 = elasticsearchClient.indices().get(c -> c.index("product02"));
//        System.out.println(product02);
    }

    @Test
    public void testMethod3() {
        // 删除索引
        System.out.println(esUtil.delIndex("product01"));
    }

    @Test
    public void testMethod3_1() throws IOException {
        // 删除索引
        DeleteIndexResponse product01 = elasticsearchClient.indices().delete(c -> c.index("product02"));
        System.out.println(product01);
        System.out.println(product01.acknowledged());
    }

    @Test
    public void testMethod4() throws IOException {
        Person person = new Person("赵三", 45, null);
        // PUT /product01/_doc/1
        CreateRequest<Person> createRequest = CreateRequest.of(c -> c.index("product01").id("5").document(person));
        CreateResponse createResponse = elasticsearchClient.create(createRequest);
        System.out.println(createResponse);
    }

    @Test
    public void testMethod4_1() throws IOException {
        Person person = new Person("赵三", 45, null);
        // PUT /product01/_doc/1
        CreateRequest<Person> createRequest = CreateRequest.of(c -> c.index("product01").id("1").document(person));
        CreateResponse createResponse = elasticsearchClient.create(createRequest);
        System.out.println(createResponse);
    }

    @Test
    public void testMethod5() throws IOException {
        // 根据id查询
        GetResponse<Person> product01 = elasticsearchClient.get(q -> q.index("product01").id("1"), Person.class);
        Person source = product01.source();
        System.out.println(product01);
    }

    @Test
    public void testMethod6() throws IOException {
        // 分页查询
        SearchResponse<Person> product01 = elasticsearchClient.search(q -> q.index("product01").from(0).size(50), Person.class);
        System.out.println(product01);
    }

    @Test
    public void testMethod7() throws IOException {
        // 分页条件查询
        SearchResponse<Person> product01 = elasticsearchClient.search(request -> request
                        .highlight(h -> h.fields("remark",
                                hf -> hf.matchedFields("remark").preTags("<span style=\"color:red\">").postTags("</span>")))
                        .query(q -> q.queryString(
                                q1 -> q1.fields("remark").query("法外狂徒")))
                        .index("product01")
                        .from(0).size(50),
                Person.class);
        System.out.println(product01);
    }

    @Test
    public void testMethod8() throws IOException {
        SearchResponse<Person> product01 = elasticsearchClient.search(request -> request
                        .query(q ->
                                q.term(q1 ->
                                        q1.field("name").value(v -> v.stringValue("张三"))))
                        .index("product01")
                        .from(0).size(50),
                Person.class);
        System.out.println(product01);
    }

    @Test
    public void testMethod9() throws IOException {
        Person person = new Person();
        person.setAge(61);
        UpdateResponse<Person> product01 = elasticsearchClient.update(req -> req.doc(person).id("3").index("product01"), Person.class);
        System.out.println(product01);
    }

    @Test
    public void testMethod10() throws IOException {
        // 删除文档
        DeleteResponse product01 = elasticsearchClient.delete(req -> req.id("3").index("product01"));
        System.out.println(product01);
    }

    @Test
    public void testMethod11() throws IOException {
        esUtil.add("product01", new ArrayList<>());
    }

    @Test
    public void testMethod12() throws IOException {
        elasticsearchAsyncClient.exists(c -> c.id("1").index("product01"))
                .thenAccept(response -> {
                    log.info(response.toString());
                    if (response.value()) {
                        // 存在
                        log.info("data is exist");
                    }
                });
    }

    @Test
    public void testMethod13() throws IOException {
        SearchResponse<Person> product01 = elasticsearchClient.search(request -> request
                        .query(q ->
                                q.term(q1 -> q1.field("age").value(v -> v.longValue(12))))
                        .index("product01")
                        .from(0).size(50),
                Person.class);
        System.out.println(product01.hits().hits());
    }

    @Test
    public void testMethod14() throws Exception {
        List<Goods> javaGoodsList = getElements("java");
        // 存储到es中
        List<CreateRequest<Object>> product02 = javaGoodsList.stream().map(item -> CreateRequest.of(c -> c.id(IdUtil.fastSimpleUUID()).index("product03").document(item))).collect(Collectors.toList());
        for (CreateRequest<Object> createRequest : product02) {
            CreateResponse createResponse = elasticsearchClient.create(createRequest);
            System.out.println(createResponse.toString());
        }
    }

    private List<Goods> getElements(String keyword) throws IOException {
        String url = "https://search.dangdang.com/?key=" + keyword;
        Document document = Jsoup.parse(new URL(url), 30000);
        Element component_59 = document.getElementById("component_59");
        Elements aClass = component_59.getElementsByAttributeValueMatching("class", "line\\d{1,}");
        List<Goods> goodsList = new ArrayList<>();
        Goods goods;
        for (Element element : aClass) {
            Elements a = element.getElementsByTag("a");
            Element element1 = a.get(1);
            String title = element1.attr("title");
            Elements img = element.getElementsByTag("img");
            Element element2 = img.get(0);
            String src = element2.attr("src");
            String alt = element2.attr("alt");
            Elements priceElements = element.getElementsByAttributeValue("class", "search_pre_price");
            String price = "null";
            if (!priceElements.isEmpty()) {
                Element element3 = priceElements.get(0);
                price = element3.text();
            }
            goods = new Goods(title, alt, src, price);
            goodsList.add(goods);
        }
        return goodsList;

    }

    @Test
    public void testMethod15() throws IOException {
        SearchResponse<Goods> goodsSearchResponse = elasticsearchClient.search(request -> request
                        .query(q ->
                                q.queryString(q1 -> q1.fields("title").query("Vue")))
                        .index("product02")
                        .from(0).size(50),
                Goods.class);
        for (Hit<Goods> hit : goodsSearchResponse.hits().hits()) {
            System.out.println(hit.source());
        }
    }

    @Test
    public void testMethod15_2() throws IOException {
        SearchResponse<Goods> goodsSearchResponse = elasticsearchClient.search(request -> request
                        .index("product03")
                        .from(0).size(50),
                Goods.class);
        for (Hit<Goods> hit : goodsSearchResponse.hits().hits()) {
            System.out.println(hit.source());
        }
    }

}
