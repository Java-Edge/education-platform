package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.entity.CourseCategory;
import com.javagpt.back.mapper.CourseCategoryMapper;
import com.javagpt.back.service.CourseCategoryService;
import com.javagpt.back.vo.course.CourseCategoryVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory> implements CourseCategoryService {
    @Override
    public List<CourseCategoryVO> mianCategoryList() {
        QueryWrapper<CourseCategory> qw = new QueryWrapper<>();
//        qw.eq("parent_id", -1);
        qw.eq("status",1);
        qw.select("id","name", "category");
        List<CourseCategory> courseCategories = this.getBaseMapper().selectList(qw);
        return courseCategories.stream()
                .map(category -> CourseCategoryVO.builder().id(category.getId()).name(category.getName()).category(category.getCategory()).build())
                .toList();
    }
}
