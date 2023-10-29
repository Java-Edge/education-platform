package com.javagpt.back.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mkingg
 * @since 2023-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DiscussOffer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 帖子id
     */
      private String postId;

    /**
     * 帖子子类型
     */
    private String postSubType;

    /**
     * 帖子标题
     */
    private String postTitle;

    /**
     * 帖子类型
     */
    private String postType;

    /**
     * 帖子类型名称
     */
    private String postTypeName;

    /**
     * 作者
     */
    private String author;

    /**
     * 作者的教育信息
     */
    private String authorEducationInfo;

    /**
     * 作者的头像信息
     */
    private String authorHead;

    /**
     * 作者的荣誉等级
     */
    private String authorHonorLevel;

    /**
     * 作者的唯一标识
     */
    private String authorId;

    /**
     * 与作者相关的学科信息
     */
    private String authorSubjects;

    /**
     * 认证信息
     */
    private Integer certify;

    /**
     * 评论数量
     */
    private Integer commentCnt;

    /**
     * 是否禁止评论
     */
    private Integer commentDisable;

    /**
     * 评论用户
     */
    private String commentUsers;

    /**
     * 帖子内容，默认为空字符串
     */
    private String content;

    /**
     * 第一张图片的URL
     */
    private String firstImgStr;

    /**
     * 总关注数
     */
    private Integer followTotal;

    /**
     * 热度指标
     */
    private Integer hot;

    /**
     * 头像装饰URL
     */
    private String headDecorateUrl;

    /**
     * 图片URL
     */
    private String img;

    /**
     * 身份信息
     */
    private String identity;

    /**
     * 内容是否完整
     */
    private Integer isFullContent;

    /**
     * 是否加精
     */
    private Integer isGilded;

    /**
     * 是否打赏
     */
    private Integer isReward;

    /**
     * 是否感谢图片
     */
    private Integer isThankImage;

    /**
     * 投票是否过期
     */
    private Integer isVoteExpired;

    /**
     * 是否包含接受标志
     */
    private Integer isWithAcceptFlag;

    /**
     * 最后一页
     */
    private Integer lastPage;

    /**
     * 点赞数量
     */
    private Integer likeCnt;

    /**
     * 新推荐指标
     */
    private Integer newReferral;

    /**
     * NC跟踪ID
     */
    private String ncTraceId;

    /**
     * 最近显示时间
     */
    private String recentDisplayTime;

    /**
     * 奖励信息
     */
    private String reward;

    /**
     * 帖子标签
     */
    private String tags;

    /**
     * 标签投票信息
     */
    private String tagVoteInfos;

    /**
     * 总评论数
     */
    private Integer totalCommentCount;

    /**
     * 浏览数量
     */
    private Integer viewCnt;

    /**
     * 投票ID
     */
    private Integer voteId;

    /**
     * 是否匿名报价
     */
    private Integer withAnonymousOffer;

    /**
     * 是否有积分
     */
    private Integer withPoint;

    /**
     * 是否有投票
     */
    private Integer withVote;

    /**
     * 精彩评论数量
     */
    private Integer wonderfulCommentCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;


}
