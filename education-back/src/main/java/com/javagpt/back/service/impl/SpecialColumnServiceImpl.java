package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.dto.SpecialQueryDTO;
import com.javagpt.back.entity.CourseEntity;
import com.javagpt.back.entity.Dictionary;
import com.javagpt.back.mapper.CourseMapper;
import com.javagpt.back.mapper.DictMapper;
import com.javagpt.back.service.SpecialColumnService;
import com.javagpt.back.vo.course.CourseVO;
import com.javagpt.common.enums.CourseEnum;
import com.javagpt.common.enums.DictTypeEnums;
import com.javagpt.common.req.PageQueryParam;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SpecialColumnServiceImpl extends ServiceImpl<CourseMapper, CourseEntity> implements SpecialColumnService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private DictMapper dictMapper;

    @Override
    public List<CourseEntity> selectList() {
        QueryWrapper<CourseEntity> qw = new QueryWrapper<>();
        qw.eq("type", CourseEnum.VIDEO.getResultCode());
        return this.getBaseMapper().selectList(qw);
    }


    @Override
    public Page<CourseVO> pageListSpecial(PageQueryParam<SpecialQueryDTO> pageQueryParam) {
        Page<CourseVO> courseVOPage = new Page<>();
        courseVOPage.setSize(pageQueryParam.getPageSize());
        courseVOPage.setCurrent(pageQueryParam.getPageNo());

        // 如果传入专栏类别为父类别，则查询出所有子类别的数据做条件查询
        SpecialQueryDTO specialQueryDTO = pageQueryParam.getParam();
        if (Objects.nonNull(specialQueryDTO.getCategory()) && specialQueryDTO.getCategory() != 0) {
            List<Integer> categories = Stream.of(specialQueryDTO.getCategory()).collect(Collectors.toList());
            LambdaQueryWrapper<Dictionary> dictQW = new LambdaQueryWrapper<Dictionary>()
                    .eq(Dictionary::getTypeKey, DictTypeEnums.special_category.getCode())
                    .eq(specialQueryDTO.getCategory() != null, Dictionary::getParentId,
                            specialQueryDTO.getCategory());

            List<Dictionary> dicts = dictMapper.selectList(dictQW);
            if (CollectionUtils.isNotEmpty(dicts)) {
                List<Integer> childCategories = dicts.stream().map(item -> Integer.parseInt(item.getValue())).toList();
                categories.addAll(childCategories);
            }
            specialQueryDTO.setCategories(categories);
        }

        return courseMapper.queryPage(courseVOPage, specialQueryDTO);
    }
}




