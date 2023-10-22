package com.javagpt.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.CourseDTO;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.back.dto.SpecialQueryDTO;
import com.javagpt.back.entity.CourseEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.vo.course.CourseVO;

import java.util.List;
import java.util.Map;

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
     * @return PV最高的
     */
    List<CourseEntity> getRecommendCourses();

    /**
     * 查询
     *
     * @return
     */
    List<CourseEntity> selectList();

    Map<Integer, CourseEntity> getCoursesMapById(List<Integer> courseIds);

    /**
     * 分页查询课程
     *
     * @param pageQueryParam
     * @return
     */
    Page<CourseVO> search(PageQueryParam<CourseDTO> pageQueryParam);

    /**
     * 专栏列表
     *
     * @param pageQueryParam
     * @return
     */
    Page<CourseVO> getSpecialList(PageQueryParam<SpecialQueryDTO> pageQueryParam);
}
