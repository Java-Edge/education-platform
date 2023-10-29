package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.entity.DiscussOffer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mkingg
 * @since 2023-10-29
 */
public interface DiscussOfferService extends IService<DiscussOffer> {

		/**
		 * 分页查询
		 * @param current 当前页
		 * @param size 每页显示条数，默认 10
		 * @return
		 */
		Page<DiscussOffer> selectPage(Integer current, Integer size);
}
