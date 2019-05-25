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
import io.goooler.pisciculturemanager.util.DatabaseUtil;
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
        syncDatabase(DatabaseUtil.getLatestOne().getId());
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
     * @param index 指定需要开始同步的 id
     */
    private void syncDatabase(long index) {
        ServiceRequestUtil.getFromIndex(index, new RequestUtil.RequestListener() {
            @Override
            public void response(Response rawRseponse, String jsonString) {
                List<OverallDataBean> beans = JsonUtil.parse(jsonString,
                        RequestDataBean.class).getBeans();
                if (beans.size() != 0) {
                    EventBusUtil.post(new EventType(EventType.SUCCEED, EventType.SERVICE_TO_OVERALL,
                            beans.get(0)));
                    DatabaseUtil.insert(beans);
                } else {
                    EventBusUtil.post(new EventType(EventType.FAILED, EventType.SERVICE_TO_OVERALL, null));
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventType eventType) {
        switch (eventType.messageCode) {
            case EventType.OVERALL_TO_SERVICE:
                if (eventType.isSuccessful()) {
                    syncDatabase(DatabaseUtil.getLatestOne().getId());
                }
            default:
                break;
        }
    }
}
