package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.entity.InterviewSchedule;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author robot
 * @since 2023-11-14
 */
public interface InterviewScheduleService extends IService<InterviewSchedule> {

    void insert(InterviewSchedule interviewSchedule);

    Page<InterviewSchedule> getByPage(Integer current, Integer size);
}
