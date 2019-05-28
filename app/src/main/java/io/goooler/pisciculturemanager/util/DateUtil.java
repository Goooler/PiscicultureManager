package io.goooler.pisciculturemanager.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换工具简单封装
 */

public class DateUtil {
    public static String DEFAULT_DATE_FORMAT = "yyyy.MM.dd HH:mm";

    /**
     * 时间戳转日期字符串，这里用了默认的格式
     *
     * @param timestamp 注意10位的时间戳要补足成13位
     * @return 字符串格式日期
     */
    public static String timestampToDate(Long timestamp) {
        if (timestamp.toString().length() == 10) {
            timestamp = timestamp * 1000;
        }
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(new Date(timestamp));
    }
}
