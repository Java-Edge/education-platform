package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.dto.CourseDTO;
import com.javagpt.back.entity.CourseEntity;
import com.javagpt.back.vo.course.CourseVO;
import com.javagpt.common.req.PageQueryParam;

import java.util.List;
import java.util.Map;

public interface VideoService extends IService<CourseEntity> {

    /**
     * 获取最近的5个课程信息作为轮播图
     * @return
     */
    List<CourseEntity> getFiveCourse();


    /**
     * 获取推荐课程
     *
     * @return PV最高的
     */
    List<CourseEntity> getRecommendCourses();


    List<CourseEntity> selectList();

    Map<Integer, CourseEntity> getCoursesMapById(List<Integer> courseIds);

    /**
     * 分页查询课程
     */
    Page<CourseVO> search(PageQueryParam<CourseDTO> pageQueryParam);
}
