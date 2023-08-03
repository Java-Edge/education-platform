package com.javagpt.interviewspider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author bubaiwantong
 * @date 2023/7/23 0:01
 * @description this is a class file created by bubaiwantong in 2023/7/23 0:01
 */

@SpringBootApplication(scanBasePackages = "com.javagpt.interviewspider")
public class InterviewSpiderApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewSpiderApplication.class, args);
    }

}
