package com.javaedge.common.util;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author JavaEdge
 * @date 2023/5/19
 */
public class DateUtils {

    /**
     * 将LocalDateTime 转为 Date
     *
     * @param localDateTime
     * @return
     */
    public static Date of(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return new Date(LocalDateTimeUtil.toEpochMilli(localDateTime));
    }



}
