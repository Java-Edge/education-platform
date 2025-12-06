package com.javaedge.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DictTypeEnums {

    /**
     * 专栏
     */
    special_category("special_category"),
    ;

    private final String code;
}
