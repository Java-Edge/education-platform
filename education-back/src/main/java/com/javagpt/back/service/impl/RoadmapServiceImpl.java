package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.entity.CourseEntity;
import com.javagpt.back.mapper.RoadmapMapper;
import com.javagpt.back.service.RoadmapService;
import com.javagpt.back.util.BeanHelper;
import com.javagpt.back.vo.course.CourseRoadmapVO;
import com.javagpt.back.vo.course.CourseVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoadmapServiceImpl extends ServiceImpl<RoadmapMapper, CourseEntity> implements RoadmapService {

    @Override
    public Page<CourseRoadmapVO> getRoadmap(Integer categoryId, Integer current, Integer size) {
        Page<CourseEntity> page = new Page<>(current, size);
        QueryWrapper<CourseEntity> qw = new QueryWrapper<>();
        qw.eq("course_cat_id", categoryId);
        qw.eq("type",1);
        qw.select("id", "name","image", "left(description, 50)", "price", "parent_id");
        Page<CourseEntity> courseEntityPage = this.getBaseMapper().selectPage(page, qw);
        List<CourseRoadmapVO> list = courseEntityPage.getRecords().stream()
                .map(courseEntity -> {
                    CourseRoadmapVO courseRoadmapVO = new CourseRoadmapVO();
                    BeanHelper.CopySourceIntoDestination(courseEntity, courseRoadmapVO);
                    return courseRoadmapVO;
                }).toList();
        return new Page<CourseRoadmapVO>()
                .setRecords(list)
                .setCurrent(current)
                .setSize(size);
    }

    @Override
    public Page<CourseVO> getRouteItems(Integer parentId, Integer current, Integer size) {
        Page<CourseEntity> page = new Page<>(current, size);
        QueryWrapper<CourseEntity> qw = new QueryWrapper<>();
        qw.eq("parent_id", parentId);
        qw.select("id", "name","image", "left(description, 50)", "creator", "updater","remark","source_url","price","step");
        Page<CourseEntity> courseEntityPage = this.getBaseMapper().selectPage(page, qw);
        List<CourseVO> list = courseEntityPage.getRecords().stream()
                .map(courseEntity -> {
                    CourseVO courseVO = new CourseVO();
                    BeanHelper.CopySourceIntoDestination(courseEntity, courseVO);
                    return courseVO;
                }).toList();
        return new Page<CourseVO>()
                .setRecords(list)
                .setCurrent(current)
                .setSize(size);
    }
}
