package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.entity.Sideline;
import com.javagpt.back.mapper.SidelineMapper;
import com.javagpt.back.service.SidelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SidelineServiceImpl extends ServiceImpl<SidelineMapper, Sideline> implements SidelineService {

    @Autowired
    private SidelineMapper sidelineMapper;


    @Override
    public Page<Sideline> selectPage(Integer current, Integer size) {
        Page<Sideline> page = new Page<>(current, size);
        QueryWrapper<Sideline> qw = new QueryWrapper<>();
        qw.eq("delete_flag", 0);
        qw.orderByDesc("create_time");
        qw.select("id", "title", "left(des, 50) des", "href", "img", "create_time", "page_view");
        Page<Sideline> sidelinePage = this.getBaseMapper().selectPage(page, qw);
        return sidelinePage;
    }

    @Override
    public void pv(Integer itemId) {
        Sideline sideline = sidelineMapper.selectById(itemId);
        sideline.setId(itemId);
        sideline.setPageView(sideline.getPageView() + 1);
        sidelineMapper.updateById(sideline);
    }
}
