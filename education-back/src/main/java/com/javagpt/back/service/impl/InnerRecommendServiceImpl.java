package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.dto.InnerRecommendQueryDTO;
import com.javagpt.back.entity.Career;
import com.javagpt.back.entity.InnerRecommend;
import com.javagpt.back.mapper.CareerMapper;
import com.javagpt.back.mapper.InnerRecommendMapper;
import com.javagpt.back.service.InnerRecommendService;
import com.javagpt.common.constant.Constants;
import com.javagpt.common.req.PageQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class InnerRecommendServiceImpl extends ServiceImpl<InnerRecommendMapper, InnerRecommend>
        implements InnerRecommendService {

    @Autowired
    private InnerRecommendMapper innerRecommendMapper;

    @Autowired
    private CareerMapper careerMapper;

    @Override
    public IPage<InnerRecommend> selectByCondition(PageQueryParam<InnerRecommendQueryDTO> pageQueryParam) {
        if (Objects.nonNull(pageQueryParam.getParam()) && Objects.nonNull(pageQueryParam.getParam().getJobId())
                // jobId=-1：查询全部岗位
                && pageQueryParam.getParam().getJobId() == Constants.select_all) {
            pageQueryParam.getParam().setJobId(null);
        }

        Page<InnerRecommend> page = new Page<>();
        page.setSize(pageQueryParam.getPageSize());
        page.setCurrent(pageQueryParam.getPageNo());
        if (pageQueryParam.getParam() == null) {
            pageQueryParam.setParam(new InnerRecommendQueryDTO());
        }
        return innerRecommendMapper.selectByCondition(page, pageQueryParam.getParam());
    }

    @Override
    public InnerRecommend articleOf(String id) {
        InnerRecommend article = getById(id);
        Integer jobId = article.getJobId();
        if (jobId != null) {
            Career byId = careerMapper.selectById(jobId);
            article.setCareerName(byId.getName());
        }
        return article;
    }

}




