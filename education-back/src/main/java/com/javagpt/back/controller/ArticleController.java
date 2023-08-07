package com.javagpt.back.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/save")
    public ResultBody save(@RequestBody ArticleEntity articleEntity) {
        articleEntity.setCreateTime(LocalDateTime.now());
        // todo 插入用户，以及封面未加上
        articleService.save(articleEntity);
        return ResultBody.success("保存文章成功");
    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable Integer id) {
        ArticleEntity article = articleService.getById(id);
        return ResultBody.success(article);
    }

    @GetMapping("/getByPage")
    public ResultBody getByPage(Integer current, Integer size, ArticleEntity article) {
        Page<ArticleEntity> page = new Page<>(current, size);
        QueryWrapper<ArticleEntity> qw = new QueryWrapper<>();
        qw.eq("delete_flag", 0);
        qw.eq(article.getType() != null, "type", article.getType());
        qw.orderByDesc("create_time");
        articleService.page(page, qw);
        return ResultBody.success(page);
    }

    @PutMapping("/updateById")
    public ResultBody updateById(@RequestBody ArticleEntity article) {
        articleService.updateById(article);
        return ResultBody.success("修改成功");
    }

    @PostMapping("/uploadImg")
    public ResultBody upload(MultipartFile file) {
        File targetFile = new File("D:/myImg/" + file.getOriginalFilename());

        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(file);
        return ResultBody.success("1");
    }

    @GetMapping("/getRanking")
    public ResultBody getRanking(ArticleEntity article) {
        List<ArticleEntity> articles = articleService.getRanking(article);
        return ResultBody.success(articles);
    }

}
