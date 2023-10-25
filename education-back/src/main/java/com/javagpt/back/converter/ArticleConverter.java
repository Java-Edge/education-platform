package com.javagpt.back.converter;

import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.entity.InnerRecommend;
import com.javagpt.back.vo.ArticleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author JavaEdge
 * @date 2023/10/25
 */
@Mapper
public interface ArticleConverter {

    ArticleConverter INSTANCE = Mappers.getMapper(ArticleConverter.class);

    ArticleVO toVO(ArticleEntity articleEntity);
}
