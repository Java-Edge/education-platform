package com.javagpt.back.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.entity.Project;
import com.javagpt.back.entity.Sideline;
import com.javagpt.back.service.ProjectService;
import com.javagpt.back.service.SidelineService;
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
@RequestMapping("/sideline")
public class SidelineController {

    @Autowired
    private SidelineService sidelineService;

    @GetMapping("/getByPage")
    public ResultBody getByPage(Integer current, Integer size) {
        Page<Sideline> page = sidelineService.selectPage(current,size);
        return ResultBody.success(page);
    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable Integer id) {
        Sideline sideline = sidelineService.getById(id);
        return ResultBody.success(sideline);
    }
}