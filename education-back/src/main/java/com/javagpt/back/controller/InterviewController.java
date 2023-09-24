package com.javagpt.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.ArticleDTO;
import com.javagpt.back.dto.PageQueryParam;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.Career;
import com.javagpt.back.entity.InterviewEntity;
import com.javagpt.back.service.CareerService;
import com.javagpt.back.service.InterviewService;
import com.javagpt.back.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interview")
public class InterviewController {

    @Autowired
    private InterviewService articleService;


    @PostMapping("/selectByCondition")
    public ResultBody selectByCondition(@RequestBody PageQueryParam<ArticleDTO> pageQueryParam){
        IPage<ArticleVO> articleVOIPage = articleService.selectByCondition(pageQueryParam);
        return ResultBody.success(articleVOIPage);
    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable String id) {
        InterviewEntity article = articleService.articleOf(id);
        return ResultBody.success(article);
    }


}
