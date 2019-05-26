package io.goooler.pisciculturemanager.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.goooler.pisciculturemanager.model.OverallDataBean;

/**
 * 数据生成工具，供服务端生成水质的几个参数，定时调用。
 * 主要目的是提供给统计折线图展示
 * 服务端生成数据，客户端请求生成的数据
 * 或者本地用代码生成，Sqlite 存取
 */

public class GenerateDataUtil {

    public static OverallDataBean generate() {
        //时间戳精度要求不高
        long timestamp = System.currentTimeMillis() / 1000;
        int hour = timeToHour(timestamp);
        return new OverallDataBean(timestamp, oxygenValue(hour), temperatureValue(hour),
                phValue(hour), nitrogenValue(hour), nitriteValue(hour));
    }

    //氧气含量根据时间变化的数据生成
    private static double oxygenValue(int hour) {
        double nextValue = 0;
        if (hour >= 2 && hour <= 11) {
            nextValue = 9 - (hour - 2) * 0.5 - Math.random() * 0.5;
        } else if (hour > 11 && hour <= 16) {
            nextValue = 4 + 2 * Math.random();
        } else if (hour > 16 && hour <= 23) {
            nextValue = 4 + (hour - 16) * 0.5 + Math.random() * 0.5;
        } else if (hour >= 0 && hour < 2) {
            nextValue = 8 + hour * 0.5 + Math.random() * 0.5;
        }
        return nextValue;
    }

    //温度根据时间变化的数据生成
    private static double temperatureValue(int hour) {
        double nextValue = 0;
        if (hour >= 5 && hour <= 14) {
            nextValue = 20 + (hour - 5) * 2 + Math.random() * 2;
        } else if (hour > 14 && hour <= 23) {
            nextValue = 40 - hour - Math.random() * 1;
        } else if (hour >= 0 && hour <= 5) {
            nextValue = 30 - hour * 2 - Math.random() * 2;
        }
        return nextValue;
    }

    //酸碱度根据时间变化的数据生成
    private static double phValue(int hour) {
        double nextValue = 0;
        if (hour >= 0 && hour <= 12) {
            nextValue = 6 + Math.random() * 2;
        } else if (hour > 12 && hour <= 23) {
            nextValue = 9 - Math.random() * 2;
        }
        return nextValue;
    }

    //氨氮含量根据时间变化的数据生成
    private static double nitrogenValue(int hour) {
        double nextValue = 0;
        if (hour >= 0 && hour <= 23) {
            nextValue = Math.random() * 0.018;
        }
        return nextValue;
    }

    //亚硝酸盐含量根据时间变化的数据生成
    private static double nitriteValue(int hour) {
        double nextValue = 0;
        if (hour >= 0 && hour <= 23) {
            nextValue = Math.random() * 0.55;
        }
        return nextValue;
    }

    //将时间戳换算成时间，仅取用时单位
    private static int timeToHour(long timestamp) {
        String hourString = new SimpleDateFormat("HH").format(new Date(timestamp));
        return Integer.valueOf(hourString);
    }
}
