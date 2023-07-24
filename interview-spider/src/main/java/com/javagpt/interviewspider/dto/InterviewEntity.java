package com.javagpt.interviewspider.dto;

import lombok.Data;

import java.util.List;

/**
 * @author JavaGPT
 * @date 2023/7/23 0:01
 * @description this is a class file created by bubaiwantong in 2023/7/23 0:01
 */
@Data
public class InterviewEntity {

    private Object blogZhuanlan;
    private Long contentType;
    private ExtraInfo extraInfo;
    private FrequencyData frequencyData;
    private MomentData momentData;
    private ContentData contentData;
    private List<Object> subjectData;
    private UserBrief userBrief;
    private VoteData voteData;


}
