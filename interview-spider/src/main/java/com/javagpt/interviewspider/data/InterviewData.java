package com.javagpt.interviewspider.data;

import lombok.Data;

import java.util.List;

/**
 * @author JavaGPT
 * @date 2023/7/23 0:01
 * @description this is a class file created by JavaGPT in 2023/7/23 0:01
 */
@Data
public class InterviewData {

    /**
     * 博客专栏
     */
    private Object blogZhuanlan;

    /**
     * 面试经验的类型，目前有74和250
     */
    private Long contentType;

    /**
     * 额外信息
     */
    private ExtraInfo extraInfo;

    /**
     *
     */
    private FrequencyData frequencyData;

    /**
     * contentType为74才会有信息
     */
    private MomentData momentData;


    /**
     * contentType为250才会有信息
     */
    private ContentData contentData;

    /**
     * 子数据，目前怀疑是评论列表的数据
     */
    private List<Object> subjectData;

    /**
     * 用户信息
     */
    private UserBrief userBrief;

    /**
     * 投票数据
     */
    private VoteData voteData;


}
