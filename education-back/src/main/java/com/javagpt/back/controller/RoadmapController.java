package com.javagpt.back.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.service.RoadmapService;
import com.javagpt.back.vo.course.CourseRoadmapVO;
import com.javagpt.back.vo.course.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/roadmap")
public class RoadmapController {

    @Autowired
    private RoadmapService roadmapService;

    @GetMapping("/route")
    public ResultBody getRoadmap(@RequestParam Integer categoryId, Integer current, Integer size) {
        Page<CourseRoadmapVO> page = roadmapService.getRoadmap(categoryId, current, size);
        return ResultBody.success(page);
    }

    @GetMapping("/route/items")
    public ResultBody getRouteItems(@RequestParam Integer parentId, Integer current, Integer size){
        Page<CourseVO> page = roadmapService.getRouteItems(parentId, current, size);
        return ResultBody.success(page);
    }
}
