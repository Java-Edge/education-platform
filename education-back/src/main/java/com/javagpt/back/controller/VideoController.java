package com.javagpt.back.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.CourseDTO;
import com.javagpt.back.entity.CourseEntity;
import com.javagpt.back.service.VideoService;
import com.javagpt.back.vo.course.CourseVO;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.common.resp.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/courseList")
    public ResultBody courseList(){
        QueryWrapper<CourseEntity> qw = new QueryWrapper<>();
        List<CourseEntity> list = videoService.selectList();
        return ResultBody.success(list);
    }

    @PostMapping("/search")
    public ResultBody search(@RequestBody PageQueryParam<CourseDTO> pageQueryParam){
        Page<CourseVO> page = videoService.search(pageQueryParam);
        return ResultBody.success(page);
    }

    @GetMapping("/course/{id}")
    public ResultBody getById(@PathVariable("id")Integer id){
        CourseEntity entity = videoService.getById(id);
        return ResultBody.success(entity);
    }


    @GetMapping("/getFiveCourse")
    public ResultBody getFiveCourse(){
        List<CourseEntity> list = videoService.getFiveCourse();
        return ResultBody.success(list);
    }

    @GetMapping("/getRecommendCourses")
    public ResultBody getRecommendCourses(){
        List<CourseEntity> list = videoService.getRecommendCourses();
        return ResultBody.success(list);
    }

}
