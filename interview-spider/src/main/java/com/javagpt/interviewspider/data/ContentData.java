package com.javagpt.interviewspider.data;

import lombok.Data;

import java.util.Date;
import java.util.List;
/**
 * @author JavaGPT
 * @date 2023/7/23 0:01
 * @description this is a class file created by JavaGPT in 2023/7/23 0:01
 */
@Data
public class ContentData {

    /**
     * 主键
     */
    private Long id;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 作者主键
     */
    private Long authorId;

    /**
     * beMyOnly
     */
    private Boolean beMyOnly;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 图片集合
     */
    private List<ImageMoment> contentImageUrls;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date editTime;

    /**
     * 实体id
     */
    private Long entityId;

    /**
     * 实体类型
     */
    private Long entityType;

    /**
     *
     */
    private Boolean hasOfferCompareTag;

    /**
     * 是否热点话题
     */
    private Boolean hot;

    /**
     * 匿名标志
     */
    private Boolean isAnonymousFlag;

    /**
     * isGilded
     */
    private Boolean isGilded;

    /**
     * isReward
     */
    private Boolean isReward;

    /**
     * 是否置顶
     */
    private String isTop;

    /**
     * 标志
     */
    private String isWithAcceptFlag;

    /**
     * 新内容
     */
    private String newContent;

    /**
     *
     */
    private String newReferral;

    /**
     * 新标题
     */
    private String newTitle;

    /**
     * 邮箱验证
     */
    private Long postCertify;

    /**
     * 是否广告推荐
     */
    private Boolean recommendAd;

    /**
     * 回报
     */
    private Long reward;

    /**
     * 富文本
     */
    private String richText;

    /**
     * 职业回答
     */
    private Boolean staffAnswer;

    /**
     * 标题
     */
    private String title;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 匿名offer
     */
    private Boolean withAnonymousOffer;

}
