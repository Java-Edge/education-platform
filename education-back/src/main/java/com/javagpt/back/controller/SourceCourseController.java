package com.javagpt.back.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.SourceCourseEntity;
import com.javagpt.back.service.SourceCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.InputStream;
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
        QueryWrapper<SourceCourseEntity> qw = new QueryWrapper<>();
        qw.eq("type", 0);
        List<SourceCourseEntity> list = sourceCourseService.list(qw);

        return ResultBody.success(list);
    }

    @GetMapping("listSpecialList")
    public ResultBody listSpecialList() {
        QueryWrapper<SourceCourseEntity> qw = new QueryWrapper<>();
        // 获取专栏内容
        qw.eq("type", 1);
        List<SourceCourseEntity> list = sourceCourseService.list(qw);
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
