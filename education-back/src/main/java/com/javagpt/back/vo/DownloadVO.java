package com.javagpt.back.vo;

import lombok.Data;

/**
 * 资源下载页面展示对象
 */
@Data
public class DownloadVO {

    private Integer articleId;
    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    private Integer pageView;
}
