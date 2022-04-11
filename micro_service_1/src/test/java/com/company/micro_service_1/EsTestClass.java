package com.company.micro_service_1;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.mapping.*;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.CreateOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.company.micro_service_1.bean.Goods;
import com.company.micro_service_1.bean.Person;
import com.company.micro_service_1.bean.Student;
import com.company.micro_service_1.bean.Student1;
import com.company.micro_service_1.bean.es.EsFieldType;
import com.company.micro_service_1.bean.es.EsSearchField;
import com.company.micro_service_1.bean.es.EsSearchPage;
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
import java.math.RoundingMode;
import java.net.URL;
import java.util.*;
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

    @Test
    public void testMethod16() throws IOException {
        // term查询主要用于精确匹配哪些值，比如数字，日期，布尔值或者not_analyzed的字符串未经分析的文本数据类型。
        SearchResponse<Person> product01 = elasticsearchClient.search(request -> request
                        .query(q ->
                                q.term(q1 -> q1.field("name").value(v -> v.stringValue("王五"))))
                        .index("product01")
                        .from(0).size(50),
                Person.class);
        System.out.println(product01.hits().hits());
    }

    @Test
    public void testMethod16_2() throws IOException {
        // terms查询主要用于精确匹配哪些值，比如数字，日期，布尔值或者not_analyzed的字符串未经分析的文本数据类型。
        SearchResponse<Person> product01 = elasticsearchClient.search(request -> request
                        .query(q ->
                                q.terms(q1 -> q1.field("age").terms(v -> v.value(Arrays.asList(FieldValue.of(12), FieldValue.of(32))))))
                        .index("product01")
                        .from(0).size(50),
                Person.class);
        System.out.println(product01.hits().hits());
    }

    @Test
    public void testMethod17() throws IOException {
        // range 范围查询age gt 大于 lt 小于
        SearchResponse<Person> product01 = elasticsearchClient.search(request -> request
                .query(q -> q.range(q1 -> q1.field("age").gt(JsonData.of(10)).lte(JsonData.of(32))))
                .index("product01"), Person.class);
        System.out.println(product01.hits().hits());
    }

    @Test
    public void testMethod18() throws Exception {
        // exist
        BooleanResponse product01 = elasticsearchClient.exists(request -> request.id("1").index("product01"));
        System.out.println(product01.value());
        // todo 属性不存在的查询
//        elasticsearchClient.existsSource(request -> request.source(v->v.fields()))
    }

    @Test
    public void testMethod19() throws Exception {
        // match 查询时一个标准的查询，不论你需要全文本查询（模糊）还是精确查询都可以用它
        SearchResponse<Person> search = elasticsearchClient.search(request
                -> request.query(q1 -> q1.match(m -> m.field("name").query(v -> v.stringValue("张三")))).index("product01"), Person.class);
        System.out.println(search.hits().hits());
    }

    @Test
    public void testMethod20() throws Exception {
        // bool 姓名中包含 '三' 年龄 不大于等于30
        SearchResponse<Person> search = elasticsearchClient.search(request -> request
                        .query(q -> q
                                .bool(b -> b
                                        .must(must -> must
                                                .match(m -> m.field("name").query(v1 -> v1.stringValue("三"))))
                                        .mustNot(mustNot -> mustNot
                                                .range(v1 -> v1.field("age").gte(JsonData.of(30))))))
                        .index("product01"),
                Person.class);
        System.out.println(search.hits().hits());
    }

    @Test
    public void testMethod21() throws Exception {
        SearchResponse<Person> search = elasticsearchClient.search(request -> request
                .query(q -> q
                        .bool(b -> b
                                .must(must -> must
                                        .match(m -> m.field("name").query(v1 -> v1.stringValue("三"))))
                                .filter(f -> f
                                        .term(t -> t.field("age").value(v -> v.longValue(45))))))
                .index("product01"), Person.class);
        System.out.println(search.hits().hits());
    }

    @Test
    public void testMethod22() throws Exception {
        // null字段会自动忽略更新
        Person person = new Person("赵四", null, null);
        UpdateResponse<Person> product01 = elasticsearchClient.update(request -> request.id("5").doc(person).index("product01"), Person.class);
        System.out.println(product01.get());
    }

    @Test
    public void testMethod23() throws Exception {
        // 若对应文档不存在 会进行insert
        Person person = new Person("李四2", null, "法外狂徒李四");
        UpdateResponse<Person> product01 = elasticsearchClient.update(request -> request.id("2").doc(person).docAsUpsert(true).index("product01"), Person.class);
        elasticsearchClient.update(request -> request.upsert(person).id("2").index("product01"), Person.class);
        System.out.println(product01.get());
        // 	detectNoop() 前后文档值相同时 是否重建索引 默认为true
    }

    @Test
    public void testMethod24() throws Exception {
        List<EsSearchField> esSearchFieldList = new ArrayList<>();
        esSearchFieldList.add(new EsSearchField());
        esSearchFieldList.add(new EsSearchField());
        HitsMetadata<Goods> product03 = esUtil.query("product04", Goods.class,
                null, null,
                new EsSearchPage(1, 50), null, null);
        System.out.println("successful");
    }

    @Test
    public void testMethod25() throws Exception {
        List<Student1> list = new LinkedList<>();
        Student1 student;
        for (int i = 1; i < 1000; i++) {
            String name = "李斐" + i;
            student = new Student1(i, name, RandomUtil.randomInt(10, 80), RandomUtil.randomDouble(20.0, 100.0, 2, RoundingMode.UP), "class" + (i % 5 + 1), "法外狂徒" + name);
            list.add(student);
        }
        list.forEach(System.out::println);
        Boolean product01 = esUtil.add("product01", list);
        System.out.println(product01);
    }

    @Test
    public void testMethod26() throws Exception {
        esUtil.delete("product01", "1");
    }

    @Test
    public void testMethod27() throws Exception {
//        List<EsSearchFieldRange> esSearchFieldRangeList = new LinkedList<>();
//        esSearchFieldRangeList.add(new EsSearchFieldRange("score", 100L, 60L, EsFieldType.FLOAT, true));
//        esSearchFieldRangeList.add(new EsSearchFieldRange("age", 30L, 0L, EsFieldType.LONG, false));
        List<EsSearchField> esSearchFieldList = new LinkedList<>();
        esSearchFieldList.add(new EsSearchField("remark", "李斐4", EsFieldType.TEXT, 3));
        // 使用排序的时候 命中score为null
//        EsSearchOrder searchOrder = new EsSearchOrder("score", EsSearchOrder.order_by_type__desc);
        HitsMetadata<Student> metadata = esUtil.query("product01", Student.class,
                esSearchFieldList, null,
                new EsSearchPage(1, 1000), null, null);
        System.out.println(metadata.hits().size());
        for (Hit<Student> hit : metadata.hits()) {
            System.out.print(hit.source() + " 分数为 ");
            System.out.println(hit.score());
        }
    }

    @Test
    public void testMethod28() throws Exception {
        SearchResponse<Student> search = elasticsearchClient.search(request -> request
                .query(q -> q
                        .bool(b -> b
//                                .must(must -> must
//                                        .match(m -> m.field("className").query(v1 -> v1.stringValue("class1"))))
//                                        .must(must -> must
//                                                .queryString(qs -> qs.fields("className").query("class1")))
//                                        .must(must -> must
//                                                .match(m -> m.field("className").query(v -> v.stringValue("class1"))))
                                        .must(must -> must.matchPhrase(mp -> mp.field("className").query("class1")))
//                                .filter(f -> f
//                                        .term(t -> t.field("age").value(v -> v.longValue(55))))
                        ))
                .from(0).size(100)
                .index("product01"), Student.class);
        for (Hit<Student> hit : search.hits().hits()) {
            System.out.print(hit.source() + " 分数为 ");
            System.out.println(hit.score());
        }
    }

    @Test
    public void testMethod29() throws Exception {
        SearchResponse<Student> search = elasticsearchClient.search(request -> request
//                .query(q -> q.matchPhrase(mp -> mp.field("className").query("class").analyzer("standard")))
//                .query(q -> q.match(m -> m.field("className").query(v->v.stringValue("class"))))
//                .query(q -> q.queryString(qs -> qs.fields("className").query("class")))
                .query(q -> q.fuzzy(f -> f.field("className").value(v -> v.stringValue("class"))))
                .from(0).size(1000)
                .index("product01"), Student.class);
        System.out.println(search.hits().hits().size());
        for (Hit<Student> hit : search.hits().hits()) {
            System.out.print(hit.source() + " 分数为 ");
            System.out.println(hit.score());
        }
    }

    @Test
    public void testMethod30() throws IOException {
        List<Goods> javaGoodsList = getElements("余华");
        List<BulkOperation> collect = javaGoodsList.stream().map(item -> CreateOperation.of(v -> v.id(IdUtil.fastSimpleUUID()).document(item))).map(BulkOperation::new).collect(Collectors.toList());
        BulkResponse product02 = elasticsearchClient.bulk(req -> req.operations(collect).index("product02"));
        System.out.println(product02);
    }

}
