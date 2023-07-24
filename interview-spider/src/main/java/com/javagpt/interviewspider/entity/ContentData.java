package com.javagpt.interviewspider.entity;
import lombok.Data;

import java.util.List;

/**
 * @Author bubaiwantong
 * @Date 2023/7/24 17:40
 * @PackageName:com.javagpt.interviewspider.entity.ContentData
 * @ClassName: ContentData
 * @Version 1.0
 */
@Data
public class ContentData {

    private String id;
    private String uuid;
    private String authorId;
    private String title;
    private String newTitle;
    private String richText;
    private String content;
    private String newContent;
    private String typeName;
    private boolean beMyOnly;
    private List<String> contentImageUrls;
    private String isTop;
    private boolean hot;
    private boolean isGilded;
    private boolean isReward;

//    private float reward;
    private boolean hasOfferCompareTag;
    private boolean staffAnswer;
    private boolean withAnonymousOffer;
    private boolean isAnonymousFlag;
    private String isWithAcceptFlag;
    private int postCertify;
    private long entityId;
    private int entityType;
    private String newReferral;
    private long createTime;
    private long editTime;
    private boolean recommendAd;
}
