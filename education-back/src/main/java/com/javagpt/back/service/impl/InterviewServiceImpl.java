package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.dto.InterviewDTO;
import com.javagpt.back.entity.Career;
import com.javagpt.back.entity.InterviewEntity;
import com.javagpt.back.mapper.CareerMapper;
import com.javagpt.back.mapper.InterviewMapper;
import com.javagpt.back.service.InterviewService;
import com.javagpt.common.constant.Constants;
import com.javagpt.common.req.PageQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class InterviewServiceImpl extends ServiceImpl<InterviewMapper, InterviewEntity>
    implements InterviewService {

    @Autowired
    private InterviewMapper interviewMapper;

    @Autowired
    private CareerMapper careerMapper;

    @Override
    public IPage<InterviewEntity> selectByCondition(PageQueryParam<InterviewDTO> pageQueryParam) {
        if (Objects.nonNull(pageQueryParam.getParam()) && Objects.nonNull(pageQueryParam.getParam().getJobId())
                // jobId=-1：查询全部岗位
                && pageQueryParam.getParam().getJobId() == Constants.select_all) {
            pageQueryParam.getParam().setJobId(null);
        }

        Page<InterviewEntity> interviewEntityPage = new Page<>();
        interviewEntityPage.setSize(pageQueryParam.getPageSize());
        interviewEntityPage.setCurrent(pageQueryParam.getPageNo());
        if (pageQueryParam.getParam() == null){
            pageQueryParam.setParam(new InterviewDTO());
        }
        return interviewMapper.selectByCondition(pageQueryParam.getParam(), interviewEntityPage);
    }

    @Override
    public InterviewEntity articleOf(String id) {
        InterviewEntity article = getById(id);
        Integer jobId = article.getJobId();
        if (Objects.nonNull(jobId)) {
            Career career = careerMapper.selectById(jobId);
            article.setCareerName(career.getName());
        }
        return article;
    }
}




