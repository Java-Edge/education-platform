package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.entity.CourseEntity;
import com.javagpt.back.mapper.CourseMapper;
import com.javagpt.back.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MSIK
 * @description 针对表【source_course】的数据库操作Service实现
 * @createDate 2023-07-09 13:40:08
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, CourseEntity>
        implements CourseService {

    @Override
    public List<CourseEntity> getFiveCourse() {

        LambdaQueryWrapper<CourseEntity> wrapper = new LambdaQueryWrapper<CourseEntity>()
                .eq(CourseEntity::getType, 0)
                .orderByDesc(CourseEntity::getCreateTime)
                .last("limit 5");
        List<CourseEntity> list = this.getBaseMapper().selectList(wrapper);
        return list;
    }

    @Override
    public List<CourseEntity> getRecommendCourses() {
        LambdaQueryWrapper<CourseEntity> wrapper = new LambdaQueryWrapper<CourseEntity>()
                .eq(CourseEntity::getType, 0)
                .orderByDesc(CourseEntity::getCreateTime)
                .last("limit 8");
        List<CourseEntity> list = this.getBaseMapper().selectList(wrapper);
        return list;
    }

    @Override
    public List<CourseEntity>selectList() {
        QueryWrapper<CourseEntity> qw = new QueryWrapper<>();
        qw.eq("type", 0);
        List<CourseEntity> courseEntities = this.getBaseMapper().selectList(qw);
        return courseEntities;
    }

    @Override
    public Map<Integer, CourseEntity> getCoursesMapById(List<Integer> courseIds) {
        List<CourseEntity> courseEntities = this.getBaseMapper().selectBatchIds(courseIds);
        Map<Integer, CourseEntity> result = new HashMap<>();
        for (CourseEntity course : courseEntities) {
            result.put(course.getId(), course);
        }
        return result;
    }
}




