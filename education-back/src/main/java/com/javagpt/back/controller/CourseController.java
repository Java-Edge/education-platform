package com.javagpt.back.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.CourseEntity;
import com.javagpt.back.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sourceCourse")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/list")
    public ResultBody list(){
        QueryWrapper<CourseEntity> qw = new QueryWrapper<>();
        qw.eq("type", 0);
        List<CourseEntity> list = courseService.list(qw);
        return ResultBody.success(list);
    }

    @GetMapping("listSpecialList")
    public ResultBody listSpecialList() {
        QueryWrapper<CourseEntity> qw = new QueryWrapper<>();
        // 获取专栏内容
        qw.eq("type", 1);
        List<CourseEntity> list = courseService.list(qw);
        return ResultBody.success(list);
    }

    @GetMapping("/course/{id}")
    public ResultBody getById(@PathVariable("id")Integer id){
        CourseEntity entity = courseService.getById(id);
        return ResultBody.success(entity);
    }


    @GetMapping("/getFiveCourse")
    public ResultBody getFiveCourse(){
        List<CourseEntity> list = courseService.getFiveCourse();
        return ResultBody.success(list);
    }

    @GetMapping("/getRecommendCourses")
    public ResultBody getRecommendCourses(){
        List<CourseEntity> list = courseService.getRecommendCourses();
        return ResultBody.success(list);
    }

}
