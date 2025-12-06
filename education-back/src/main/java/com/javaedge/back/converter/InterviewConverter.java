package com.javaedge.back.converter;

import com.javaedge.back.entity.InterviewEntity;
import com.javaedge.back.vo.InterviewVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author JavaEdge
 * @date 2023/10/24
 */
@Mapper
public interface InterviewConverter {

    InterviewConverter INSTANCE = Mappers.getMapper(InterviewConverter.class);

    InterviewVO toVO(InterviewEntity interviewEntity);
}
