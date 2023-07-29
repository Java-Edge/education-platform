package com.javagpt.interviewspider.data.nowcoder;

import lombok.Data;

/**
 * @author JavaGPT
 * @date 2023/7/23 0:01
 * @description this is a class file created by JavaGPT in 2023/7/23 0:01
 */
@Data
public class FrequencyData {

    /**
     * 评论数
     */
    private Long commentCnt;

    /**
     * 赞同数
     */
    private Long followCnt;

    /**
     * 是否赞同
     */
    private Boolean isFollow;

    /**
     * 是否喜欢
     */
    private Boolean isLike;

    /**
     * 喜欢总数
     */
    private Long likeCnt;

    /**
     * 分享总数
     */
    private Long shareCnt;

    /**
     * 总评论数
     */
    private Long totalCommentCnt;

    /**
     * 观看人数
     */
    private Long viewCnt;


}