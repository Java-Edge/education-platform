package com.javagpt.interviewspider.service;

/**
 *
 * @Author JavaGPT
 * @Date 2023/8/1 14:47
 * @PackageName:com.javagpt.interviewspider.service
 * @ClassName: NowCoderRecruitService
 * @Version 1.0
 */
public interface NowCoderRecruitService {


    /**
     * 爬取岗位信息
     */
    void grabRecruits();

    /**
     * 获取所有岗位信息
     */
    void grabAllInfo();

    /**
     * 获取最新的岗位信息，用于定时器
     */
    void grabLastInfo();

}
