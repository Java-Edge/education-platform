package com.javagpt.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.javagpt.back.dto.ArticleDTO;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.back.entity.InterviewEntity;
import com.javagpt.back.service.InterviewService;
import com.javagpt.back.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 面经页面入口
 */
@RestController
@RequestMapping("/interview")
public class InterviewController {

    @Autowired
    private InterviewService articleService;


    @PostMapping("/selectByCondition")
    public ResultBody selectByCondition(@RequestBody PageQueryParam<ArticleDTO> pageQueryParam) {
        IPage<ArticleVO> articleVOIPage = articleService.selectByCondition(pageQueryParam);
        return ResultBody.success(articleVOIPage);
    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable String id) {
        InterviewEntity article = articleService.articleOf(id);
        return ResultBody.success(article);
    }
}
