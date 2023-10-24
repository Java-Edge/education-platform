package com.javagpt.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.javagpt.back.dto.ArticleDTO;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.back.entity.InterviewEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.vo.ArticleVO;

public interface InterviewService extends IService<InterviewEntity> {


    /**
     * 条件查询
     *
     * @param pageQueryParam
     * @return
     */
    IPage<ArticleVO> selectByCondition(PageQueryParam<ArticleDTO> pageQueryParam);

    /**
     * 获取文章
     *
     * @param id
     * @return
     */
    InterviewEntity articleOf(String id);
}
