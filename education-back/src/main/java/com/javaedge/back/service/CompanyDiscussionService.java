package com.javaedge.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaedge.back.dto.InnerRecommendQueryDTO;
import com.javaedge.back.entity.CompanyDiscussion;
import com.javaedge.common.req.PageQueryParam;

public interface CompanyDiscussionService extends IService<CompanyDiscussion> {

    IPage<CompanyDiscussion> selectByCondition(PageQueryParam<InnerRecommendQueryDTO> pageQueryParam);
}
