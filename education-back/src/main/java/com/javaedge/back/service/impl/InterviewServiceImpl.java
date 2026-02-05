package com.javaedge.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaedge.back.dto.InterviewDTO;
import com.javaedge.back.entity.Career;
import com.javaedge.back.entity.InterviewEntity;
import com.javaedge.back.mapper.CareerMapper;
import com.javaedge.back.mapper.InterviewMapper;
import com.javaedge.back.service.InterviewService;
import com.javaedge.common.constant.CommonConstants;
import com.javaedge.common.req.PageQueryParam;
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
        // 处理查询全部岗位的情况
        if (Objects.nonNull(pageQueryParam.getParam()) && Objects.nonNull(pageQueryParam.getParam().getJobId())
                // jobId=-1：查询全部岗位
                && pageQueryParam.getParam().getJobId() == CommonConstants.select_all) {
            pageQueryParam.getParam().setJobId(null);
        }

        if (pageQueryParam.getParam() == null){
            pageQueryParam.setParam(new InterviewDTO());
        }

        // 执行数据库查询
        Page<InterviewEntity> interviewEntityPage = new Page<>();
        interviewEntityPage.setSize(pageQueryParam.getPageSize());
        interviewEntityPage.setCurrent(pageQueryParam.getPageNo());

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