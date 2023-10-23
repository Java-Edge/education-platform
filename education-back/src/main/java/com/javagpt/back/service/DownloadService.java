package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.entity.ArticleEntity;

public interface DownloadService extends IService<ArticleEntity> {

    Page<ArticleEntity> listByPage(Integer current, Integer size);
}
