package com.javagpt.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.javagpt.back.converter.InterviewConverter;
import com.javagpt.back.dto.InterviewDTO;
import com.javagpt.back.entity.InterviewEntity;
import com.javagpt.back.service.InterviewService;
import com.javagpt.back.vo.InterviewVO;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.common.resp.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 面经页面入口
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
        return ResultBody.success(interviewVOIPage);
    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable String id) {
        InterviewEntity article = articleService.articleOf(id);
        return ResultBody.success(article);
    }
}
