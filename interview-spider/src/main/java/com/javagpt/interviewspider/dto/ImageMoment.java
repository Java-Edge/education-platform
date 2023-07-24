package com.javagpt.interviewspider.dto;

import lombok.Data;

/**
 * @author JavaGPT
 * @date 2023/7/24 21:33
 * @description this is a class file created by JavaGPT in 2023/7/24 21:33
 */
@Data
public class ImageMoment {

    /**
     * 图片链接
     */
    private String src;

    /**
     * 图片悬浮名称
     */
    private String alt;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * 高度
     */
    private Integer height;


}
