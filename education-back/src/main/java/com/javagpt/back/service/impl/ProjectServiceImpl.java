package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.entity.Project;
import com.javagpt.back.mapper.ProjectMapper;
import com.javagpt.back.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Override
    public Page<Project> selectPage(Integer current, Integer size, ArticleEntity article) {
        Page<Project> page = new Page<>(current, size);
        QueryWrapper<Project> qw = new QueryWrapper<>();
        qw.eq("delete_flag", 0);
        qw.orderByDesc("create_time");
        qw.select("id", "title", "left(des, 50)", "create_time", "img");
        Page<Project> projectPage = this.getBaseMapper().selectPage(page, qw);
        return projectPage;
    }
}
