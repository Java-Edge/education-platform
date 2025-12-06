package com.javaedge.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公司性质
 *
 * @author JavaEdge
 * @date 2023/3/20
 */
@Getter
@AllArgsConstructor
public enum CompanyNatureEnums {

    PRIVATE(4712, "私企"),
    COUNTRY(2830, "国企"),
    FOREIGNER(2834, "外企"),
    // 实际是私企
    ENTERPRIZE(4713, "事业单位"),
    INTERNAL(500, "公务员"),
    ;

    private int code;

    private String name;
}
