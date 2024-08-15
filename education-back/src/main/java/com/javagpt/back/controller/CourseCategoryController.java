package com.javagpt.back.controller;

import com.javagpt.common.resp.ResultBody;
import com.javagpt.back.service.CourseCategoryService;
import com.javagpt.back.vo.course.CourseCategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courseCategory")
public class CourseCategoryController {

    @Autowired
    private CourseCategoryService courseCategoryService;

    @GetMapping("/mainCategoryList")
    public ResultBody list(){
        List<CourseCategoryVO> list = courseCategoryService.mainCategoryList();
        return ResultBody.success(list);
    }
}
