package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.dto.CourseQueryDTO;
import com.javagpt.back.entity.CoursePO;
import com.javagpt.back.vo.course.CourseVO;
import com.javagpt.common.req.PageQueryParam;

import java.util.List;

public interface CourseService extends IService<CoursePO> {

    List<CoursePO> selectList();

    /**
     * 专栏列表
     */
    Page<CourseVO> pageQuery(PageQueryParam<CourseQueryDTO> pageQueryParam);

    void pv( Integer itemId);
}
