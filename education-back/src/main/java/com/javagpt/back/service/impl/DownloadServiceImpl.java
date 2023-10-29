package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.mapper.ArticleMapper;
import com.javagpt.back.service.DownloadService;
import com.javagpt.common.constant.Constants;
import com.javagpt.common.enums.ArticleTypeEnums;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class DownloadServiceImpl extends ServiceImpl<ArticleMapper, ArticleEntity> implements DownloadService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Page<ArticleEntity> listByPage(Integer current, Integer size) {
        Page<ArticleEntity> page = new Page<>(current, size);
        QueryWrapper<ArticleEntity> qw = new QueryWrapper<>();
        qw.eq(Constants.DELETE_FLAG, 0);
        qw.eq("type", ArticleTypeEnums.DOWNLOAD.getCode());
        qw.orderByDesc("create_time");
        qw.select("article_id", "img", "href", "title", "left(content, 50) content", "page_view");
        return this.getBaseMapper().selectPage(page, qw);
    }

    @Override
    public ArticleEntity selectById(Integer id) {
        return this.getBaseMapper().selectById(id);
    }
}




