package com.javagpt.interviewspider.entity.service;

import com.javagpt.interviewspider.data.boss.BossContentInfo;

import java.util.List;

/**
 * @author JavaGPT
 * @date 2023/7/29 23:24
 * @description this is a class file created by JavaGPT in 2023/7/29 23:24
 */
public interface BossService {

    /**
     * 抓取Boss上的面试经验
     */
    List<BossContentInfo> grabInterviewExperience();

}
