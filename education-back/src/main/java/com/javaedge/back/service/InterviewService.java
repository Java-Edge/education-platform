package com.javaedge.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaedge.back.dto.InterviewDTO;
import com.javaedge.back.entity.InterviewEntity;
import com.javaedge.common.req.PageQueryParam;

public interface InterviewService extends IService<InterviewEntity> {

    IPage<InterviewEntity> selectByCondition(PageQueryParam<InterviewDTO> pageQueryParam);

    InterviewEntity articleOf(String id);
}
