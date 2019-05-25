package io.goooler.pisciculturemanager.util;

import android.os.Looper;

import java.io.IOException;
import java.util.Map;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.base.BaseApplication;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp 请求简单封装
 *
 * @constant 是对常量的注释
 */

public class RequestUtil {
    /**
     * @constant DEFAULT_URL    通用的请求地址前缀，工程比较简单只使用了一个服务器，不用考虑切换服务器的问题
     * @constant ALL_DATA   请求所有数据的地址，查询参数是获取最近条数
     * @constant ADD_ONE    向表中添加一条数据的接口，
     */

    public static final String DEFAULT_URL = "http://149.129.123.191/piscicultureManager/mananger/";
    public static final String ALL_DATA = "allData/%s";
    public static final String ADD_ONE = "add";

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
     * @param url      请求地址
     * @param paramMap post 请求参数
     * @param listener 返回结果回调
     */
    public static void postRequest(String url, Map<String, String> paramMap, RequestListener listener) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        for (String key : paramMap.keySet()) {
            bodyBuilder.add(key, paramMap.get(key));
        }
        Request request = new Request.Builder().url(url).post(bodyBuilder.build()).build();
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

    //请求失败直接弹出 toast 提示失败
    private static void showToast() {
        Looper.prepare();
        BaseApplication.showToast(ResUtil.getString(R.string.request_failed));
        Looper.loop();
    }

    //请求成功将返回的源 response 直接回调给发起方
    private static void sendCallback(Response response, RequestListener listener) {
        if (response.isSuccessful()) {
            String jsonString = null;
            try {
                jsonString = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            listener.response(response, jsonString);
        }
    }

    public interface RequestListener<T> {
        void response(Response rawRseponse, String jsonString);
    }
}
