package com.javagpt.back.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.Recruit;
import com.javagpt.back.service.RecruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zqy
 * @since 2023-07-23
 */
@RestController
@RequestMapping("/recruit")
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    @GetMapping("/getByPage")
    public ResultBody getByPage(Integer current, Integer size) {
        Page<Recruit> page = new Page<>(current, size);
        QueryWrapper<Recruit> qw = new QueryWrapper<>();
        qw.orderByDesc("create_time");
        recruitService.page(page, qw);
        return ResultBody.success(page);
    }

    @GetMapping("/list")
    public ResultBody list() {
        List<Recruit> list = recruitService.list();
        return ResultBody.success(list);
    }
    @PostMapping("/save")
    public ResultBody save(@RequestBody Recruit recruit) {
        recruit.setCreateTime(LocalDateTime.now());
        recruitService.save(recruit);
        return ResultBody.success("保存成功");
    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable Integer id) {
        Recruit byId = recruitService.getById(id);
        return ResultBody.success(byId);
    }

}

