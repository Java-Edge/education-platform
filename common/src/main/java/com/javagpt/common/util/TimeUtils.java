package com.javagpt.common.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author JavaEdge
 * @date 2023/4/13
 */
public class TimeUtils {

    public static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public static final String reportDateFormat = "yyyy/MM/dd HH:mm:ss";

    public static long localDateTime2Timestamp(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+8")).getEpochSecond() * 1000;
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    }
}
