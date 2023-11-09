package com.javagpt.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.dto.InnerRecommendQueryDTO;
import com.javagpt.back.entity.CompanyDiscussion;
import com.javagpt.common.req.PageQueryParam;

public interface CompanyDiscussionService extends IService<CompanyDiscussion> {

    IPage<CompanyDiscussion> selectByCondition(PageQueryParam<InnerRecommendQueryDTO> pageQueryParam);
}
