package com.javagpt.back.converter;

import com.javagpt.back.entity.CompanyDiscussion;
import com.javagpt.back.vo.CompanyDiscussionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author JavaEdge
 * @date 2023/10/24
 */
@Mapper
public interface ComDiscussionConverter {

    ComDiscussionConverter INSTANCE = Mappers.getMapper(ComDiscussionConverter.class);

    CompanyDiscussionVO toVO(CompanyDiscussion innerRecommend);
}
