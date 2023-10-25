package com.javagpt.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.dto.InterviewDTO;
import com.javagpt.back.entity.InterviewEntity;
import com.javagpt.common.req.PageQueryParam;

public interface InterviewService extends IService<InterviewEntity> {


    /**
     * 条件查询
     */
    IPage<InterviewEntity> selectByCondition(PageQueryParam<InterviewDTO> pageQueryParam);

    /**
     * 获取文章
     *
     * @param id
     * @return
     */
    InterviewEntity articleOf(String id);
}
