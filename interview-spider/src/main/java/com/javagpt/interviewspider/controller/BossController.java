package com.javagpt.interviewspider.controller;

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

    @GetMapping("/grabInterviewExperience")
    public void grabInterviewExperience(){
        bossService.grabInterviewExperience();
    }

}
