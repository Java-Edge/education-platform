package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.entity.DiscussOffer;
import com.javagpt.back.mapper.DiscussOfferMapper;
import com.javagpt.back.service.DiscussOfferService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mkingg
 * @since 2023-10-29
 */
@Service
public class DiscussOfferServiceImpl extends ServiceImpl<DiscussOfferMapper, DiscussOffer> implements DiscussOfferService {

		@Override
		public Page<DiscussOffer> selectPage(Integer current, Integer size) {
				Page<DiscussOffer> page = new Page<>(current, size);
				QueryWrapper<DiscussOffer> queryWrapper = new QueryWrapper<>();
				queryWrapper.orderByDesc("create_time");
				return this.getBaseMapper().selectPage(page, queryWrapper);
		}
}
