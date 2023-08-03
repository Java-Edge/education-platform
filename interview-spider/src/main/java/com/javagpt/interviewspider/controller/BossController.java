package com.javagpt.interviewspider.controller;

import com.javagpt.interviewspider.service.BossCityService;
import com.javagpt.interviewspider.service.BossQuestionService;
import com.javagpt.interviewspider.service.BossRecruitService;
import com.javagpt.interviewspider.service.BossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JavaGPT
 * @date 2023/7/29 22:24
 * @description this is a class file created by JavaGPT in 2023/7/29 22:24
 */
@RestController
@RequestMapping("/boss")
public class BossController {

    @Autowired
    private BossService bossService;

    @Autowired
    private BossQuestionService bossQuestionService;

    @Autowired
    private BossCityService bossCityService;

    @Autowired
    private BossRecruitService bossRecruitService;

    @GetMapping("/grabInterviewExperience")
    public void grabInterviewExperience() {
        bossService.grabInterviewExperience();
    }


    @GetMapping("/grabQuestions")
    public void grabQuestions() {
        bossQuestionService.grabQuestions();
    }

    @GetMapping("/grabCities")
    public void grabCities() {
        bossCityService.grabCities();
    }

    @GetMapping("/grabPositions")
    public void grabPositions() {
        bossRecruitService.grabInfo();
    }


}
