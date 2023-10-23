package com.javagpt.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文章用途种类划分
 */
@Getter
@AllArgsConstructor
public enum ArticleTypeEnums {

    NEWS(0, "动态"),

    DOWNLOAD(1, "下载");

    private final int code;

    private final String name;

}
