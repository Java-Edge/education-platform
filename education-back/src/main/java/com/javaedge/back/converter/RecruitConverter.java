package com.javaedge.back.converter;

import com.javaedge.back.vo.RecruitEntity;
import com.javaedge.back.vo.RecruitVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author JavaEdge
 * @date 2023/10/25
 */
@Mapper
public interface RecruitConverter {

    RecruitConverter INSTANCE = Mappers.getMapper(RecruitConverter.class);

    RecruitVO toVO(RecruitEntity recruitEntity);
}
