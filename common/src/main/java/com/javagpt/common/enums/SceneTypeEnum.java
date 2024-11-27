package com.javagpt.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 千祎来了
 * @date 2023/10/19 22:35
 */
@Getter
@AllArgsConstructor
public enum SceneTypeEnum {
    TIMELINE_DETAIL_NEED_AUTH_CODE(1, "动态文章详情需要邀请码");

    private int resultCode;

    private String resultMsg;
}
