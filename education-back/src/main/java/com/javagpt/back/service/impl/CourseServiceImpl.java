package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.dto.CourseDTO;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.back.dto.SpecialQueryDTO;
import com.javagpt.back.entity.CourseEntity;
import com.javagpt.back.entity.Dictionary;
import com.javagpt.back.mapper.CourseMapper;
import com.javagpt.back.mapper.DictionaryMapper;
import com.javagpt.back.service.CourseService;
import com.javagpt.back.vo.course.CourseVO;
import com.javagpt.common.enums.CourseEnum;
import com.javagpt.common.req.PageQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author MSIK
 * @description 针对表【source_course】的数据库操作Service实现
 * @createDate 2023-07-09 13:40:08
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, CourseEntity>
        implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Override
    public List<CourseEntity> getFiveCourse() {

        LambdaQueryWrapper<CourseEntity> wrapper = new LambdaQueryWrapper<CourseEntity>()
                .eq(CourseEntity::getType, CourseEnum.VIDEO.getResultCode())
                .orderByDesc(CourseEntity::getCreateTime)
                .last("limit 5");
        List<CourseEntity> list = this.getBaseMapper().selectList(wrapper);
        return list;
    }

    @Override
    public List<CourseEntity> getRecommendCourses() {
        LambdaQueryWrapper<CourseEntity> wrapper = new LambdaQueryWrapper<CourseEntity>()
                .eq(CourseEntity::getType, CourseEnum.VIDEO.getResultCode())
                .orderByDesc(CourseEntity::getPageView)
                .last("limit 8");
        List<CourseEntity> list = this.getBaseMapper().selectList(wrapper);
        return list;
    }

    @Override
    public List<CourseEntity> selectList() {
        QueryWrapper<CourseEntity> qw = new QueryWrapper<>();
        qw.eq("type", CourseEnum.VIDEO.getResultCode());
        List<CourseEntity> courseEntities = this.getBaseMapper().selectList(qw);
        return courseEntities;
    }

    @Override
    public Map<Integer, CourseEntity> getCoursesMapById(List<Integer> courseIds) {
        List<CourseEntity> courseEntities = this.getBaseMapper().selectBatchIds(courseIds);
        Map<Integer, CourseEntity> result = new HashMap<>();
        for (CourseEntity course : courseEntities) {
            result.put(course.getId(), course);
        }
        return result;
    }

    @Override
    public Page<CourseVO> search(PageQueryParam<CourseDTO> pageQueryParam) {

        Page<CourseVO> page = new Page<>(pageQueryParam.getPageNo(), pageQueryParam.getPageSize());
        Page<CourseVO> courseVOPage = courseMapper.selectByPage(page, pageQueryParam.getParam());
        return courseVOPage;
    }

    @Override
    public Page<CourseVO> getSpecialList(PageQueryParam<SpecialQueryDTO> pageQueryParam) {
        // 1. 定义数据库查询参数
        Map<String, Object> params = new HashMap<>();

        // 2. 构建分页查询参数
        Page<CourseVO> queryPage = new Page<>();
        queryPage.setSize(pageQueryParam.getPageSize());
        queryPage.setCurrent(pageQueryParam.getPageNo());


        // 3. 如果传入专栏类别为父类别，则查询出所有子类别的数据做条件查询
        SpecialQueryDTO dto = pageQueryParam.getParam();
        if (dto.getCategory() != null && dto.getCategory() != 0) {
            List<Integer> categories = Stream.of(dto.getCategory()).collect(Collectors.toList());
            LambdaQueryWrapper<Dictionary> wrapper = new LambdaQueryWrapper<Dictionary>()
//                    .select(Dictionary::getValue)
                    .eq(Dictionary::getTypeKey, "special_category")
                    .eq(dto.getCategory() != null, Dictionary::getParentId, dto.getCategory());
            List<Dictionary> dictionaries = dictionaryMapper.selectList(wrapper);
            if (dictionaries != null && !dictionaries.isEmpty()) {
                List<Integer> childCategories = dictionaries.stream().map(item -> Integer.parseInt(item.getValue())).toList();
                categories.addAll(childCategories);
            }
            dto.setCategories(categories);
        }

        Page<CourseVO> page = courseMapper.queryPage(queryPage, dto);

        return page;
    }
}




