package com.javagpt.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.CourseDTO;
import com.javagpt.back.dto.SpecialQueryDTO;
import com.javagpt.back.entity.CourseEntity;
import com.javagpt.back.vo.course.CourseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CourseMapper extends BaseMapper<CourseEntity> {


    /**
     * 分页查询
     */
    Page<CourseVO> selectByPage(@Param("page") Page<CourseVO> page, @Param("dto") CourseDTO dto);


    /***
     * 分页查询教程表
     */
    Page<CourseVO> queryPage(@Param("page") Page<CourseVO> page, @Param("params") SpecialQueryDTO params);

}




