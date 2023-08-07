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
        // 如果传入的 jobId 是 -1 表示查询全部岗位
        if (pageQueryParam.getParam() != null && pageQueryParam.getParam().getJobId() != null && pageQueryParam.getParam().getJobId() == -1) {
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
