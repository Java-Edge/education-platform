package com.javagpt.interviewspider.entity;

import lombok.Data;

import java.util.List;

/**
 * JavaGPT
 */
@Data
public class InterviewEntity {

    private Object blogZhuanlan;
    private Long contentType;
    private ExtraInfo extraInfo;
    private FrequencyData frequencyData;
    private MomentData momentData;
    private List<Object> subjectData;
    private UserBrief userBrief;
    private VoteData voteData;


}