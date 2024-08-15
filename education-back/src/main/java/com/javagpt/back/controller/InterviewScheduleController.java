package com.javagpt.back.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.entity.InterviewSchedule;
import com.javagpt.back.service.InterviewScheduleService;
import com.javagpt.common.resp.ResultBody;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/back/interview-schedule")
public class InterviewScheduleController {

    @Resource
    private InterviewScheduleService interviewScheduleService;

    // 新增一条记录的save 方法
    @PostMapping("/save")
    public ResultBody save(@RequestBody InterviewSchedule interviewSchedule) {
        interviewScheduleService.insert(interviewSchedule);
        return ResultBody.success("保存预约记录成功");
    }

    @GetMapping("/getByPage")
    public ResultBody getByPage(Integer current, Integer size) {
        Page<InterviewSchedule> interviewScheduleServiceByPage = interviewScheduleService.getByPage(current, size);
        return ResultBody.success(interviewScheduleServiceByPage);
    }
}