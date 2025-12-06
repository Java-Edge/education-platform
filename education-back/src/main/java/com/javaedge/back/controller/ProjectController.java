package com.javaedge.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.javaedge.back.converter.ProjectConverter;
import com.javaedge.back.entity.ArticleEntity;
import com.javaedge.back.entity.ProjectEntity;
import com.javaedge.back.service.ProjectService;
import com.javaedge.back.vo.ProjectVO;
import com.javaedge.common.resp.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 项目页面入口
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/getByPage")
    public ResultBody getByPage(Integer current, Integer size, ArticleEntity article) {

        Page<ProjectEntity> projectEntityPage = projectService.selectPage(current,size, article);

        List<ProjectVO> articleVOS = Lists.newArrayList();
        for (ProjectEntity projectEntity: projectEntityPage.getRecords()) {
            articleVOS.add(ProjectConverter.INSTANCE.toVO(projectEntity));
        }
        IPage<ProjectVO> projectVOPage = new Page<>();
        projectVOPage.setRecords(articleVOS);
        projectVOPage.setTotal(projectEntityPage.getTotal());
        return ResultBody.success(projectVOPage);

    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable Integer id) {
        ProjectEntity project = projectService.getById(id);
        return ResultBody.success(project);
    }
}