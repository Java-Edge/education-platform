package com.javagpt.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.dto.InnerRecommendQueryDTO;
import com.javagpt.back.entity.InnerRecommend;
import com.javagpt.common.req.PageQueryParam;

public interface InnerRecommendService extends IService<InnerRecommend> {

    /**
     * 条件查询
     */
    IPage<InnerRecommend> selectByCondition(PageQueryParam<InnerRecommendQueryDTO> pageQueryParam);

    /**
     * 获取文章
     */
    InnerRecommend articleOf(String id);
}
