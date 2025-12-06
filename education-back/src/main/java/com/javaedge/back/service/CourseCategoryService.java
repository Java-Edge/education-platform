package com.javaedge.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javaedge.back.entity.CourseCategory;
import com.javaedge.back.vo.course.CourseCategoryVO;

import java.util.List;

public interface CourseCategoryService extends IService<CourseCategory> {

    List<CourseCategoryVO> mainCategoryList();
}