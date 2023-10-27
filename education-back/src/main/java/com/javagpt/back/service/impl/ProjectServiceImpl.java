package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.entity.ProjectEntity;
import com.javagpt.back.mapper.ProjectMapper;
import com.javagpt.back.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import static com.javagpt.common.constant.Constants.DELETE_FLAG;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, ProjectEntity> implements ProjectService {

    @Override
    public Page<ProjectEntity> selectPage(Integer current, Integer size, ArticleEntity article) {
        Page<ProjectEntity> page = new Page<>(current, size);
        QueryWrapper<ProjectEntity> qw = new QueryWrapper<>();
        qw.eq(DELETE_FLAG, 0);
        qw.orderByDesc("create_time");
        qw.select("id", "title", "des", "create_time", "img","page_view");
        Page<ProjectEntity> projectPage = this.getBaseMapper().selectPage(page, qw);
        return projectPage;
    }
}
