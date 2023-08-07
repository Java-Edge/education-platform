package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value ="article")
@Data
public class ArticleEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer articleId;

    private String title;

    private String content;

    private Integer type;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private Integer likeCnt;

    private Integer collectCnt;

    private Integer deleteFlag;

    private Integer img;

    private Integer userId;

    private Integer pageView;

    @TableField(exist = false)
    private Integer ranking;

}
