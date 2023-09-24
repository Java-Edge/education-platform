package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zqy
 * @since 2023-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Sideline implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 副业id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 副业标题
     */
    private String title;

    /**
     * 副业简介
     */
    private String des;

    private String img;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private Integer deleteFlag;

    private String href;
}
