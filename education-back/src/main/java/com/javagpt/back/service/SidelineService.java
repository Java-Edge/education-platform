package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.entity.Project;
import com.javagpt.back.entity.Sideline;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zqy
 * @since 2023-08-22
 */
public interface SidelineService extends IService<Sideline> {

    /**
     * 分页查询
     *
     * @param current
     * @param size
     * @return
     */
    Page<Sideline> selectPage(Integer current, Integer size);

}
