package com.javagpt.back.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.alibaba.fastjson.JSONObject;
import com.javagpt.back.entity.UserPO;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.common.util.EsDocumentUtils;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JavaEdge
 * @date 2024/9/14
 */
@RestController
@RequestMapping("es")
public class ESController {

    @Resource
    private ElasticsearchTemplate esTemplate;

    private static final String indexName="bas_activity";

    @Resource
    private ElasticsearchClient esClient;

    @Autowired
    private EsDocumentUtils esDocumentUtils;

    @GetMapping("createIndex")
    public Object createIndex() {
        IndexCoordinates ico = IndexCoordinates.of("bas_activity");

        // 创建索引（如果不存在）
        esTemplate.indexOps(ico).create();

        // 模拟插入一些数据
        List<UserPO> users = new ArrayList<>();
        users.add(new UserPO("user1", 5));  // activityAmount 在 0 到 10 之间
        users.add(new UserPO("user2", 8));  // activityAmount 在 0 到 10 之间
        users.add(new UserPO("user3", 15)); // activityAmount 不在范围内
        users.add(new UserPO("user4", 0));  // activityAmount 边界值
        users.add(new UserPO("user5", 10)); // activityAmount 边界值

        for (UserPO user : users) {
            esTemplate.save(user, ico);
        }

        return ResultBody.success();
    }

    @GetMapping("deleteIndex")
    public Object deleteIndex() {
        IndexCoordinates ico = IndexCoordinates.of("user");
        boolean deleted = esTemplate.indexOps(ico).delete();
        return ResultBody.success();
    }

    @GetMapping("range")
    public Object range() {
        Query query = RangeQuery.of(r -> r.field("activityAmount").gte(JsonData.of(0)).lte(JsonData.of(10)))._toQuery();

        SearchResponse<UserPO> response = esDocumentUtils.getDocumentsByCustomize(indexName, UserPO.class, query);
        for (Hit<UserPO> hit : response.hits().hits()) {
            System.out.println(JSONObject.toJSON(hit.source()));
        }
        return ResultBody.success();
    }

    @SneakyThrows
    public <U> SearchResponse<U> getDocumentsByCustomize(String indexName, Class<U> classz, Query query) {
        SearchResponse<U> searchResponse = esClient.search(s ->
                        s.index(indexName)
                                .query(query)
                , classz);
        return searchResponse;
    }
}