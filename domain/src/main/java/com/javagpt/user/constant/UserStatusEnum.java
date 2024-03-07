package com.javagpt.user.constant;

import lombok.Getter;

/**
 * @author JavaEdge
 * @date 2023/3/22
 */
@Getter
public enum UserStatusEnum {


    AUDITING(1),

    ENABLED(2),

    DISABLED(3),
    ;

    UserStatusEnum(Integer status) {
        this.status = status;
    }

    private final Integer status;

}
