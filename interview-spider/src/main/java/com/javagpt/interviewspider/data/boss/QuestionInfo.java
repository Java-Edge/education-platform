package com.javagpt.interviewspider.data.boss;

import lombok.Data;

import java.util.List;

/**
 * JavaGPT
 */
@Data
public class QuestionInfo {

    /**
     * 添加时间
     */
    private Object addTime;

    /**
     *
     */
    private Long answerCommentUv;

    /**
     * 回复统计
     */
    private Long answerCount;

    /**
     * 回复链接
     */
    private String answerLinkUrl;
    private List<Object> answerList;
    private Long auditLimitHours;
    private Long auditStatus;
    private Long brandId;
    private String content;
    private Long contentType;
    private List<Object> creativeAvatarList;
    private Long creativeScore;
    private Long creativeUserCount;
    private String expectIdV1;
    private String expectIdV2;
    private String expectIdV3;
    private String expectNameV1;
    private String expectNameV2;
    private String expectNameV3;
    private Long expectType;
    private Long exposePv;
    private Long exposeUv;
    private Object exposuredJobInfo;
    private Long focusCount;
    private Long grayExpose;
    private Long hasCurrPostAnswer;
    private Long interestCount;
    private Long isMarkdown;
    private Long isPeerQuestion;
    private Long isSelf;
    private Object lastPublishTime;
    private String lid;
    private String linkUrl;
    private Long methodFlag;
    private String originalFormId;
    private List<Object> picList;
    private Long postCheckStatus;
    private String postCheckUrl;
    private String questionDesc;
    private String questionId;
    private Long showAnswerGuide;
    private Object special;
    private List<Object> specialList;
    private Object titleHighlight;
    private Long viewCount;
    private Object vote;
    private Object voteV2;
}
