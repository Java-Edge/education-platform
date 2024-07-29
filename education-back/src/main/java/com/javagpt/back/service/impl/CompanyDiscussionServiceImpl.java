package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.dto.InnerRecommendQueryDTO;
import com.javagpt.back.entity.CompanyDiscussion;
import com.javagpt.back.mapper.CompanyDiscussionMapper;
import com.javagpt.back.service.CompanyDiscussionService;
import com.javagpt.common.constant.CommonConstants;
import com.javagpt.common.req.PageQueryParam;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CompanyDiscussionServiceImpl extends ServiceImpl<CompanyDiscussionMapper, CompanyDiscussion>
        implements CompanyDiscussionService {

    @Resource
    private CompanyDiscussionMapper companyDiscussionMapper;

    @Override
    public IPage<CompanyDiscussion> selectByCondition(PageQueryParam<InnerRecommendQueryDTO> pageQueryParam) {
        if (Objects.nonNull(pageQueryParam.getParam()) && Objects.nonNull(pageQueryParam.getParam().getJobId())
                // jobId=-1：查询全部岗位
                && pageQueryParam.getParam().getJobId() == CommonConstants.select_all) {
            pageQueryParam.getParam().setJobId(null);
        }

        Page<CompanyDiscussion> page = new Page<>();
        page.setSize(pageQueryParam.getPageSize());
        page.setCurrent(pageQueryParam.getPageNo());
        if (pageQueryParam.getParam() == null) {
            pageQueryParam.setParam(new InnerRecommendQueryDTO());
        }
        return companyDiscussionMapper.listByCondition(page, pageQueryParam.getParam());
    }
}
