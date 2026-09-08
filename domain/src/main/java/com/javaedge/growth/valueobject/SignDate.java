package com.javaedge.growth.valueobject;

import java.time.LocalDate;

/**
 * 签到日期值对象：年 / 月 / 日。
 *
 * <p>把"自然日相邻"的判定收敛进值对象，避免业务代码里写一堆 {@code day - 1}
 * 之类的脆弱计算。连续签到天数完全靠这个语义方法推导。
 */
public record SignDate(int year, int month, int day) {

    public SignDate {
        if (month < 1 || month > 12 || day < 1 || day > 31) {
            throw new IllegalArgumentException("非法日期: " + year + "-" + month + "-" + day);
        }
    }

    public static SignDate of(int year, int month, int day) {
        return new SignDate(year, month, day);
    }

    public static SignDate today() {
        LocalDate now = LocalDate.now();
        return new SignDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
    }

    /** 判断本日期是否为 {@code other} 的前一天（自然日严格相邻）。 */
    public boolean isExactlyPreviousOf(SignDate other) {
        LocalDate self = LocalDate.of(year, month, day).plusDays(1);
        return self.getYear() == other.year() && self.getMonthValue() == other.month()
                && self.getDayOfMonth() == other.day();
    }

    public LocalDate toLocalDate() {
        return LocalDate.of(year, month, day);
    }
}
