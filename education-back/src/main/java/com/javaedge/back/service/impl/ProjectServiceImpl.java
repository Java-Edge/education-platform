package com.javaedge.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaedge.back.entity.ArticleEntity;
import com.javaedge.back.entity.ProjectEntity;
import com.javaedge.back.mapper.ProjectMapper;
import com.javaedge.back.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import static com.javaedge.common.constant.CommonConstants.DELETE_FLAG;

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
