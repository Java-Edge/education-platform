package com.javagpt.back.controller;

import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.SourceCourseEntity;
import com.javagpt.back.service.SourceCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author bubaiwantong
 * @date 2023/6/27 20:49
 * @description this is a class file created by bubaiwantong in 2023/6/27 20:49
 */
@RestController
@RequestMapping("/sourceCourse")
public class SourceCourseController {

    @Autowired
    private SourceCourseService sourceCourseService;

    @GetMapping("/list")
    public ResultBody list(){
        List<SourceCourseEntity> list = sourceCourseService.list();
        return ResultBody.success(list);
    }


    @GetMapping("/course/{id}")
    public ResultBody courseDetail(@PathVariable("id")Integer id){
        SourceCourseEntity entity = sourceCourseService.getById(id);
        return ResultBody.success(entity);
    }


    @GetMapping("/getFiveCourse")
    public ResultBody getFiveCourse(){
        List<SourceCourseEntity> list = sourceCourseService.getFiveCourse();
        return ResultBody.success(list);
    }

    @GetMapping("/getRecommendCourses")
    public ResultBody getRecommendCourses(){
        List<SourceCourseEntity> list = sourceCourseService.getRecommendCourses();
        return ResultBody.success(list);
    }

}
