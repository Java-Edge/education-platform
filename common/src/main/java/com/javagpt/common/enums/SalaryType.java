package com.javagpt.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 薪资范围枚举
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum SalaryType {

    /**
     * 正式薪资范围，包含校招、社招
     */
    FORMAL_SALARY_RANGE(0, "salary_range"),

    /**
     * 校招薪资范围
     */
    SCHOOL_SALARY_RANGE(1, "salary_range"),

    /**
     * 实习薪资范围
     */
    INTERNSHIP_SALARY_RANGE(2, "internship_salary_range"),

    /**
     * 社招薪资范围
     */
    SOCIAL_SALARY_RANGE(3, "salary_range");

    private Integer code;

    private String value;


    public static SalaryType getByCode(Integer code) {
        for (SalaryType salaryType : SalaryType.values()) {
            if (salaryType.getCode().equals(code)) {
                return salaryType;
            }
        }
        return FORMAL_SALARY_RANGE;
    }

}
