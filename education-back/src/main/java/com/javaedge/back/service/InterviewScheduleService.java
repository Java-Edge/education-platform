package com.javaedge.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaedge.back.entity.InterviewSchedule;

public interface InterviewScheduleService extends IService<InterviewSchedule> {

    void insert(InterviewSchedule interviewSchedule);

    Page<InterviewSchedule> getByPage(Integer current, Integer size);
}