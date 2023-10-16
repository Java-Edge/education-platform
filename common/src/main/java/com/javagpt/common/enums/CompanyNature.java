package com.javagpt.common.enums;

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
public enum CompanyNature {

    SUCCESS(4712, "私企"),
    BODY_NOT_MATCH(2830, "国企"),
    SIGNATURE_NOT_MATCH(2834, "外企"),
    NOT_FOUND(4713, "事业单位"),
    INTERNAL_SERVER_ERROR(500, "公务员"),
    ;

    private int code;

    private String name;
}
