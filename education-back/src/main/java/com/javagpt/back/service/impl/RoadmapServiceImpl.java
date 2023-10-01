package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.entity.CourseEntity;
import com.javagpt.back.entity.RoadMap;
import com.javagpt.back.mapper.RoadmapMapper;
import com.javagpt.back.service.RoadmapService;
import com.javagpt.back.util.BeanHelper;
import com.javagpt.back.vo.course.CourseRoadmapVO;
import com.javagpt.back.vo.course.CourseVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoadmapServiceImpl extends ServiceImpl<RoadmapMapper, RoadMap> implements RoadmapService {

    @Override
    public Page<RoadMap> getRoadmap(Integer categoryId, Integer current, Integer size) {
        Page<RoadMap> page = new Page<>(current, size);
        QueryWrapper<RoadMap> qw = new QueryWrapper<>();
        qw.eq("category_id", categoryId);
        qw.select("id", "title","img", "left(description, 50) description", "collect", "course", "step", "category_id");
        this.getBaseMapper().selectPage(page, qw);
        return page;
    }


}
