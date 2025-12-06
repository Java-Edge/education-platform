package com.javaedge.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaedge.back.entity.ArticleEntity;
import com.javaedge.back.entity.ProjectEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ProjectService extends IService<ProjectEntity> {

    /**
     * 分页查询
     */
    Page<ProjectEntity> selectPage(Integer current, Integer size, ArticleEntity article);

}
