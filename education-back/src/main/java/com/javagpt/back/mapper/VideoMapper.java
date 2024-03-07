package com.javagpt.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.CourseDTO;
import com.javagpt.back.entity.CoursePO;
import com.javagpt.back.vo.course.CourseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VideoMapper extends BaseMapper<CoursePO> {

    Page<CourseVO> selectByPage(@Param("page") Page<CourseVO> page, @Param("dto") CourseDTO dto);
}




