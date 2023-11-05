package com.javagpt.back.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SidelineVO {

    /**
     * 副业id
     */
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

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private String href;

    private Integer pageView;
}
