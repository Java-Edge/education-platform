package com.javagpt.common.util;

import cn.hutool.core.collection.CollectionUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.GeoBoundingBoxQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.GeoDistanceQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.util.ObjectBuilder;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class EsDocumentUtils {

    @Resource
    private ElasticsearchClient esClient;

    /**
     * 1、向Document(活动表)中添加数据(文档)
     * 1.1、es、向Document(活动表)中添加数据(文档)
     * 1.2、mysql、向活动表添加一条数据
     *
     * @param indexName 索引名称-即表名
     * @param document  数据行-文档
     * @param id        表的主键id
     * @param classz    表结构对应的实体类
     */
    @SneakyThrows
    public <T, U> CreateResponse addDocument(String indexName, T document, String id, Class<U> classz) {
        Assert.isTrue(StringUtils.isNotBlank(indexName), "indexName 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(id), "id 不能为空");
        Assert.isTrue(document != null, "document 数据行-不能为空");
        Assert.isTrue(classz != null, "classz 表结构对应的实体类-不能为空");

        // 构建一个创建Doc的请求
        CreateResponse createResponse = esClient.create(item -> item.index(indexName).id(id).document(document));
        return createResponse;
    }

    /**
     * 1-1、批量向Document(活动表)中添加数据(文档)
     * 1.1、es、向Document(活动表)中添加数据(文档)
     * 1.2、mysql、向活动表添加一条数据
     *
     * @param indexName 索引名称-即表名
     * @param documents 多数据行
     * @param id        表的主键id
     * @param classz    表结构对应的实体类
     */
    @SneakyThrows
    public <T, U> BulkResponse batchAddDocument(String indexName, List<T> documents, String id, Class<U> classz) {
        Assert.isTrue(StringUtils.isNotBlank(indexName), "indexName 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(id), "id 不能为空");
        Assert.isTrue(CollectionUtil.isEmpty(documents), "documents 数据行-不能为空");
        Assert.isTrue(classz != null, "classz 表结构对应的实体类-不能为空");

        List<BulkOperation> bulkOperations = new ArrayList<>();
        //遍历添加到bulk中
        for (T item : documents) {
            bulkOperations.add(BulkOperation.of(o -> o.index(i -> i.document(item))));
        }
        BulkResponse bulkResponse = esClient.bulk(b -> b.index(indexName).operations(bulkOperations));
        return bulkResponse;
    }

    /**
     * 更新文档数据-map入参方式
     *
     * @param indexName 索引名称-即表名
     * @param id        表的主键id
     * @param map       构建需要修改的内容，这里使用了Map
     * @param classz    表结构对应的实体类
     */
    @SneakyThrows
    public <U> UpdateResponse updateDocByMap(String indexName, String id, Map<String, Object> map, Class<U> classz) {
        Assert.isTrue(StringUtils.isNotBlank(indexName), "indexName 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(id), "id 不能为空");
        Assert.isTrue(map != null, "map 需要修改的内容-不能为空");
        Assert.isTrue(classz != null, "classz 表结构对应的实体类-不能为空");

        // 构建修改文档的请求
        UpdateResponse<U> response = esClient.update(item -> item.index(indexName).id(id).doc(map), classz);
        return response;
    }

    /**
     * 更新文档数据-model入参方式
     *
     * @param indexName 索引名称-即表名
     * @param id        表的主键id
     * @param document  数据行-文档
     * @param classz    表结构对应的实体类
     */
    @SneakyThrows
    public <T, U> UpdateResponse updateDocumentByModel(String indexName, String id, T document, Class<U> classz) {
        Assert.isTrue(StringUtils.isNotBlank(indexName), "indexName 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(id), "id 不能为空");
        Assert.isTrue(document != null, "document 数据行-不能为空");
        Assert.isTrue(classz != null, "classz 表结构对应的实体类-不能为空");

        // 构建修改文档的请求
        UpdateResponse<U> response = esClient.update(item -> item.index(indexName).id(id).doc(document), classz);
        return response;
    }

    /**
     * 判断Document是否存在
     *
     * @param indexName 索引名称-即表名
     * @param id        表的主键id
     */
    @SneakyThrows
    public BooleanResponse existsDocument(String indexName, String id) {
        Assert.isTrue(StringUtils.isNotBlank(indexName), "indexName 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(id), "id 不能为空");

        BooleanResponse response = esClient.exists(item -> item.index(indexName).id(id));
        return response;
    }

    /**
     * 查询Document
     *
     * @param indexName 索引名称-即表名
     * @param id        表的主键id
     * @param classz    表结构对应的实体类
     */
    @SneakyThrows
    public <U> GetResponse<U> getDocument(String indexName, String id, Class<U> classz) {
        Assert.isTrue(StringUtils.isNotBlank(indexName), "indexName 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(id), "id 不能为空");
        Assert.isTrue(classz != null, "classz 表结构对应的实体类-不能为空");
        GetResponse<U> response = esClient.get(item -> item.index(indexName).id(id), classz);
        return response;
    }

    /**
     * 删除Document
     *
     * @param indexName 索引名称-即表名
     * @param id        表的主键id
     */
    @SneakyThrows
    public DeleteResponse deleteDocument(String indexName, String id) {
        Assert.isTrue(StringUtils.isNotBlank(indexName), "indexName 不能为空");
        Assert.isTrue(StringUtils.isNotBlank(id), "id 不能为空");

        DeleteResponse response = esClient.delete(item -> item.index(indexName).id(id));
        return response;
    }

    /**
     * 根据某个字段精确查询数据---term查询(完全匹配)
     * 不使用分词器精确查找
     *
     * @param indexName 索引名称-即表名
     * @param classz    表结构对应的实体类
     * @param field     要查询的字段
     * @param value     要查询的字段值
     */
    @SneakyThrows
    public <U> SearchResponse<U> getDocumentsByField(String indexName, Class<U> classz, String field, String value) {
        SearchResponse<U> searchResponse = esClient.search(s -> deal(s, indexName, field, value), classz);
        return searchResponse;
    }

    /**
     * 8、自定义查询条件查询
     *  不使用分词器精确查找
     * @param indexName 索引名称-即表名
     * @param classz 表结构对应的实体类
     * @param query 自定义的查询条件
     * @param <U>
     */
    @SneakyThrows
    public <U> SearchResponse<U> getDocumentsByCustomize(String indexName, Class<U> classz, Query query){
        SearchResponse<U> searchResponse = esClient.search(s ->
                        s.index(indexName)
                                .query(query)
                ,classz);
        return searchResponse;
    }

    private <U> ObjectBuilder<SearchRequest> deal(SearchRequest.Builder s, String indexName, String field, String value) {
        s.index(indexName);
        s.query(q -> q
                .term(t -> t
                        .field(field)
                        .value(v -> v.stringValue(value))
                ));
//        s.from(0);
//        s.size(3);
        s.sort(f -> f.field(o -> o.field("createTime").order(SortOrder.Desc)));
        return s;
    }

    /**
     * 自定义查询条件查询
     * 不使用分词器精确查找
     *
     * @param indexName 索引名称-即表名
     * @param classz    表结构对应的实体类
     * @param query     自定义的查询条件
     */
    @SneakyThrows
    public <U> SearchResponse<U> getDocsByCustomize(String indexName, Class<U> classz, Query query) {
        SearchResponse<U> searchResponse = esClient.search(s ->
                        s.index(indexName)
                                .query(query)
                , classz);
        return searchResponse;
    }

    /**
     * 自定义多查询条件查询
     * 不使用分词器精确查找
     *
     * @param indexName 索引名称-即表名
     * @param classz    表结构对应的实体类
     * @param queryList 自定义的查询条件多查询条件查询
     */
    @SneakyThrows
    public <U> SearchResponse<U> getDocumentsByCustomizeMore(String indexName, Class<U> classz, List<Query> queryList) {
        SearchResponse<U> searchResponse = esClient.search(s ->
                        s.index(indexName)
                                .query(q -> q.bool(b -> b.must(queryList)))
                , classz);
        return searchResponse;
    }


    /**
     * es基于距离查询
     */
    @SneakyThrows
    public <U> SearchResponse<U> geoDistanceSelect(String indexName, Class<U> classz, GeoDistanceQuery geoDistanceQuery) {
        SearchResponse<U> searchResponse = esClient.search(s ->
                        s.index(indexName)
                                .query(q -> q.bool(t -> t.must(k -> k.geoDistance(geoDistanceQuery)).queryName("location")))
                , classz);
        return searchResponse;
    }

    /**
     * es基于矩形范围查询
     */
    @SneakyThrows
    public <U> SearchResponse<U> geoBoundingBoxSelect(String indexName, Class<U> classz, GeoBoundingBoxQuery geoBoundingBoxQuery) {
        SearchResponse<U> searchResponse = esClient.search(s ->
                        s.index(indexName)
                                .query(q -> q.bool(t -> t.must(k -> k.geoBoundingBox(geoBoundingBoxQuery)).queryName("location")))
                , classz);
        return searchResponse;
    }

    @SneakyThrows
    public void getDocs(String indexName) {
        GetIndexResponse getIndexResponse = esClient.indices().get(item -> item.index(indexName));
        System.out.println(getIndexResponse);
    }

    // 判断索引是否存在
    @SneakyThrows
    public void existsIndex(String indexName) {
        BooleanResponse booleanResponse = esClient.indices().exists(item -> item.index(indexName));
        log.info("booleanResponse:{}", booleanResponse.value());
    }

    @SneakyThrows
    public void deleteIndex(String indexName) {
        DeleteIndexResponse deleteIndexResponse = esClient.indices().delete(item -> item.index(indexName));
        log.info("deleteIndexResponse:{}", deleteIndexResponse.acknowledged());
    }
}
