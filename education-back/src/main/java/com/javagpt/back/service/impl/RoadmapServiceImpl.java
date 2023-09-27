package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.entity.Roadmap;
import com.javagpt.back.mapper.RoadmapMapper;
import com.javagpt.back.service.RoadmapService;
import org.springframework.stereotype.Service;

@Service
public class RoadmapServiceImpl extends ServiceImpl<RoadmapMapper, Roadmap> implements RoadmapService {

    @Override
    public Page<Roadmap> selectPage(Integer current, Integer size) {
        Page<Roadmap> page = new Page<>(current, size);
        QueryWrapper<Roadmap> qw = new QueryWrapper<>();
        qw.select("id", "title", "left(des, 50)","target","highlight","lecturer", "href");
        return this.getBaseMapper().selectPage(page, qw);
    }
}
