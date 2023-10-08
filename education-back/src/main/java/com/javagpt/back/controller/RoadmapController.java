package com.javagpt.back.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.RoadMap;
import com.javagpt.back.entity.RoadMapDetail;
import com.javagpt.back.service.RoadmapService;
import com.javagpt.back.vo.course.CourseRoadmapVO;
import com.javagpt.back.vo.course.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/roadmap")
public class RoadmapController {

    @Autowired
    private RoadmapService roadmapService;

    @GetMapping("/route")
    public ResultBody getRoadmap(@RequestParam Integer categoryId, Integer current, Integer size) {
        Page<RoadMap> roadmap = roadmapService.getRoadmap(categoryId, current, size);
        return ResultBody.success(roadmap);
    }

    @GetMapping("/getRoadMapDetail/{roadMapId}")
    public ResultBody getRoadMapDetail(@PathVariable Integer roadMapId) {
        List<RoadMapDetail> results = roadmapService.getRoadMapDetail(roadMapId);
        return ResultBody.success(results);
    }


}
