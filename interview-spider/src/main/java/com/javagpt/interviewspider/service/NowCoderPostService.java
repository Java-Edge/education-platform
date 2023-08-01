package com.javagpt.interviewspider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.interviewspider.entity.JobEntity;

/**
 *
 * @Author JavaGPT
 * @Date 2023/8/1 13:18
 * @PackageName:com.javagpt.interviewspider.service
 * @ClassName: NowCoderPostService
 * @Version 1.0
 */
public interface NowCoderPostService extends IService<JobEntity> {

    /**
     * 抓取职位信息
     */
    void grabPositions();

}
