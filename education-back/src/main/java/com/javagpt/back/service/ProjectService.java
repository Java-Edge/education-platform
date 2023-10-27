package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.entity.ProjectEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ProjectService extends IService<ProjectEntity> {

    /**
     * 分页查询
     */
    Page<ProjectEntity> selectPage(Integer current, Integer size, ArticleEntity article);

}
