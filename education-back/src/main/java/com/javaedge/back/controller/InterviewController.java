package com.javaedge.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.javaedge.back.converter.InterviewConverter;
import com.javaedge.back.dto.InterviewDTO;
import com.javaedge.back.entity.InterviewEntity;
import com.javaedge.back.service.InterviewService;
import com.javaedge.back.vo.InterviewVO;
import com.javaedge.common.req.PageQueryParam;
import com.javaedge.common.resp.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 面经
 */
@RestController
@RequestMapping("/interview")
public class InterviewController {

    @Autowired
    private InterviewService articleService;


    @PostMapping("/selectByCondition")
    public ResultBody selectByCondition(@RequestBody PageQueryParam<InterviewDTO> pageQueryParam) {
        IPage<InterviewEntity> interviewEntityIPage = articleService.selectByCondition(pageQueryParam);
        List<InterviewVO> interviewVOS = Lists.newArrayList();
        for (InterviewEntity interviewEntity: interviewEntityIPage.getRecords()) {
            interviewVOS.add(InterviewConverter.INSTANCE.toVO(interviewEntity));
        }
        IPage<InterviewVO> interviewVOIPage = new Page<>();
        interviewVOIPage.setRecords(interviewVOS);
        interviewVOIPage.setTotal(interviewEntityIPage.getTotal());
        return ResultBody.success(interviewVOIPage);
    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable String id) {
        InterviewEntity article = articleService.articleOf(id);
        return ResultBody.success(article);
    }
}
