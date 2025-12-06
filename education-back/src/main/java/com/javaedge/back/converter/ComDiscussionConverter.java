package com.javaedge.back.converter;

import com.javaedge.back.entity.CompanyDiscussion;
import com.javaedge.back.vo.CompanyDiscussionVO;
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
