package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.entity.ArticleEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService extends IService<ArticleEntity> {

    /**
     * 插入文章
     *
     * @param articleEntity
     */
    void insert(ArticleEntity articleEntity);

    /**
     * 根据id查询
     */
    ArticleEntity selectById(Integer id);


    Page<ArticleEntity> getByPage(Integer current, Integer size);

    /**
     * 上传图片
     *
     * @param file
     */
    void uploadImg(MultipartFile file);
}
