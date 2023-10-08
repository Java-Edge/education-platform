package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.entity.CourseEntity;
import com.javagpt.back.entity.RoadMap;
import com.javagpt.back.entity.RoadMapDetail;
import com.javagpt.back.vo.course.CourseRoadmapVO;
import com.javagpt.back.vo.course.CourseVO;

import java.util.List;


public interface RoadmapService extends IService<RoadMap> {
    Page<RoadMap> getRoadmap(Integer categoryId, Integer current, Integer size);

    List<RoadMapDetail> getRoadMapDetail(Integer roadMapId);
}
