package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.entity.CourseEntity;
import com.javagpt.back.entity.RoadMap;
import com.javagpt.back.vo.course.CourseRoadmapVO;
import com.javagpt.back.vo.course.CourseVO;


public interface RoadmapService extends IService<RoadMap> {
    Page<RoadMap> getRoadmap(Integer categoryId, Integer current, Integer size);
}
