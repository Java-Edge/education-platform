package com.javaedge.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecruitTypeEnums {

    graduate(0, "校招"),

    inter(1, "实习"),

    graduated(2, "社招"),

    ;

    private final int code;

    private final String name;

}
