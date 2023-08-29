package com.javagpt.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.javagpt.back.entity.CourseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author MSIK
* @description 针对表【source_course】的数据库操作Service
* @createDate 2023-07-09 13:40:08
*/
public interface CourseService extends IService<CourseEntity> {

    /**
     * 获取最近的5个课程信息作为轮播图
     * @return
     */
    List<CourseEntity> getFiveCourse();


    /**
     * 获取推荐课程
     *
     * @return
     */
    List<CourseEntity> getRecommendCourses();

    /**
     * 查询
     *
     * @return
     */
    List<CourseEntity> selectList();
}
