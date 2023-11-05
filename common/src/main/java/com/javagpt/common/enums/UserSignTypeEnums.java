package com.javagpt.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 千祎来了
 * @date 2023/10/29 21:23
 */
@Getter
@AllArgsConstructor
public enum UserSignTypeEnums {
    SIGN_NORMAL_INTEGRAL(1, "正常签到所得积分"),
    SIGN_CONTINOUS_INTEGRAL(2, "连续签到所得积分");

    private int type;

    private String msg;

}
