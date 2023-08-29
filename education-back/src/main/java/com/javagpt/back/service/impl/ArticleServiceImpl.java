package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.mapper.ArticleMapper;
import com.javagpt.back.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MSIK
 * @description 针对表【source_course】的数据库操作Service实现
 * @createDate 2023-07-09 13:40:08
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleEntity>
        implements ArticleService {


    @Override
    public void insert(ArticleEntity articleEntity) {
        articleEntity.setCreateTime(LocalDateTime.now());
        // todo 插入用户，以及封面未加上
        this.getBaseMapper().insert(articleEntity);
    }

    @Override
    public ArticleEntity selectById(Integer id) {
        ArticleEntity articleEntity = this.getBaseMapper().selectById(id);
        return articleEntity;
    }

    @Override
    public Page<ArticleEntity> getByPage(Integer current, Integer size, ArticleEntity article) {

        Page<ArticleEntity> page = new Page<>(current, size);
        QueryWrapper<ArticleEntity> qw = new QueryWrapper<>();
        qw.eq("delete_flag", 0);
        qw.eq(article.getType() != null, "type", article.getType());
        qw.orderByDesc("page_view");
        qw.orderByDesc("create_time");

        Page<ArticleEntity> articleEntityPage = this.getBaseMapper().selectPage(page, qw);
        return articleEntityPage;
    }

    @Override
    public void uploadImg(MultipartFile file) {
        File targetFile = new File("D:/myImg/" + file.getOriginalFilename());

        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(file);
    }
}




