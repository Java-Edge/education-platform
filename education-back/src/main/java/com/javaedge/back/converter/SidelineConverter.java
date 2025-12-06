package com.javaedge.back.converter;

import com.javaedge.back.entity.Sideline;
import com.javaedge.back.vo.SidelineVO;
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
