package io.goooler.pisciculturemanager.util;

import android.os.Looper;

import java.io.IOException;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.base.BaseApplication;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp 请求简单封装
 */

public class RequestUtil {

    public static final String defaultUrl = "http://67.216.214.48/";
    public static final String requestDataTest = "RequestDataTest.json";

    public static void request(String url, RequestListener listener) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().
                url(defaultUrl + url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败直接弹出 toast 提示失败
                Looper.prepare();
                BaseApplication.showToast(ResUtil.getString(R.string.request_failed));
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //请求成功将返回的源 response 直接回调给发起方
                    listener.response(response);
                }
            }
        });
    }

    public interface RequestListener<T> {
        void response(Response rawRseponse);
    }
}
