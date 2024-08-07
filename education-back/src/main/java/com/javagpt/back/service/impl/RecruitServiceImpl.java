package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.entity.RecruitPO;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.back.dto.RecruitQO;
import com.javagpt.back.entity.Dictionary;
import com.javagpt.back.mapper.DictMapper;
import com.javagpt.back.mapper.RecruitMapper;
import com.javagpt.back.service.RecruitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.util.UserContextHolder;
import com.javagpt.back.vo.RecruitEntity;
import com.javagpt.common.enums.SalaryType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RecruitServiceImpl extends ServiceImpl<RecruitMapper, RecruitPO> implements RecruitService {


    @Autowired
    private RecruitMapper recruitMapper;

    @Autowired
    private DictMapper dictionaryMapper;

    @Override
    public Page<RecruitPO> selectPage(Integer current, Integer size, HttpServletRequest request) {
        Integer userId = UserContextHolder.getCurrentUserId(request);
        Page<RecruitPO> page = new Page<>(current, size);
        // 如果用户未登录，直接返回空数据
        if (userId == null) {
            return page;
        }
        QueryWrapper<RecruitPO> qw = new QueryWrapper<>();
        qw.orderByDesc("create_time");
        Page<RecruitPO> recruitPage = this.baseMapper.selectPage(page, qw);
        return recruitPage;
    }

    @Override
    public Page<RecruitEntity> selectByCondition(PageQueryParam<RecruitQO> pageQueryParam, HttpServletRequest request) {
        Page<RecruitPO> page = new Page<>();
        page.setCurrent(pageQueryParam.getPageNo());
        page.setSize(pageQueryParam.getPageSize());

        Integer userId = UserContextHolder.getCurrentUserId(request);
        // 如果用户未登录，直接返回空数据
        if (userId == null) {
            return new Page<>();
        }

        RecruitQO recruitQO = pageQueryParam.getParam();

        // 1. 查询筛选框的薪资范围
        if (recruitQO.getSalaryRange() != null && !recruitQO.getSalaryRange().isEmpty()) {
            SalaryType salaryType = SalaryType.getByCode(recruitQO.getSalaryType());
            List<Dictionary> salaryRangeList = dictionaryMapper.selectList(new LambdaQueryWrapper<Dictionary>()
//                    .select(Dictionary::getMinValue, Dictionary::getMaxValue)
                    .eq(Dictionary::getTypeKey, salaryType.getValue())
                    .in(Dictionary::getValue, recruitQO.getSalaryRange()));

            if (salaryRangeList != null && !salaryRangeList.isEmpty()) {

                Integer salaryMin = salaryRangeList.stream().map(Dictionary::getMinValue).toList()
                        .stream().min(Comparator.comparing(x -> x)).orElse(null);

                Integer salaryMax = salaryRangeList.stream().map(Dictionary::getMaxValue).toList()
                        .stream().max(Comparator.comparing(x -> x)).orElse(null);

                recruitQO.setSalaryMin(salaryMin);
                recruitQO.setSalaryMax(salaryMax);
            }
        }


        // 2. 条件查询
        Page<RecruitEntity> recruitVOPage = recruitMapper.queryPage(page, recruitQO);

        return recruitVOPage;
    }

    @Override
    public RecruitEntity queryById(String id) {
        RecruitEntity recruitVO = recruitMapper.queryById(id);
        return recruitVO;
    }

    @Override
    public List<RecruitPO> getHotRecruits() {
        List<RecruitPO> recruits = recruitMapper.selectList(new LambdaQueryWrapper<RecruitPO>()
                .orderByDesc(RecruitPO::getPageView)
                .last("limit 6"));
        return recruits;
    }
}