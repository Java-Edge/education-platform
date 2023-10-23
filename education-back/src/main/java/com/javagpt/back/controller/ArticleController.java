package com.javagpt.back.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 编辑文章
 * 动态页面展示文章信息
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/save")
    public ResultBody save(@RequestBody ArticleEntity articleEntity) {
        articleService.insert(articleEntity);
        return ResultBody.success("保存文章成功");
    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable Integer id) {
        ArticleEntity article = articleService.selectById(id);
        return ResultBody.success(article);
    }

    @GetMapping("/getByPage")
    public ResultBody getByPage(Integer current, Integer size) {
        Page<ArticleEntity> page = articleService.getByPage(current, size);
        return ResultBody.success(page);
    }

    @PutMapping("/updateById")
    public ResultBody updateById(@RequestBody ArticleEntity article) {
        articleService.updateById(article);
        return ResultBody.success("修改成功");
    }

    @PostMapping("/uploadImg")
    public ResultBody upload(MultipartFile file) {
        articleService.uploadImg(file);
        return ResultBody.success("1");
    }
}
