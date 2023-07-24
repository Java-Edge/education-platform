package com.javagpt.interviewspider.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author bubaiwantong
 * @Date 2023/7/24 17:40
 * @PackageName:com.javagpt.interviewspider.InterviewEntity
 * @ClassName: InterviewEntity
 * @Version 1.0
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
