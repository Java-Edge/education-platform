package com.javagpt.back.service;

import com.javagpt.back.entity.SourceCourseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author MSIK
* @description 针对表【source_course】的数据库操作Service
* @createDate 2023-07-09 13:40:08
*/
public interface SourceCourseService extends IService<SourceCourseEntity> {

    /**
     * 获取最近的5个课程信息作为轮播图
     * @return
     */
    List<SourceCourseEntity> getFiveCourse();


    /**
     * 获取推荐课程
     *
     * @return
     */
    List<SourceCourseEntity> getRecommendCourses();



}
