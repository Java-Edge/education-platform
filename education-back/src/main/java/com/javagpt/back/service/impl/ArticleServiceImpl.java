package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.mapper.ArticleMapper;
import com.javagpt.back.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MSIK
 * @description 针对表【source_course】的数据库操作Service实现
 * @createDate 2023-07-09 13:40:08
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleEntity>
        implements ArticleService {

    @Resource
    ArticleMapper articleMapper;

    @Override
    public List<ArticleEntity> getRanking(ArticleEntity article) {
        QueryWrapper<ArticleEntity> qw = new QueryWrapper<>();
        qw.orderByDesc("page_view");
        qw.last("limit 10");
        List<ArticleEntity> articles = articleMapper.selectList(qw);
        if (articles == null) return null;
        for (int i = 0; i < articles.size(); i ++) {
            articles.get(i).setRanking(i+1);
        }
        return articles;
    }
}




