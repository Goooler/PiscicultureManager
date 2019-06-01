package io.goooler.pisciculturemanager.util;

import android.os.Looper;

import java.io.IOException;

import io.goooler.pisciculturemanager.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp 请求简单封装
 *
 * @constant 是对常量的注释
 */

public class RequestUtil {
    /**
     * DEFAULT_URL    通用的请求地址前缀，工程比较简单只使用了一个服务器，不用考虑切换服务器的问题
     * ALL_DATA   请求所有数据的地址，查询参数是获取最近条数
     * SOME_DATA   请求部分数据的地址，查询参数是获取从指定 id 之后的所有条目
     * ADD_ONE    向表中添加一条数据的接口，
     */

    private static final String DEFAULT_URL = "http://120.77.41.191:4000/piscicultureManager/";
    private static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";
    static final String ALL_DATA = "allData/%s";
    static final String SOME_DATA = "some/%s";
    static final String ADD_ONE = "add";

    /**
     * 普通 get 异步请求
     *
     * @param url      请求地址
     * @param listener 返回结果回调
     */
    public static void getRequest(String url, RequestListener listener) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(DEFAULT_URL + url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showToast();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendCallback(response, listener);
            }
        });
    }

    /**
     * 普通 post 异步请求
     *
     * @param url        请求地址
     * @param jsonString body 是 json 的方式
     * @param listener   返回结果回调
     */
    public static void postRequest(String url, String jsonString, RequestListener listener) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse(CONTENT_TYPE_JSON), jsonString);
        Request request = new Request.Builder().url(DEFAULT_URL + url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showToast();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendCallback(response, listener);
            }
        });
    }

    /**
     * 请求失败直接弹出 toast 提示失败
     */
    private static void showToast() {
        Looper.prepare();
        ToastUtil.showToast(R.string.request_failed);
        Looper.loop();
    }

    /**
     * 请求成功将返回的源 response 直接回调给发起方
     */
    private static void sendCallback(Response response, RequestListener listener) {
        if (response.isSuccessful()) {
            String jsonString = null;
            try {
                jsonString = response.body().string();
            } catch (IOException e) {
            }
            listener.response(response, jsonString);
        }
    }

    public interface RequestListener {
        /**
         * 自定义要回调给发起放大的数据类型
         *
         * @param rawRseponse 原始的 response
         * @param jsonString  body string
         */
        void response(Response rawRseponse, String jsonString);
    }
}
