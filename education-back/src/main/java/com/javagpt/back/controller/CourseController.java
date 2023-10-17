package com.javagpt.back.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.CourseDTO;
import com.javagpt.back.dto.PageQueryParam;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.CourseEntity;
import com.javagpt.back.service.CourseService;
import com.javagpt.back.vo.course.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
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

    @PostMapping("search")
    public ResultBody search(@RequestBody PageQueryParam<CourseDTO> pageQueryParam){
        Page<CourseVO> page = courseService.search(pageQueryParam);
        return ResultBody.success(page);
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
