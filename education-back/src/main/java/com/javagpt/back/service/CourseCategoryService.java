package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.entity.CourseCategory;
import com.javagpt.back.vo.course.CourseCategoryVO;

import java.util.List;

public interface CourseCategoryService extends IService<CourseCategory> {

    List<CourseCategoryVO> mainCategoryList();
}