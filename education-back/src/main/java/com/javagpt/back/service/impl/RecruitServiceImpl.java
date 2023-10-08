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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Dictionary salaryRange = dictionaryMapper.selectOne(new LambdaQueryWrapper<Dictionary>()
                .eq(Dictionary::getTypeKey, "salary_range")
                .eq(Dictionary::getValue, recruitDTO.getSalaryRange()));

        if (salaryRange != null) {
            String[] salaries = salaryRange.getLabel().split("-");
            if (salaries.length > 1) {
                recruitDTO.setSalaryMin(Integer.valueOf(salaries[0]));
                recruitDTO.setSalaryMax(Integer.valueOf(salaries[1].replace("K", "")));
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
