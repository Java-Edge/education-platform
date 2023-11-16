package com.javagpt.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.dto.InterviewDTO;
import com.javagpt.back.entity.InterviewEntity;
import com.javagpt.common.req.PageQueryParam;

public interface InterviewService extends IService<InterviewEntity> {

    IPage<InterviewEntity> selectByCondition(PageQueryParam<InterviewDTO> pageQueryParam);

    InterviewEntity articleOf(String id);
}
