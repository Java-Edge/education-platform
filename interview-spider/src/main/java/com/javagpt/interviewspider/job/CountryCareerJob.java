package com.javagpt.interviewspider.job;

import com.javagpt.interviewspider.service.CountryCareerService;
import com.javagpt.interviewspider.service.NowCoderRecruitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author JavaGPT
 */
@Slf4j
@Component
public class CountryCareerJob {


    @Autowired
    private CountryCareerService countryCareerService;


    /**
     * 定时获取牛客网的招聘信息
     */
    @Scheduled(cron = "0 */20 * * * ?")
    public void recruitsJob() {
        countryCareerService.grabAllCareer();
    }

}