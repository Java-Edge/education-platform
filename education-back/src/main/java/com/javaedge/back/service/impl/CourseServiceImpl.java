package com.javaedge.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaedge.back.dto.CourseQueryDTO;
import com.javaedge.back.entity.CoursePO;
import com.javaedge.back.entity.Dictionary;
import com.javaedge.back.mapper.CourseMapper;
import com.javaedge.back.mapper.DictMapper;
import com.javaedge.back.service.CourseService;
import com.javaedge.back.vo.course.CourseVO;
import com.javaedge.common.enums.CourseEnum;
import com.javaedge.common.enums.DictTypeEnums;
import com.javaedge.common.req.PageQueryParam;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, CoursePO> implements CourseService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private DictMapper dictMapper;

    @Override
    public List<CoursePO> selectList() {
        QueryWrapper<CoursePO> qw = new QueryWrapper<>();
        qw.eq("type", CourseEnum.VIDEO.getResultCode());
        return this.getBaseMapper().selectList(qw);
    }


    @Override
    public Page<CourseVO> pageQuery(PageQueryParam<CourseQueryDTO> pageQueryParam) {
        Page<CourseVO> courseVOPage = new Page<>();
        courseVOPage.setSize(pageQueryParam.getPageSize());
        courseVOPage.setCurrent(pageQueryParam.getPageNo());

        // 如果传入专栏类别为父类别，则查询出所有子类别的数据做条件查询
        CourseQueryDTO courseQueryDTO = pageQueryParam.getParam();
        if (Objects.nonNull(courseQueryDTO.getCategory()) && courseQueryDTO.getCategory() != 0) {
            List<Integer> categories = Stream.of(courseQueryDTO.getCategory()).collect(Collectors.toList());
            LambdaQueryWrapper<Dictionary> dictQW = new LambdaQueryWrapper<Dictionary>()
                    .eq(Dictionary::getTypeKey, DictTypeEnums.special_category.getCode())
                    .eq(courseQueryDTO.getCategory() != null, Dictionary::getParentId,
                            courseQueryDTO.getCategory());

            List<Dictionary> dicts = dictMapper.selectList(dictQW);
            if (CollectionUtils.isNotEmpty(dicts)) {
                List<Integer> childCategories = dicts.stream().map(item -> Integer.parseInt(item.getValue())).toList();
                categories.addAll(childCategories);
            }
            courseQueryDTO.setCategories(categories);
        }

        return courseMapper.queryPage(courseVOPage, courseQueryDTO);
    }

    @Override
    public void pv(Integer itemId) {
        CoursePO coursePO = courseMapper.selectById(itemId);
        coursePO.setId(itemId);
        coursePO.setPageView(coursePO.getPageView() + 1);
        courseMapper.updateById(coursePO);
    }
}




