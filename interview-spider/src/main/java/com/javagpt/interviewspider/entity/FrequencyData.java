package com.javagpt.interviewspider.entity;

import lombok.Data;

/**
 * JavaGPT
 */
@Data
public class FrequencyData {

    private Long commentCnt;
    private Long followCnt;
    private Boolean isFollow;
    private Boolean isLike;
    private Long likeCnt;
    private Long shareCnt;
    private Long totalCommentCnt;
    private Long viewCnt;


}