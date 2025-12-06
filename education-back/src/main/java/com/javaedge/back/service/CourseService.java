package com.javaedge.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaedge.back.dto.CourseQueryDTO;
import com.javaedge.back.entity.CoursePO;
import com.javaedge.back.vo.course.CourseVO;
import com.javaedge.common.req.PageQueryParam;

import java.util.List;

public interface CourseService extends IService<CoursePO> {

    List<CoursePO> selectList();

    /**
     * 专栏列表
     */
    Page<CourseVO> pageQuery(PageQueryParam<CourseQueryDTO> pageQueryParam);

    void pv( Integer itemId);
}
