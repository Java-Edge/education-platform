package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.entity.Roadmap;


public interface RoadmapService extends IService<Roadmap> {
    Page<Roadmap> selectPage(Integer current, Integer size);
}
