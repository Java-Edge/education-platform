package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.entity.RoadMap;
import com.javagpt.back.entity.RoadMapDetail;

import java.util.List;
import java.util.Map;


public interface RoadmapService extends IService<RoadMap> {
    Page<RoadMap> getRoadmap(Integer categoryId, Integer current, Integer size);

    List<RoadMapDetail> getRoadMapDetail(Integer roadMapId);

    List<RoadMap> getRecommendRoad();

    Map<Integer, List<RoadMap>> getAll();
}
