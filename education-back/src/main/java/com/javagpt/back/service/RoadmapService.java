package com.javagpt.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.dto.PageQueryParam;
import com.javagpt.back.entity.CourseEntity;
import com.javagpt.back.vo.course.CourseRoadmapVO;
import com.javagpt.back.vo.course.CourseVO;


public interface RoadmapService extends IService<CourseEntity> {
    Page<CourseRoadmapVO> getRoadmap(Integer categoryId, Integer current, Integer size);

    Page<CourseVO> getRouteItems(Integer parentId, Integer current, Integer size);
}
