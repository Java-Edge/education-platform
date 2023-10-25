package com.javagpt.back.converter;

import com.javagpt.back.entity.InterviewEntity;
import com.javagpt.back.vo.InterviewVO;
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
