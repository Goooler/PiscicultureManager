package io.goooler.pisciculturemanager.util;

import io.goooler.pisciculturemanager.model.OverallDataBean;

/**
 * RequestService 中需要用到的几个方法封装
 */

public class ServiceRequestUtil {
    private static final int LATEST_ONE = 1;

    /**
     * 只拿最近一次的
     */
    public static void getLatestData(RequestUtil.RequestListener requestListener) {
        getLatestData(LATEST_ONE, requestListener);
    }

    /**
     * 获取最近 n 条数据
     *
     * @param number          最近数据条数
     * @param requestListener 接口回调
     */
    public static void getLatestData(int number, RequestUtil.RequestListener requestListener) {
        RequestUtil.getRequest(String.format(RequestUtil.ALL_DATA, number), requestListener);
    }

    /**
     * 获取从指定 id 之后的所有数据，方便服务端和本地同步数据库
     *
     * @param id              指定的 id，
     * @param requestListener 接口回调
     */
    public static void getFromIndex(long id, RequestUtil.RequestListener requestListener) {
        RequestUtil.getRequest(String.format(RequestUtil.SOME_DATA, id + 1), requestListener);
    }

    /**
     * 本地数据修改之后同步到服务端
     *
     * @param dataBean        要上传的参数，后面 post 请求发送对象转的 json
     * @param requestListener 接口回调
     */
    public static void postSync(OverallDataBean dataBean, RequestUtil.RequestListener requestListener) {
        RequestUtil.postRequest(RequestUtil.ADD_ONE, dataBean.toJsonString(), requestListener);
    }
}
