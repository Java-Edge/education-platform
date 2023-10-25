package com.javagpt.back.converter;

import com.javagpt.back.entity.InnerRecommend;
import com.javagpt.back.vo.InnerRecommendVO;
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
