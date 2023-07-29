package com.javagpt.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.InterviewArticleDto;
import com.javagpt.back.dto.PageQueryParam;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.entity.Career;
import com.javagpt.back.entity.InterviewExperienceArticleEntity;
import com.javagpt.back.entity.SourceCourseEntity;
import com.javagpt.back.service.CareerService;
import com.javagpt.back.service.InterviewExperienceArticleService;
import com.javagpt.back.vo.ArticleVO;
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

    @Autowired
    private CareerService careerService;


    @PostMapping("/getPage")
    public ResultBody getPage(@RequestBody PageQueryParam<InterviewArticleDto> pageQueryParam){
        Page<InterviewExperienceArticleEntity> page = new Page<>();
        page.setCurrent(pageQueryParam.getPageNo());
        page.setSize(pageQueryParam.getPageSize());

        Page<InterviewExperienceArticleEntity> articleEntityPage = articleService.page(page);

        return ResultBody.success(articleEntityPage);
    }


    @PostMapping("/selectByCondition")
    public ResultBody selectByCondition(@RequestBody PageQueryParam<InterviewArticleDto> pageQueryParam){
        if (pageQueryParam.getParam().getJobId() != null && pageQueryParam.getParam().getJobId() == -1) {
            pageQueryParam.getParam().setJobId(null);
        }
        IPage<ArticleVO> articleVOS = articleService.selectByCondition(pageQueryParam);

        return ResultBody.success(articleVOS);
    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable String id) {
        InterviewExperienceArticleEntity article = articleService.getById(id);
        Integer jobId = article.getJobId();
        if (jobId != null) {
            Career byId = careerService.getById(jobId);
            article.setCareerName(byId.getName());
        }
        return ResultBody.success(article);
    }


}
