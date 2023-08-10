package com.javagpt.interviewspider.controller;

import com.javagpt.interviewspider.service.NowCoderPostService;
import com.javagpt.interviewspider.service.NowCoderRecruitService;
import com.javagpt.interviewspider.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JavaGPT
 * @date 2023/7/23 0:01
 * @description this is a class file created by JavaGPT in 2023/7/23 0:01
 */
@RestController
@RequestMapping("/nowcoder")
public class NowCoderController {


    @Autowired
    private SpiderService spiderService;

    @Autowired
    private NowCoderPostService nowCoderPostService;

    @Autowired
    private NowCoderRecruitService nowCoderRecruitService;

    @GetMapping("/obtainInterviewExperience")
    public void obtainInterviewExperience(){
        spiderService.obtainInterviewExperience();
    }


    @GetMapping("/grabPositions")
    private void grabPositions(){
        nowCoderPostService.grabPositions();
    }

    @GetMapping("/grabRecruits")
    private void grabRecruits(){
        nowCoderRecruitService.grabRecruits();
    }

}