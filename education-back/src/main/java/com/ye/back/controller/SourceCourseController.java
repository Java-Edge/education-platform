package com.ye.back.controller;

import com.ye.back.dto.ResultBody;
import com.ye.back.entity.SourceCourseEntity;
import com.ye.back.service.SourceCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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


}
