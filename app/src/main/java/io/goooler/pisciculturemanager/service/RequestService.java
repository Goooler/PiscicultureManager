package io.goooler.pisciculturemanager.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.goooler.pisciculturemanager.model.EventType;
import io.goooler.pisciculturemanager.model.OverallDataBean;
import io.goooler.pisciculturemanager.model.RequestDataBean;
import io.goooler.pisciculturemanager.util.EventBusUtil;
import io.goooler.pisciculturemanager.util.JsonUtil;
import io.goooler.pisciculturemanager.util.RequestUtil;
import io.goooler.pisciculturemanager.util.ServiceRequestUtil;
import okhttp3.Response;

public class RequestService extends Service {
    private List<OverallDataBean> beans;

    public RequestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBusUtil.register(this);
        requestAndInsert(10);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }

    /**
     * 往 OverallDataBean 表里写入一组请求得来的数据，
     * 注意如果重复写入同一条 id 的数据会让 GreenDao 报错闪退
     *
     * @param number 请求的数据条数
     */
    private void requestAndInsert(int number) {
        ServiceRequestUtil.getLatestData(number, new RequestUtil.RequestListener() {
            @Override
            public void response(Response rawRseponse, String jsonString) {
                List<OverallDataBean> beans = JsonUtil.parse(jsonString,
                        RequestDataBean.class).getBeans();
                EventBusUtil.post(new EventType(EventType.SUCCEED, EventType.SERVICE_TO_OVERALL,
                        beans.get(0)));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventType eventType) {
        if (eventType.isSuccessful()) {

        }
    }
}
