package com.javaedge.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaedge.back.dto.InnerRecommendQueryDTO;
import com.javaedge.back.entity.InnerRecommend;
import com.javaedge.common.req.PageQueryParam;

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
