package com.javagpt.back.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.Roadmap;
import com.javagpt.back.service.RoadmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/roadmap")
public class RoadmapController {

    @Autowired
    private RoadmapService roadmapService;

    @GetMapping("/getByPage")
    public ResultBody getByPage(Integer current, Integer size) {
        Page<Roadmap> page = roadmapService.selectPage(current,size);
        return ResultBody.success(page);
    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable Integer id) {
        Roadmap roadmap = roadmapService.getById(id);
        return ResultBody.success(roadmap);
    }
}
