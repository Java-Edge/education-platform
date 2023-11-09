package com.javagpt.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.javagpt.back.converter.ComDiscussionConverter;
import com.javagpt.back.dto.InnerRecommendQueryDTO;
import com.javagpt.back.entity.CompanyDiscussion;
import com.javagpt.back.service.CompanyDiscussionService;
import com.javagpt.back.vo.CompanyDiscussionVO;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.common.resp.ResultBody;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/company-discussion")
public class CompanyDiscussionController {

    @Resource
    private CompanyDiscussionService companyDiscussionService;

    @PostMapping("/selectByCondition")
    public ResultBody selectByCondition(@RequestBody PageQueryParam<InnerRecommendQueryDTO> pageQueryParam){
        IPage<CompanyDiscussion> companyDiscussionIPage = companyDiscussionService.selectByCondition(pageQueryParam);
        List<CompanyDiscussionVO> companyDiscussionVOS = Lists.newArrayList();
        for (CompanyDiscussion companyDiscussion: companyDiscussionIPage.getRecords()) {
            companyDiscussionVOS.add(ComDiscussionConverter.INSTANCE.toVO(companyDiscussion));
        }
        IPage<CompanyDiscussionVO> companyDiscussionVOPage = new Page<>();
        companyDiscussionVOPage.setRecords(companyDiscussionVOS);
        companyDiscussionVOPage.setTotal(companyDiscussionIPage.getTotal());
        return ResultBody.success(companyDiscussionVOPage);
    }
}

