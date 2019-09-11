package com.etekcity.cloud.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具
 *
 * @author Levi
 * @date 2019/7/26/026
 * @since 0.0.1
 */
public class DateUtil {

    /**
     * 时间字符串
     */
    private static final String TIME_PATTERN_STRING = "yyyyMMddHHmmssSSS";

    private static final String TIME_PATTERN_UTC = "yyyy-MM-dd'T'HH-mm-ssZ";

    public static String nowTimeString() {
        return new SimpleDateFormat(TIME_PATTERN_STRING).format(System.currentTimeMillis());
    }


    public static Date getTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String timeString(Date date) {
        return new SimpleDateFormat(TIME_PATTERN_STRING).format(date);
    }

    /**
     * 获取指定日期偏移指定时间后的时间
     *
     * @param date          基准日期
     * @param calendarField 偏移的粒度大小（小时、天、月等）使用Calendar中的常数
     * @param offset        偏移量，正数为向后偏移，负数为向前偏移
     * @return 偏移后的日期
     */
    public static Date offsetDate(Date date, int calendarField, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendarField, offset);
        return cal.getTime();
    }
}
