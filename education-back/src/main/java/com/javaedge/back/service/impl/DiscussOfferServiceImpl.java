package com.javaedge.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaedge.back.entity.DiscussOfferEntity;
import com.javaedge.back.mapper.DiscussOfferMapper;
import com.javaedge.back.service.DiscussOfferService;
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
public class DiscussOfferServiceImpl extends ServiceImpl<DiscussOfferMapper, DiscussOfferEntity> implements DiscussOfferService {

		@Override
		public Page<DiscussOfferEntity> selectPage(Integer current, Integer size) {
				Page<DiscussOfferEntity> page = new Page<>(current, size);
				QueryWrapper<DiscussOfferEntity> queryWrapper = new QueryWrapper<>();
				queryWrapper.orderByDesc("create_time");
				return this.getBaseMapper().selectPage(page, queryWrapper);
		}
}
