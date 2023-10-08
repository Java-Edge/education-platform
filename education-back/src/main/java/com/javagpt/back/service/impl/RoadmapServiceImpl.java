package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.entity.CourseEntity;
import com.javagpt.back.entity.RoadMap;
import com.javagpt.back.entity.RoadMapDetail;
import com.javagpt.back.entity.RoadMapTag;
import com.javagpt.back.mapper.RoadMapDetailMapper;
import com.javagpt.back.mapper.RoadMapTagMapper;
import com.javagpt.back.mapper.RoadmapMapper;
import com.javagpt.back.service.RoadmapService;
import com.javagpt.back.util.BeanHelper;
import com.javagpt.back.vo.course.CourseRoadmapVO;
import com.javagpt.back.vo.course.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoadmapServiceImpl extends ServiceImpl<RoadmapMapper, RoadMap> implements RoadmapService {

    @Autowired
    private RoadMapDetailMapper roadMapDetailMapper;

    @Autowired
    private RoadMapTagMapper roadMapTagMapper;

    @Override
    public Page<RoadMap> getRoadmap(Integer categoryId, Integer current, Integer size) {
        Page<RoadMap> page = new Page<>(current, size);
        QueryWrapper<RoadMap> qw = new QueryWrapper<>();
        qw.eq("category_id", categoryId);
        qw.select("id", "title","img", "left(description, 50) description", "collect", "course", "step", "category_id");
        this.getBaseMapper().selectPage(page, qw);
        return page;
    }

    @Override
    public List<RoadMapDetail> getRoadMapDetail(Integer roadMapId) {
        QueryWrapper<RoadMapDetail> qw = new QueryWrapper<>();
        qw.orderByAsc("map_order");
        qw.eq("road_map_id", roadMapId);
        List<RoadMapDetail> roadMapDetails = roadMapDetailMapper.selectList(qw);
        for (RoadMapDetail roadMapDetail : roadMapDetails) {
            String[] tagIds = roadMapDetail.getTag().split(",");
            QueryWrapper<RoadMapTag> tqw = new QueryWrapper<>();
            tqw.in("id", tagIds);
            List<String> roadMapTags = roadMapTagMapper.selectList(tqw).stream().map(tag -> tag.getName()).collect(Collectors.toList());
            roadMapDetail.setTags(roadMapTags);
        }
        return roadMapDetails;
    }


}
