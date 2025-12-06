package com.javaedge.back.converter;

import com.javaedge.back.vo.course.CourseVO;
import com.javaedge.back.vo.course.SpecialColumnVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author JavaEdge
 * @date 2023/10/24
 */
@Mapper
public interface CourseConverter {

    CourseConverter INSTANCE = Mappers.getMapper(CourseConverter.class);

    SpecialColumnVO toVO(CourseVO courseVO);
}
