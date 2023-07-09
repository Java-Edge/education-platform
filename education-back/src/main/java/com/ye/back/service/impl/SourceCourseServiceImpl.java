package com.ye.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ye.back.entity.SourceCourseEntity;
import com.ye.back.mapper.SourceCourseMapper;
import com.ye.back.service.SourceCourseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MSIK
 * @description 针对表【source_course】的数据库操作Service实现
 * @createDate 2023-07-09 13:40:08
 */
@Service
public class SourceCourseServiceImpl extends ServiceImpl<SourceCourseMapper, SourceCourseEntity>
        implements SourceCourseService {

    @Override
    public List<SourceCourseEntity> getFiveCourse() {

        LambdaQueryWrapper<SourceCourseEntity> wrapper = new LambdaQueryWrapper<SourceCourseEntity>()
                .orderByDesc(SourceCourseEntity::getCreatTime)
                .last("limit 5");
        List<SourceCourseEntity> list = this.getBaseMapper().selectList(wrapper);
        return list;
    }

    @Override
    public List<SourceCourseEntity> getRecommendCourses() {
        LambdaQueryWrapper<SourceCourseEntity> wrapper = new LambdaQueryWrapper<SourceCourseEntity>()
                .orderByDesc(SourceCourseEntity::getCreatTime)
                .last("limit 6");
        List<SourceCourseEntity> list = this.getBaseMapper().selectList(wrapper);
        return list;
    }
}




