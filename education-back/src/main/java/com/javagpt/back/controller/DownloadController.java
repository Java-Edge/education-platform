package com.javagpt.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.javagpt.back.converter.ArticleConverter;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.service.DownloadService;
import com.javagpt.back.vo.ArticleVO;
import com.javagpt.common.resp.ResultBody;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 资料下载板块
 */
@RestController
@RequestMapping("/download")
public class DownloadController {

    @Resource
    private DownloadService downloadService;

    @GetMapping("/listByPage")
    public ResultBody getRanking(Integer current, Integer size) {
        Page<ArticleEntity> articleEntityPage = downloadService.listByPage(current, size);
        List<ArticleVO> articleVOS = Lists.newArrayList();
        for (ArticleEntity innerRecommend: articleEntityPage.getRecords()) {
            articleVOS.add(ArticleConverter.INSTANCE.toVO(innerRecommend));
        }
        IPage<ArticleVO> articleVOPage = new Page<>();
        articleVOPage.setRecords(articleVOS);
        articleVOPage.setTotal(articleEntityPage.getTotal());
        return ResultBody.success(articleVOPage);
    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable Integer id) {
        ArticleEntity article = downloadService.selectById(id);
        return ResultBody.success(article);
    }
}
