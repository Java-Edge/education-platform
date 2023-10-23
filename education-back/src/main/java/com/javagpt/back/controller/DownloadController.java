package com.javagpt.back.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.service.DownloadService;
import com.javagpt.common.resp.ResultBody;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Page<ArticleEntity> page = downloadService.listByPage(current, size);
        return ResultBody.success(page);
    }

}
