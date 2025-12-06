package com.javaedge.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.javaedge.back.converter.ComDiscussionConverter;
import com.javaedge.back.dto.InnerRecommendQueryDTO;
import com.javaedge.back.entity.CompanyDiscussion;
import com.javaedge.back.service.CompanyDiscussionService;
import com.javaedge.back.vo.CompanyDiscussionVO;
import com.javaedge.common.req.PageQueryParam;
import com.javaedge.common.resp.ResultBody;
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

