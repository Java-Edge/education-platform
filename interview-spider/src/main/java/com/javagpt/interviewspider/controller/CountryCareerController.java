package com.javagpt.interviewspider.controller;

import com.javagpt.interviewspider.service.CountryCareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/countryCareer")
public class CountryCareerController {


    @Autowired
    private CountryCareerService countryCareerService;

    @GetMapping("/grabCareer")
    public void grabAllCareer(){
        countryCareerService.grabCareer();
    }



}
