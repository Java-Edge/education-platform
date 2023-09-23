package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.entity.Project;
import com.javagpt.back.entity.Sideline;
import com.javagpt.back.mapper.ProjectMapper;
import com.javagpt.back.mapper.SidelineMapper;
import com.javagpt.back.service.ProjectService;
import com.javagpt.back.service.SidelineService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zqy
 * @since 2023-08-22
 */
@Service
public class SidelineServiceImpl extends ServiceImpl<SidelineMapper, Sideline> implements SidelineService {

    @Override
    public Page<Sideline> selectPage(Integer current, Integer size) {
        Page<Sideline> page = new Page<>(current, size);
        QueryWrapper<Sideline> qw = new QueryWrapper<>();
        qw.eq("delete_flag", 0);
        qw.orderByDesc("create_time");
        qw.select("id", "title", "des", "href", "img", "create_time");
        Page<Sideline> sidelinePage = this.getBaseMapper().selectPage(page, qw);
        return sidelinePage;
    }
}
