package com.javagpt.interviewspider.dto;

import lombok.Data;

/**
 * @author JavaGPT
 * @date 2023/7/23 0:01
 * @description this is a class file created by bubaiwantong in 2023/7/23 0:01
 */
@Data
public class VoteData {

    /**
     * 投票id
     */
    private Long voteId;

    /**
     * 投票标题
     */
    private Object voteTitle;

    /**
     * 投票类型
     */
    private Object voteType;

    /**
     * 投票
     */
    private Boolean withVote;


}