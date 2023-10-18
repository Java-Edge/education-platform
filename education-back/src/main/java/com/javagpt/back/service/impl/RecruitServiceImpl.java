package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.PageQueryParam;
import com.javagpt.back.dto.RecruitDTO;
import com.javagpt.back.entity.Dictionary;
import com.javagpt.back.entity.Recruit;
import com.javagpt.back.mapper.DictionaryMapper;
import com.javagpt.back.mapper.RecruitMapper;
import com.javagpt.back.service.RecruitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.vo.RecruitVO;
import com.javagpt.common.enums.SalaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zqy
 * @since 2023-07-23
 */
@Service
public class RecruitServiceImpl extends ServiceImpl<RecruitMapper, Recruit> implements RecruitService {


    @Autowired
    private RecruitMapper recruitMapper;

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Override
    public Page<Recruit> selectPage(Integer current, Integer size) {
        Page<Recruit> page = new Page<>(current, size);
        QueryWrapper<Recruit> qw = new QueryWrapper<>();
        qw.orderByDesc("create_time");
        Page<Recruit> recruitPage = this.baseMapper.selectPage(page, qw);
        return recruitPage;
    }

    @Override
    public Page<RecruitVO> selectByCondition(PageQueryParam<RecruitDTO> pageQueryParam) {
        Page<Recruit> page = new Page<>();
        page.setCurrent(pageQueryParam.getPageNo());
        page.setSize(pageQueryParam.getPageSize());

        RecruitDTO recruitDTO = pageQueryParam.getParam();

        // 1. 查询筛选框的薪资范围
        if (recruitDTO.getSalaryRange() != null && !recruitDTO.getSalaryRange().isEmpty()) {
            SalaryType salaryType = SalaryType.getByCode(recruitDTO.getSalaryType());
            List<Dictionary> salaryRangeList = dictionaryMapper.selectList(new LambdaQueryWrapper<Dictionary>()
//                    .select(Dictionary::getMinValue, Dictionary::getMaxValue)
                    .eq(Dictionary::getTypeKey, salaryType.getValue())
                    .in(Dictionary::getValue, recruitDTO.getSalaryRange()));

            if (salaryRangeList != null && !salaryRangeList.isEmpty()) {

                Integer salaryMin = salaryRangeList.stream().map(Dictionary::getMinValue).toList()
                        .stream().min(Comparator.comparing(x -> x)).orElse(null);

                Integer salaryMax = salaryRangeList.stream().map(Dictionary::getMaxValue).toList()
                        .stream().max(Comparator.comparing(x -> x)).orElse(null);

                recruitDTO.setSalaryMin(salaryMin);
                recruitDTO.setSalaryMax(salaryMax);
            }
        }


        // 2. 条件查询
        Page<RecruitVO> recruitVOPage = recruitMapper.queryPage(page, recruitDTO);

        return recruitVOPage;
    }

    @Override
    public RecruitVO queryById(String id) {
        RecruitVO recruitVO = recruitMapper.queryById(id);
        return recruitVO;
    }
}
