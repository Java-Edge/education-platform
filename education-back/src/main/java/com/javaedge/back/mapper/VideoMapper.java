package com.javaedge.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaedge.back.dto.CourseDTO;
import com.javaedge.back.entity.CoursePO;
import com.javaedge.back.vo.course.CourseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VideoMapper extends BaseMapper<CoursePO> {

    Page<CourseVO> selectByPage(@Param("page") Page<CourseVO> page, @Param("dto") CourseDTO dto);
}




