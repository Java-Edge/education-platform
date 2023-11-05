package com.javagpt.back.converter;

import com.javagpt.back.entity.Sideline;
import com.javagpt.back.vo.SidelineVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author JavaEdge
 * @date 2023/11/05
 */
@Mapper
public interface SidelineConverter {

    SidelineConverter INSTANCE = Mappers.getMapper(SidelineConverter.class);

    SidelineVO toVO(Sideline courseVO);
}
