package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.dto.ArticleDTO;
import com.javagpt.common.constant.Constants;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.back.entity.Career;
import com.javagpt.back.entity.InterviewEntity;
import com.javagpt.back.mapper.CareerMapper;
import com.javagpt.back.service.InterviewService;
import com.javagpt.back.mapper.InterviewMapper;
import com.javagpt.back.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class InterviewServiceImpl extends ServiceImpl<InterviewMapper, InterviewEntity>
    implements InterviewService {

    @Autowired
    private InterviewMapper articleMapper;

    @Autowired
    private CareerMapper careerMapper;

    @Override
    public IPage<ArticleVO> selectByCondition(PageQueryParam<ArticleDTO> pageQueryParam) {
        if (Objects.nonNull(pageQueryParam.getParam()) && Objects.nonNull(pageQueryParam.getParam().getJobId())
                // jobId=-1：查询全部岗位
                && pageQueryParam.getParam().getJobId() == Constants.select_all) {
            pageQueryParam.getParam().setJobId(null);
        }

        Page<ArticleVO> articleVOPage = new Page<>();
        articleVOPage.setSize(pageQueryParam.getPageSize());
        articleVOPage.setCurrent(pageQueryParam.getPageNo());
        if (pageQueryParam.getParam() == null){
            pageQueryParam.setParam(new ArticleDTO());
        }
        return articleMapper.selectByCondition(pageQueryParam.getParam(), articleVOPage);
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




