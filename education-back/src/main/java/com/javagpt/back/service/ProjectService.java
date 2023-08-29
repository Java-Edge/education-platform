package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zqy
 * @since 2023-08-22
 */
public interface ProjectService extends IService<Project> {

    /**
     * 分页查询
     *
     * @param current
     * @param size
     * @param article
     * @return
     */
    Page<Project> selectPage(Integer current, Integer size, ArticleEntity article);

}
