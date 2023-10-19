package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.PageQueryParam;
import com.javagpt.back.dto.RecruitDTO;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.Dictionary;
import com.javagpt.back.entity.Recruit;
import com.javagpt.back.mapper.DictionaryMapper;
import com.javagpt.back.mapper.RecruitMapper;
import com.javagpt.back.service.RecruitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.util.U;
import com.javagpt.back.vo.RecruitVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Page<Recruit> selectPage(Integer current, Integer size, HttpServletRequest request) {
        Integer userId = U.getCurrentUserId(request);
        Page<Recruit> page = new Page<>(current, size);
        // 如果用户未登录，直接返回空数据
        if (userId == null) {
            return page;
        }
        QueryWrapper<Recruit> qw = new QueryWrapper<>();
        qw.orderByDesc("create_time");
        Page<Recruit> recruitPage = this.baseMapper.selectPage(page, qw);
        return recruitPage;
    }

    @Override
    public Page<RecruitVO> selectByCondition(PageQueryParam<RecruitDTO> pageQueryParam, HttpServletRequest request) {
        Page<Recruit> page = new Page<>();
        page.setCurrent(pageQueryParam.getPageNo());
        page.setSize(pageQueryParam.getPageSize());

        Integer userId = U.getCurrentUserId(request);
        // 如果用户未登录，直接返回空数据
        if (userId == null) {
            return new Page<RecruitVO>();
        }

        RecruitDTO recruitDTO = pageQueryParam.getParam();

        // 1. 查询筛选框的薪资范围
        if (recruitDTO.getSalaryRange() != null && !recruitDTO.getSalaryRange().isEmpty()) {
            List<Dictionary> salaryRangeList = dictionaryMapper.selectList(new LambdaQueryWrapper<Dictionary>()
                    .eq(Dictionary::getTypeKey, "salary_range")
                    .in(Dictionary::getValue, recruitDTO.getSalaryRange()));

            if (salaryRangeList != null && !salaryRangeList.isEmpty()) {
                List<Integer> salaryList = new ArrayList<>();
                for (Dictionary salaryRange : salaryRangeList) {
                    String[] salaries = salaryRange.getLabel().split("-");
                    if (salaries.length > 1) {
                        salaryList.add(Integer.valueOf(salaries[0]));
                        salaryList.add(Integer.valueOf(salaries[1].replace("K", "")));
                    }
                }
                recruitDTO.setSalaryMin(salaryList.stream().min((a, b) -> a - b).get());
                recruitDTO.setSalaryMax(salaryList.stream().max((a, b) -> a - b).get());
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
