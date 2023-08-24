package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.entity.ArticleEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author MSIK
* @description 针对表【source_course】的数据库操作Service
* @createDate 2023-07-09 13:40:08
*/
public interface ArticleService extends IService<ArticleEntity> {

    /**
     * 插入文章
     *
     * @param articleEntity
     */
    void insert(ArticleEntity articleEntity);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    ArticleEntity selectById(Integer id);

    /**
     *
     * 分页查询
     *
     * @param current
     * @param size
     * @param article
     */
    Page<ArticleEntity> getByPage(Integer current, Integer size, ArticleEntity article);

    /**
     * 上传图片
     *
     * @param file
     */
    void uploadImg(MultipartFile file);
}
