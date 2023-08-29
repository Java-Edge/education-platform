package com.javagpt.back.mapper;

import com.javagpt.back.entity.CourseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author MSIK
* @description 针对表【source_course】的数据库操作Mapper
* @createDate 2023-06-27 20:48:40
* @Entity com.javagpt.back.entity.SourceCourseEntity
*/
@Mapper
public interface CourseMapper extends BaseMapper<CourseEntity> {

}




