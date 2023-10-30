package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.entity.DiscussOfferEntity;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mkingg
 * @since 2023-10-29
 */
public interface DiscussOfferService extends IService<DiscussOfferEntity> {

		/**
		 * 分页查询
		 * @param current 当前页
		 * @param size 每页显示条数，默认 10
		 * @return
		 */
		Page<DiscussOfferEntity> selectPage(Integer current, Integer size);
}
