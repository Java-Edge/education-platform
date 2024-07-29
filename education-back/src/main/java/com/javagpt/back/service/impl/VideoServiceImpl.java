package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.dto.CourseDTO;
import com.javagpt.back.entity.CoursePO;
import com.javagpt.back.mapper.CourseMapper;
import com.javagpt.back.mapper.DictMapper;
import com.javagpt.back.service.VideoService;
import com.javagpt.back.vo.course.CourseVO;
import com.javagpt.common.constant.CommonConstants;
import com.javagpt.common.enums.CourseEnum;
import com.javagpt.common.req.PageQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoServiceImpl extends ServiceImpl<CourseMapper, CoursePO> implements VideoService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private DictMapper dictMapper;

    @Override
    public List<CoursePO> getFiveCourse() {

        LambdaQueryWrapper<CoursePO> wrapper = new LambdaQueryWrapper<CoursePO>()
                .eq(CoursePO::getType, CourseEnum.VIDEO.getResultCode())
                .orderByDesc(CoursePO::getCreateTime)
                .last("limit 5");
        List<CoursePO> list = this.getBaseMapper().selectList(wrapper);
        return list;
    }

    @Override
    public List<CoursePO> getRecommendCourses() {
        LambdaQueryWrapper<CoursePO> wrapper = new LambdaQueryWrapper<CoursePO>()
                .eq(CoursePO::getType, CourseEnum.VIDEO.getResultCode())
                .orderByDesc(CoursePO::getPageView)
                .last("limit 8");
        List<CoursePO> list = this.getBaseMapper().selectList(wrapper);
        return list;
    }

    @Override
    public List<CoursePO> selectList() {
        QueryWrapper<CoursePO> qw = new QueryWrapper<>();
        qw.eq("type", CourseEnum.VIDEO.getResultCode());
        qw.orderByDesc(CommonConstants.PV);
        List<CoursePO> courseEntities = this.getBaseMapper().selectList(qw);
        return courseEntities;
    }

    @Override
    public Map<Integer, CoursePO> getCoursesMapById(List<Integer> courseIds) {
        List<CoursePO> courseEntities = this.getBaseMapper().selectBatchIds(courseIds);
        Map<Integer, CoursePO> result = new HashMap<>();
        for (CoursePO course : courseEntities) {
            result.put(course.getId(), course);
        }
        return result;
    }

    @Override
    public Page<CourseVO> search(PageQueryParam<CourseDTO> pageQueryParam) {
        Page<CourseVO> page = new Page<>(pageQueryParam.getPageNo(), pageQueryParam.getPageSize());
        Page<CourseVO> courseVOPage = courseMapper.selectByPage(page, pageQueryParam.getParam());
        return courseVOPage;
    }
}




