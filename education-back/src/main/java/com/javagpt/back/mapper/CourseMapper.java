package com.javagpt.back.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.CourseDTO;
import com.javagpt.back.entity.CourseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javagpt.back.vo.course.CourseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author MSIK
 * @description 针对表【source_course】的数据库操作Mapper
 * @createDate 2023-06-27 20:48:40
 * @Entity com.javagpt.back.entity.SourceCourseEntity
 */
@Mapper
public interface CourseMapper extends BaseMapper<CourseEntity> {


    /**
     * 分页查询
     *
     * @param page
     * @param dto
     * @return
     */
    Page<CourseVO> selectByPage(@Param("Page") Page<CourseVO> page, @Param("dto") CourseDTO dto);
}




