package com.javagpt.back.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.alibaba.fastjson.JSONObject;
import com.javagpt.back.dto.BasActivity;
import com.javagpt.back.entity.UserPO;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.common.util.EsDocumentUtils;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.Queries;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author JavaEdge
 * @date 2024/9/14
 */
@RestController
@RequestMapping("es")
public class ESController {

    @Resource
    private ElasticsearchTemplate esTemplate;

    // Document名称
    private static final String indexName = "bas_activity";
    private static final Long activityId = 318799649324400640L;

    @Resource
    private ElasticsearchClient esClient;

    @Autowired
    private EsDocumentUtils esDocUtils;

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

    /**
     * 活动相关es测试方法
     *      如果索引已经创建---更新字段类型的话--要先删除已存在索引
     */
    @GetMapping("range")
    public Object range() {
// SpringBoot3.4.0后过时
//        Query query = RangeQuery
//                .of(r -> r.field("activityAmount")
//                        .gte(JsonData.of(0))
//                        .lte(JsonData.of(10)))
//                ._toQuery();
        Query query = Query.of(q -> q
                .range(r -> r
                        .number(nr -> nr
                                .field("activityAmount")
                                .gte(0.0)  // Use Double directly
                                .lte(10.0) // Use Double directly
                        )
                )
        );

        SearchResponse<UserPO> response = esDocUtils.getDocsByCustomize(indexName, UserPO.class, query);
        for (Hit<UserPO> hit : response.hits().hits()) {
            System.out.println(JSONObject.toJSON(hit.source()));
        }
        return ResultBody.success();
    }

    @SneakyThrows
    @GetMapping("queryIndex")
    public Object queryIndex() {
        esDocUtils.getDocs(indexName);

        return ResultBody.success();
    }

    @GetMapping("existsIndex")
    public Object existsIndex() {
        esDocUtils.existsIndex(indexName);
        return ResultBody.success();
    }


    @PostMapping("deleteIndex")
    public Object deleteIndex() {
        IndexCoordinates ico = IndexCoordinates.of(indexName);
        boolean deleted = esTemplate.indexOps(ico).delete();

        esDocUtils.deleteIndex(indexName);
        return ResultBody.success();
    }

    @PostMapping("existsDocument")
    public Object existsDocument() {
        BooleanResponse response = esDocUtils.existsDocument(indexName, activityId.toString());
        System.out.println(response.value());
        return ResultBody.success();
    }

    @GetMapping("getDocument")
    public Object getDocument() {
        GetResponse<BasActivity> response = esDocUtils.getDocument(indexName, activityId.toString(), BasActivity.class);
        System.out.println(JSONObject.toJSON(response.source()));
        return ResultBody.success();
    }

    // 根据某个字段精确查询数据---term查询(完全匹配)
    @GetMapping("getDocumentsByField")
    public Object getDocumentsByField() {
        SearchResponse<BasActivity> response = esDocUtils.getDocumentsByField(indexName, BasActivity.class, "activityCity.keyword", "苏州市");
        for (Hit<BasActivity> hit : response.hits().hits()) {
            System.out.println(JSONObject.toJSON(hit.source()));
        }
        return ResultBody.success();
    }

    // 自定义查询条件查询
    @GetMapping("getDocumentsByCustomize")
    public Object getDocumentsByCustomize() {
        // old versions Query query= QueryBuilders.termQueryAsQuery("id","318799649324400640");
        Query query = Queries.termQueryAsQuery("activityCity.keyword", "苏州市");
        SearchResponse<BasActivity> response = esDocUtils.getDocumentsByCustomize(indexName, BasActivity.class, query);
        for (Hit<BasActivity> hit : response.hits().hits()) {
            System.out.println(JSONObject.toJSON(hit.source()));
        }
        return ResultBody.success();
    }

    /**
     * 多条件活动列表查询
     * 组合查询语句
     */
    @GetMapping("getDocumentsByCustomizes")
    public Object getDocumentsByCustomizes() {
        List<Query> queryList = new ArrayList<>();
        //1、term关键词精确查询-ES默认分词规则不能支持中文-通过安装IK Analysis for Elasticsearch支持中文分词才可以支持查询中文
        Query query1 = Queries.termQueryAsQuery("activityCity.keyword", "苏州市");
        //2、ids-多个指定id查询
        Query query2 = Queries.idsQueryAsQuery(Arrays.asList(activityId.toString(), "45646546456"));

        //3、range-范围查询-活动报名费用在0元到10元之间的活动
// SpringBoot3.4.0后过时
//        Query query3 = RangeQuery.of(r -> r.field("activityAmount").gte(JsonData.of(0)).lte(JsonData.of(10)))._toQuery();
        Query query3 = RangeQuery.of(r -> r
                .number(nr -> nr
                        .field("activityAmount")
                        .gte(0.0)  // Use Double directly
                        .lte(10.0) // Use Double directly
                )
        )._toQuery();
        //4、prefix-前缀匹配查询
        Query query4 = PrefixQuery.of(r -> r.field("activityCity.keyword").value("苏"))._toQuery();
        //5、wildcard 通配符查询* 任意多个字符
        Query query5 = Queries.wildcardQueryAsQuery("activityCity.keyword", "*市");
        //6、multi_match-多字段查询
        Query query6 = MultiMatchQuery.of(r -> r.query("陆").fields("activityAddress", "activityCity"))._toQuery();

        queryList.add(query1);
        queryList.add(query2);
        queryList.add(query3);
        queryList.add(query4);
        queryList.add(query5);
        queryList.add(query6);

        SearchResponse<BasActivity> response = esDocUtils.getDocumentsByCustomizeMore(indexName, BasActivity.class, queryList);
        for (Hit<BasActivity> hit : response.hits().hits()) {
            System.out.println(JSONObject.toJSON(hit.source()));
        }
        return ResultBody.success();
    }

    /**
     * 基于城市站点的附近活动列表查询
     */
    @GetMapping("geoDistanceAndCityCode")
    public Object geoDistanceAndCityCode() {
        List<Query> queryList = new ArrayList<>();
        //1、term关键词精确查询-城市编码
        Query query1 = Queries.termQueryAsQuery("cityCode.keyword", "101190401");
        //设定搜索半径
        //根据矩形的左上、右下两个点的坐标，然后画出一个矩形，查询落在该矩形内的都是符合条件的点
        Query query2 = GeoDistanceQuery.of(r ->
                // # 半径
                r.distance("20km").field("location")
                        //# 圆心（经纬坐标）
                        .location(m -> m.latlon(k -> k.lat(31.36897).lon(120.64247)))
        )._toQuery();
        queryList.add(query1);
        queryList.add(query2);
        SearchResponse<BasActivity> response = esDocUtils.getDocumentsByCustomizeMore(indexName, BasActivity.class, queryList);
        for (Hit<BasActivity> hit : response.hits().hits()) {
            System.out.println(JSONObject.toJSON(hit.source()));
        }
        return ResultBody.success();
    }

    @PostMapping("deleteDocument")
    public Object deleteDocument() {
        DeleteResponse response = esDocUtils.deleteDocument(indexName, "299686158885978112");
        System.out.println(response.id());
        return ResultBody.success();
    }

    /**
     * 1、向Document(活动表)中添加数据(文档)
     * 1.1、es、向Document(活动表)中添加数据(文档)
     * 1.2、mysql、向活动表添加一条数据
     * geo_point是Elasticsearch中用于存储坐标点的数据格式，每一个坐标点都有经度和维度信息，geo_point格式的字段无法做自动映射，需要指定数据类型
     */
    @PostMapping("addDocument")
    public Object addDocument() {
        List<Long> activityIds = new ArrayList<>();
        activityIds.add(299686158885978112L);
        activityIds.add(299687016948301824L);
        activityIds.add(299687626166763520L);

        for (int i = 0; i < activityIds.size(); i++) {
            Long id = activityIds.get(i);
            // 要存储数据(文档)
            BasActivity basActivity = new BasActivity();
            basActivity.setId(id);
            // 构建一个创建Doc的请求
            CreateResponse createResponse = esDocUtils.addDocument(indexName, basActivity, id.toString(), BasActivity.class);
            // 打印请求结果
            System.out.println(createResponse.result());
        }
        return ResultBody.success();
    }

    // 更新文档数据-map入参方式
    @PostMapping("updateDocByMap")
    public Object updateDocByMap() {
        // 构建需要修改的内容，这里使用了Map
        Map<String, Object> map = new HashMap<>();
        map.put("activityAmount", 0.02);
        // 构建修改文档的请求
        UpdateResponse<BasActivity> response = esDocUtils.updateDocByMap(indexName, activityId.toString(), map, BasActivity.class);
        System.out.println(response.result());
        return ResultBody.success();
    }

    // 更新文档数据-model入参方式
    @PostMapping("updateDocumentByModel")
    public Object updateDocumentByModel() {
        BasActivity basActivity = new BasActivity();
        basActivity.setId(activityId);
        //构建需要修改的内容
        basActivity.setActivityAmount(new BigDecimal("0.01"));
        // 构建修改文档的请求
        UpdateResponse<BasActivity> response = esDocUtils.updateDocumentByModel(indexName, activityId.toString(), basActivity, BasActivity.class);
        // 打印请求结果
        System.out.println(response.result());
        return ResultBody.success();
    }

    /**
     * 我的附近活动查询-【方案一、基于矩形范围查询】
     * id-299686158885978112-【经度：120.61640，维度：31.33406 (苏州火车站南广场公交枢纽)】
     * id-299687016948301824-【经度：120.61456，维度：31.32579 (苏州市桃坞高级中学)】
     * id-299687626166763520-【经度：120.62515，维度：31.32134 (静思园)】
     */
    @GetMapping("geoBoundingBox")
    public Object geoBoundingBox() {
        //结果为一个活动----因为仅苏州火车站南广场公交枢纽在矩形范围内
        //矩形的左上角坐标-经度：120.61068，维度：31.33882(苏站别苑)
        //矩形的右下角坐标-经度：经度：120.62419，维度：31.33170(苏阀大厦)
        //矩形的右下角坐标-经度：经度：120.62153，维度：31.33318(平门楼宾馆)
        double topLeftLat = 31.33882;
        double topLeftLon = 120.61068;
        double bottomRightLat = 31.33318;
        double bottomRightLon = 120.62153;

        //结果为三个活动----因为全部在矩形范围内
        //矩形的左上角坐标-经度：120.59924，维度：31.34269(大观花园)
        //矩形的右下角坐标-经度：经度：120.63429，维度：31.31083(苏州公园)
//        double topLeftLat=31.34269;
//        double topLeftLon=120.59924;
//        double bottomRightLat=31.31083;
//        double bottomRightLon=120.63429;

        //根据矩形的左上、右下两个点的坐标，然后画出一个矩形，查询落在该矩形内的都是符合条件的点
        GeoBoundingBoxQuery geoBoundingBoxQuery = GeoBoundingBoxQuery.of(r -> r.boundingBox(t ->
                t.tlbr(s ->
                        //指定矩形范围的左上点位坐标(经纬度)
                        s.topLeft(m -> m.latlon(k -> k.lat(topLeftLat).lon(topLeftLon)))
                                //指定矩形范围的右下点位坐标(经纬度)
                                .bottomRight(m -> m.latlon(k -> k.lat(bottomRightLat).lon(bottomRightLon)))

                )
        ).field("location"));

        SearchResponse<BasActivity> response = esDocUtils.geoBoundingBoxSelect(indexName, BasActivity.class, geoBoundingBoxQuery);
        Integer size = response.hits().hits().size();
        System.out.println(String.format("搜索矩形范围内有%s个活动", size));
        /*for (Hit<BasActivity> hit : response.hits().hits()) {
            System.out.println(JSONObject.toJSON(hit.source()));
        }*/
        return ResultBody.success();
    }

    /**
     * 我的附近活动查询-【方案二、基于距离查询】
     * <p>
     * id-299686158885978112-【经度：120.61640，维度：31.33406 (苏州火车站南广场公交枢纽)】
     * id-299687016948301824-【经度：120.61456，维度：31.32579 (苏州市桃坞高级中学)】
     * id-299687626166763520-【经度：120.62515，维度：31.32134 (静思园)】
     */
    @GetMapping("geoDistance")
    public Object geoDistance() {
        //苏州火车站(经度：120.61708，维度：31.33663)为坐标
        double lat = 31.33663;
        double lon = 120.61708;
//        String distance="0.1km";//0个
//        String distance="0.5km";//1个
        String distance = "1.5km";//2个
//        String distance="5km";//3个

        //设定搜索半径
        GeoDistanceQuery geoDistanceQuery = GeoDistanceQuery.of(r ->
                // # 半径
                r.distance(distance)
                        //搜索字段
                        .field("location")
                        //# 圆心（经纬坐标）
                        .location(m -> m.latlon(k -> k.lat(lat).lon(lon)))
        );
        SearchResponse<BasActivity> response = esDocUtils.geoDistanceSelect(indexName, BasActivity.class, geoDistanceQuery);
        Integer size = response.hits().hits().size();
        System.out.println(String.format("经纬度(%s,%s)-搜索半径%s范围内有%s个活动", lon, lat, distance, size));
        /*for (Hit<BasActivity> hit : response.hits().hits()) {
            System.out.println(JSONObject.toJSON(hit.source()));
        }*/
        return ResultBody.success();
    }

}