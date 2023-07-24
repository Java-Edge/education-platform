package com.javagpt.back.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.InterviewArticleDto;
import com.javagpt.back.dto.PageQueryParam;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.InterviewExperienceArticleEntity;
import com.javagpt.back.entity.SourceCourseEntity;
import com.javagpt.back.service.InterviewExperienceArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author bubaiwantong
 * @date 2023/7/24 23:39
 * @description this is a class file created by bubaiwantong in 2023/7/24 23:39
 */
@RestController
@RequestMapping("/interview-experience")
public class InterviewController {

    @Autowired
    private InterviewExperienceArticleService articleService;

    @PostMapping("/getPage")
    public ResultBody getPage(@RequestBody PageQueryParam<InterviewArticleDto> pageQueryParam){
        Page<InterviewExperienceArticleEntity> page = new Page<>();
        page.setCurrent(pageQueryParam.getPageNo());
        page.setSize(pageQueryParam.getPageSize());

        page = articleService.page(page);
        return ResultBody.success(page);
    }

}
