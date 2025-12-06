package com.javaedge.back.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class DiscussOfferVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 帖子id
     */
      private String postId;

    /**
     * 帖子标题
     */
    private String postTitle;

    /**
     * 帖子类型名称
     */
    private String postTypeName;

    /**
     * 作者
     */
    private String author;

    /**
     * 作者的头像信息
     */
    private String authorHead;

    /**
     * 作者的唯一标识
     */
    private String authorId;



    /**
     * 认证信息
     */
    private Integer certify;

    /**
     * 评论数量
     */
    private Integer commentCnt;

    /**
     * 帖子内容，默认为空字符串
     */
    private String content;

    /**
     * 图片URL
     */
    private String img;

    /**
     * 是否加精
     */
    private Integer isGilded;

    /**
     * 点赞数量
     */
    private Integer likeCnt;


    /**
     * 帖子标签
     */
    // private String tags;

    /**
     * 标签投票信息
     */
    // private String tagVoteInfos;

    /**
     * 总评论数
     */
    // private Integer totalCommentCount;

    /**
     * 浏览数量
     */
    private Integer viewCnt;

    /**
     * 投票ID
     */
    // private Integer voteId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;


}
