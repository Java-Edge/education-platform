package com.javagpt.back.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.back.entity.RoadMap;
import com.javagpt.back.entity.RoadMapDetail;
import com.javagpt.back.service.RoadmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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

    @GetMapping("/getAll")
    public ResultBody getAll() {
        Map<Integer, List<RoadMap>> roadmap = roadmapService.getAll();
        return ResultBody.success(roadmap);
    }

    @GetMapping("/getRoadMapById/{roadMapId}")
    public ResultBody getRoadMapById(@PathVariable Integer roadMapId) {
        RoadMap byId = roadmapService.getById(roadMapId);
        return ResultBody.success(byId);
    }

    @GetMapping("/getRoadMapDetail/{roadMapId}")
    public ResultBody getRoadMapDetail(@PathVariable Integer roadMapId) {
        List<RoadMapDetail> results = roadmapService.getRoadMapDetail(roadMapId);
        return ResultBody.success(results);
    }

    @GetMapping("/getRecommentRoad")
    public ResultBody getRecommentRoad() {
        List<RoadMap> res = roadmapService.getRecommendRoad();
        return ResultBody.success(res);
    }


}
