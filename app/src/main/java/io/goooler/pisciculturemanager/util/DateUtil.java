package io.goooler.pisciculturemanager.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换工具简单封装
 */

public class DateUtil {
    public static String DEFAULT_DATE_FORMAT = "yyyy.MM.dd HH:mm";

    public static String timestampToDate(long timestamp) {
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(new Date(timestamp));
    }
}
