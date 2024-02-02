package com.javagpt.user.constant;

import lombok.Getter;

/**
 * @author JavaEdge
 * @date 2023/3/22
 */
@Getter
public enum UserTypeEnum {


    ENTERPRISE(1), NORMAL(2);

    UserTypeEnum(Integer type) {
        this.type = type;
    }

    private final Integer type;

}
