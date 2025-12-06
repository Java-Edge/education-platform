package com.javaedge.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaedge.back.entity.InterviewSchedule;
import com.javaedge.back.mapper.InterviewScheduleMapper;
import com.javaedge.back.service.InterviewScheduleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaedge.common.constant.CommonConstants;
import org.springframework.stereotype.Service;

@Service
public class InterviewScheduleServiceImpl extends ServiceImpl<InterviewScheduleMapper, InterviewSchedule> implements InterviewScheduleService {

    @Override
    public void insert(InterviewSchedule interviewSchedule) {
        this.getBaseMapper().insert(interviewSchedule);
    }

    @Override
    public Page<InterviewSchedule> getByPage(Integer current, Integer size) {
        Page<InterviewSchedule> page = new Page<>(current, size);
        QueryWrapper<InterviewSchedule> qw = new QueryWrapper<>();
        qw.eq(CommonConstants.DELETE_FLAG, 0);
        qw.orderByDesc("create_time");
        qw.select();
        return this.getBaseMapper().selectPage(page, qw);
    }
}