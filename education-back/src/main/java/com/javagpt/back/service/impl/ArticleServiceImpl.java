package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.mapper.ArticleMapper;
import com.javagpt.back.service.ArticleService;
import com.javagpt.common.constant.CommonConstants;
import com.javagpt.common.enums.ArticleTypeEnums;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleEntity> implements ArticleService {


    @Override
    public void insert(ArticleEntity articleEntity) {
        articleEntity.setCreateTime(LocalDateTime.now());
        // todo 插入用户，以及封面未加上
        this.getBaseMapper().insert(articleEntity);
    }

    @Override
    public ArticleEntity selectById(Integer id) {
        return this.getBaseMapper().selectById(id);
    }

    @Override
    public Page<ArticleEntity> getByPage(Integer current, Integer size) {

        Page<ArticleEntity> page = new Page<>(current, size);
        QueryWrapper<ArticleEntity> qw = new QueryWrapper<>();
        qw.eq(CommonConstants.DELETE_FLAG, 0);
        qw.eq("type", ArticleTypeEnums.TIME_LINE.getCode());
        qw.orderByDesc("create_time");
        qw.select("article_id",
                "img",
                "href",
                "title",
                "left(content, 10) content",
                "page_view",
                "create_time");
        return this.getBaseMapper().selectPage(page, qw);
    }

    @Override
    public void uploadImg(MultipartFile file) {
        File targetFile = new File("D:/myImg/" + file.getOriginalFilename());

        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}




