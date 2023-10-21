package com.javagpt.back.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.entity.Project;
import com.javagpt.back.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zqy
 * @since 2023-08-22
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/getByPage")
    public ResultBody getByPage(Integer current, Integer size, ArticleEntity article) {
        Page<Project> page = projectService.selectPage(current,size, article);
        return ResultBody.success(page);
    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable Integer id) {
        Project project = projectService.getById(id);
        return ResultBody.success(project);
    }
}
