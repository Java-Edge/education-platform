package com.javaedge.back.converter;

import com.javaedge.back.entity.InnerRecommend;
import com.javaedge.back.vo.InnerRecommendVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author JavaEdge
 * @date 2023/10/24
 */
@Mapper
public interface InnerConverter {

    InnerConverter INSTANCE = Mappers.getMapper(InnerConverter.class);

    InnerRecommendVO toVO(InnerRecommend innerRecommend);
}
