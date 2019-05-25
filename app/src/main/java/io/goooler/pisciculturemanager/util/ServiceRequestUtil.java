package io.goooler.pisciculturemanager.util;

/**
 * RequestService 中需要用到的几个方法封装
 */

public class ServiceRequestUtil {
    private static final int LATEST_ONE = 1;

    public static void getLatestData(RequestUtil.RequestListener requestListener) {
        getLatestData(LATEST_ONE, requestListener);
    }

    public static void getLatestData(int number, RequestUtil.RequestListener requestListener) {
        RequestUtil.getRequest(String.format(RequestUtil.ALL_DATA, number), requestListener);
    }
}
