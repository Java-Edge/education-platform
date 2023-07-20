package com.ye.back.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ye.back.dto.ResultBody;
import com.ye.back.entity.ArticleEntity;
import com.ye.back.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
    public ResultBody getByPage(Integer current, Integer size) {
        Page<ArticleEntity> page = new Page<>(current, size);
        QueryWrapper<ArticleEntity> qw = new QueryWrapper<>();
        qw.eq("delete_flag", 0);
        articleService.page(page, qw);
        return ResultBody.success(page);
    }

    @PutMapping("/updateById")
    public ResultBody updateById(@RequestBody ArticleEntity article) {
        articleService.updateById(article);
        return ResultBody.success("修改成功");
    }

}
