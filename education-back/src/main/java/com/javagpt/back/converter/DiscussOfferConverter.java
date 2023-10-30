package com.javagpt.back.converter;

import com.javagpt.back.entity.DiscussOfferEntity;
import com.javagpt.back.vo.DiscussOfferVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author: mkingg
 * @since: 2023-10-30
 */

@Mapper
public interface DiscussOfferConverter {

		DiscussOfferConverter INSTANCE = Mappers.getMapper(DiscussOfferConverter.class);
		DiscussOfferVO toVO(DiscussOfferEntity discussOffer);
}
