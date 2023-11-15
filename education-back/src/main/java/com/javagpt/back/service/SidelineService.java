package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.entity.Sideline;

public interface SidelineService extends IService<Sideline> {

    /**
     * 分页查询
     */
    Page<Sideline> selectPage(Integer current, Integer size);

    void pv(Integer itemId);
}
