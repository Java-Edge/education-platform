package com.javaedge.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 千祎来了
 * @date 2023/10/21 23:30
 */
@Getter
@AllArgsConstructor
public enum CourseEnum {

    VIDEO(0, "视频"),
    SPECIAL(1, "专栏");

    private final int resultCode;

    private final String resultMsg;

}
