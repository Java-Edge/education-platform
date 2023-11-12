package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.dto.SpecialQueryDTO;
import com.javagpt.back.entity.CourseEntity;
import com.javagpt.back.vo.course.CourseVO;
import com.javagpt.common.req.PageQueryParam;

import java.util.List;

public interface SpecialColumnService extends IService<CourseEntity> {

    List<CourseEntity> selectList();


    /**
     * 专栏列表
     */
    Page<CourseVO> pageListSpecial(PageQueryParam<SpecialQueryDTO> pageQueryParam);

    void pv( Integer itemId);
}
