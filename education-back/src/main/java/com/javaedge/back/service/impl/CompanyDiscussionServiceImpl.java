package com.javaedge.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaedge.back.dto.InnerRecommendQueryDTO;
import com.javaedge.back.entity.CompanyDiscussion;
import com.javaedge.back.mapper.CompanyDiscussionMapper;
import com.javaedge.back.service.CompanyDiscussionService;
import com.javaedge.common.constant.CommonConstants;
import com.javaedge.common.req.PageQueryParam;
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
