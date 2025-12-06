package com.javaedge.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 千祎来了
 * @date 2023/10/19 22:35
 */
@Getter
@AllArgsConstructor
public enum PermissionConfigTypeEnum {
    NEED_LOGIN(1, "需要登录");

    private int resultCode;

    private String resultMsg;
}
