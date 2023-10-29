package com.javagpt.back.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.entity.DiscussOffer;
import com.javagpt.back.entity.RecruitPO;
import com.javagpt.back.service.DiscussOfferService;
import com.javagpt.common.resp.ResultBody;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
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
				Page<DiscussOffer> page = discussOfferService.selectPage(current,size);
				return ResultBody.success(page);
		}
}

