package com.javagpt.back.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.javagpt.back.converter.DiscussOfferConverter;
import com.javagpt.back.entity.DiscussOfferEntity;
import com.javagpt.back.service.DiscussOfferService;
import com.javagpt.back.vo.DiscussOfferVO;
import com.javagpt.common.resp.ResultBody;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mkingg
 * @since 2023-10-29
 */
@RestController
@RequestMapping("/discuss/offer")
public class DiscussOfferController {

		@Resource
		private DiscussOfferService discussOfferService;

		@GetMapping("/getByPage")
		public ResultBody getByPage(Integer current, Integer size) {
				Page<DiscussOfferEntity> page = discussOfferService.selectPage(current, size);
				List<DiscussOfferVO> discussOfferVOs = Lists.newArrayList();

				for (DiscussOfferEntity discussOfferEntity : page.getRecords()) {
						discussOfferVOs.add(DiscussOfferConverter.INSTANCE.toVO(discussOfferEntity));
				}

				Page<DiscussOfferVO> discussOfferVOPage = new Page<>();
				discussOfferVOPage.setRecords(discussOfferVOs);
				discussOfferVOPage.setTotal(page.getTotal());

				return ResultBody.success(discussOfferVOPage);
		}
}

