package io.goooler.pisciculturemanager.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.IOException;
import java.util.List;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.model.OverallDataBean;
import io.goooler.pisciculturemanager.model.RequestDataBean;
import io.goooler.pisciculturemanager.util.DatabaseUtil;
import io.goooler.pisciculturemanager.util.JsonUtil;
import io.goooler.pisciculturemanager.util.LogUtil;
import io.goooler.pisciculturemanager.util.RequestUtil;
import okhttp3.Response;

public class RequestService extends Service {
    private List<OverallDataBean> beans;

    public RequestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("binded");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("started");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("destroyed");
    }

    /**
     * 往 OverallDataBean 表里写入一组请求得来的数据，
     * 注意如果重复写入同一条 id 的数据会让 GreenDao 报错闪退
     */
    private void requestAndInsert() {
        RequestUtil.request(getResources().getString(R.string.requestDataTest), new RequestUtil.RequestListener() {
            @Override
            public void response(Response rawRseponse) {
                try {
                    beans = JsonUtil.parse(rawRseponse.body().string(), RequestDataBean.class).getBeans();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DatabaseUtil.insert(beans);
            }
        });
    }
}
