package com.javaedge.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaedge.back.entity.CoursePO;
import com.javaedge.back.entity.RoadMap;
import com.javaedge.back.entity.RoadMapDetail;
import com.javaedge.back.entity.RoadMapTag;
import com.javaedge.back.mapper.RoadMapDetailMapper;
import com.javaedge.back.mapper.RoadMapTagMapper;
import com.javaedge.back.mapper.RoadMapMapper;
import com.javaedge.back.service.VideoService;
import com.javaedge.back.service.RoadmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoadmapServiceImpl extends ServiceImpl<RoadMapMapper, RoadMap> implements RoadmapService {

    @Autowired
    private RoadMapDetailMapper roadMapDetailMapper;

    @Autowired
    private RoadMapTagMapper roadMapTagMapper;

    @Autowired
    private VideoService videoService;

    @Override
    public Page<RoadMap> getRoadmap(Integer categoryId, Integer current, Integer size) {
        Page<RoadMap> page = new Page<>(current, size);
        QueryWrapper<RoadMap> qw = new QueryWrapper<>();

        /**
         * 如果是要查询热门数据，根据收藏数进行排序即可
         */
        if (categoryId != null && categoryId == 0) {
            qw.select("id", "title","img", "img2", "left(description, 50) description", "collect", "course", "step", "category_id");
            qw.orderByDesc("collect");
            this.getBaseMapper().selectPage(page, qw);
            return page;
        }

        qw.eq(categoryId != null, "category_id", categoryId);
        qw.select("id", "title","img", "img2", "left(description, 50) description", "collect", "course", "step", "category_id");
        this.getBaseMapper().selectPage(page, qw);
        QueryWrapper<RoadMapDetail> qwd = new QueryWrapper<>();
        for (RoadMap roadMap : page.getRecords()) {
            qwd.clear();
            qwd.eq("road_map_id", roadMap.getId());
            Integer courseCount = roadMapDetailMapper.selectCount(qwd).intValue();
            /**
             * 查询的时候，实时维护一下路线的对应课程数和步骤
             */
            if (!roadMap.getStep().equals(courseCount)) {
                roadMap.setStep(courseCount);
                roadMap.setCourse(courseCount);
                this.getBaseMapper().updateById(roadMap);
            }
        }
        return page;
    }

    @Override
    public List<RoadMapDetail> getRoadMapDetail(Integer roadMapId) {
        QueryWrapper<RoadMapDetail> qw = new QueryWrapper<>();
        qw.orderByAsc("map_order");
        qw.eq("road_map_id", roadMapId);
        List<RoadMapDetail> roadMapDetails = roadMapDetailMapper.selectList(qw);
        List<Integer> courseIds = roadMapDetails.stream().map(item -> item.getCourseId()).collect(Collectors.toList());
        /**
         * 如果路线没有对应的课程
         */
        if (courseIds == null || courseIds.size() == 0) {
            return new ArrayList<>();
        }
        Map<Integer, CoursePO> coursesMap = videoService.getCoursesMapById(courseIds);
        for (RoadMapDetail roadMapDetail : roadMapDetails) {
            String[] tagIds = roadMapDetail.getTag().split(",");
            QueryWrapper<RoadMapTag> tqw = new QueryWrapper<>();
            if (tagIds.length > 0) {
                tqw.in("id", tagIds);
            }
            List<String> roadMapTags = roadMapTagMapper.selectList(tqw).stream().map(tag -> tag.getName()).collect(Collectors.toList());
            roadMapDetail.setTags(roadMapTags);
            roadMapDetail.setCourse(coursesMap.get(roadMapDetail.getCourseId()));
        }

        return roadMapDetails;
    }

    @Override
    public List<RoadMap> getRecommendRoad() {
        QueryWrapper<RoadMap> qw = new QueryWrapper<>();
        qw.orderByDesc("collect");
        qw.last("limit 2");
        return this.getBaseMapper().selectList(qw);
    }

    @Override
    public Map<Integer, List<RoadMap>> getAll() {
        QueryWrapper<RoadMap> qw = new QueryWrapper<>();
        List<RoadMap> roadMaps = this.getBaseMapper().selectList(qw);
        Map<Integer, List<RoadMap>> result = new HashMap<>();
        for (RoadMap roadMap : roadMaps) {
            List<RoadMap> rms = result.getOrDefault(roadMap.getCategoryId(), new ArrayList<>());
            rms.add(roadMap);
            result.put(roadMap.getCategoryId(), rms);
        }

        // 查出推荐学习路线
        QueryWrapper<RoadMap> qw2 = new QueryWrapper<>();
        qw2.select("id", "title","img", "img2", "left(description, 50) description", "collect", "course", "step", "category_id");
        qw2.orderByDesc("collect");
        qw2.last("limit 3");
        List<RoadMap> recommend = this.getBaseMapper().selectList(qw2);
        result.put(0, recommend);
        return result;
    }


}
