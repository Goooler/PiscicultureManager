package io.goooler.pisciculturemanager.util;

import android.util.Log;

/**
 * Log 工具的简单封装，可自由控制全局 log 输出
 */

public class LogUtil {
    private static boolean showLog = true;
    private static final String DEFAULT_LOG_TAG = "defaultLogTag";

    public static void d(String debugInfo) {
        log(DEFAULT_LOG_TAG, debugInfo);
    }

    public static void d(String tag, String debugInfo) {
        log(tag, debugInfo);
    }

    private static void log(String tag, String debugInfo) {
        if (showLog) {
            Log.d(tag, debugInfo);
        }
    }
}
