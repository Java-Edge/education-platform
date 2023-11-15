package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.entity.InterviewSchedule;
import com.javagpt.back.mapper.InterviewScheduleMapper;
import com.javagpt.back.service.InterviewScheduleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.common.constant.Constants;
import com.javagpt.common.enums.ArticleTypeEnums;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author robot
 * @since 2023-11-14
 */
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
        qw.eq(Constants.DELETE_FLAG, 0);
        qw.orderByDesc("create_time");
        qw.select();
        return this.getBaseMapper().selectPage(page, qw);
    }
}
