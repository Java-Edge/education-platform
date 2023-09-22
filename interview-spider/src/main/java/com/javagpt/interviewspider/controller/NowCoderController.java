package com.javagpt.interviewspider.controller;

import com.javagpt.interviewspider.service.NowCodeInnerRecommendService;
import com.javagpt.interviewspider.service.NowCoderRecruitService;
import com.javagpt.interviewspider.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JavaGPT
 * @date 2023/7/23 0:01
 * @description 牛客网爬虫接口写在此文件中
 */
@RestController
@RequestMapping("/nowcoder")
public class NowCoderController {


    @Autowired
    private SpiderService spiderService;


    @Autowired
    private NowCoderRecruitService nowCoderRecruitService;

    @Autowired
    private NowCodeInnerRecommendService innerRecommendService;

    @GetMapping("/obtainInterviewExperience")
    public void obtainInterviewExperience(){
        spiderService.obtainInterviewExperience();
    }


    @GetMapping("/grabRecruits")
    public void grabRecruits(){
        nowCoderRecruitService.grabRecruits();
    }

    @GetMapping("/grabInnerRecommend")
    public void grabInnerRecommend(){
        innerRecommendService.grabInnerRecommend();
    }


}
