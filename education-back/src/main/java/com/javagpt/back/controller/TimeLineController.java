package com.javagpt.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.javagpt.back.converter.ArticleConverter;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.service.ArticleService;
import com.javagpt.back.service.UserService;
import com.javagpt.back.vo.ArticleVO;
import com.javagpt.common.enums.SceneTypeEnum;
import com.javagpt.common.resp.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 编辑文章
 * 动态页面入口
 */
@RestController
@RequestMapping("/article")
public class TimeLineController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResultBody save(@RequestBody ArticleEntity articleEntity) {
        articleService.insert(articleEntity);
        return ResultBody.success("保存文章成功");
    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable Integer id) {
        // 校验当前登录用户的可用状态
        userService.checkPermission(id, SceneTypeEnum.TIMELINE_DETAIL_NEED_AUTH_CODE);

        ArticleEntity article = articleService.selectById(id);
        return ResultBody.success(article);
    }

    @GetMapping("/getByPage")
    public ResultBody getByPage(Integer current, Integer size) {
        Page<ArticleEntity> articleEntityPage = articleService.getByPage(current, size);

        List<ArticleVO> articleVOS = Lists.newArrayList();
        for (ArticleEntity innerRecommend: articleEntityPage.getRecords()) {
            articleVOS.add(ArticleConverter.INSTANCE.toVO(innerRecommend));
        }
        IPage<ArticleVO> articleVOPage = new Page<>();
        articleVOPage.setRecords(articleVOS);
        articleVOPage.setTotal(articleEntityPage.getTotal());
        return ResultBody.success(articleVOPage);
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
