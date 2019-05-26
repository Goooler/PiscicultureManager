package io.goooler.pisciculturemanager.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.base.BaseApplication;
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
    //定时任务的间隔时长，默认一小时同步一次
    private final int durTime = 3600 * 1000;

    private List<OverallDataBean> beans;

    private AlarmManager alarmManager;
    PendingIntent pendingIntent;

    /**
     * AlarmManager 系统定时服务，效果好于其它定时任务
     * PendingIntent    满足特定条件才执行的任务
     */

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

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        pendingIntent = pendingIntent = PendingIntent.getService(this, 0,
                new Intent(this, RequestService.class), PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startCronSync();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
        stopCronSync();
    }

    /**
     * 请求服务端数据，保持数据库同步
     *
     * @param index 指定需要开始同步的 id
     */
    private void syncDatabase(long index) {
        ServiceRequestUtil.getFromIndex(index, new RequestUtil.RequestListener() {
            @Override
            public void response(Response rawRseponse, String jsonString) {
                List<OverallDataBean> beans = JsonUtil.parse(jsonString,
                        RequestDataBean.class).getBeans();
                if (beans.size() != 0) {
                    //最新一条的数据返回给首页展示总览，
                    EventBusUtil.post(new EventType(EventType.SUCCEED, EventType.SERVICE_TO_OVERALL,
                            beans.get(0)));
                    //所有回传的全部存入数据库，这里是异步操作
                    DatabaseUtil.insert(beans);
                    sendNotification(beans);
                } else {
                    //回传 json 为空说明没有更新
                    EventBusUtil.post(new EventType(EventType.FAILED, EventType.SERVICE_TO_OVERALL, null));
                }
            }
        });
    }

    /**
     * 不指定参数情况下直接从数据库现有的最新值开始同步
     * <p>
     * 目前两个地方调用，service 的定时任务中和首页下拉刷新的请求
     */
    private void syncDatabase() {
        syncDatabase(DatabaseUtil.getLatestOne().getId());
    }

    /**
     * 本地更新数据之后上传到服务器同步
     *
     * @param dataBean 要上传的参数，后面 post 请求发送对象转的 json
     */
    private void updateParams(OverallDataBean dataBean) {
        ServiceRequestUtil.postSync(dataBean, new RequestUtil.RequestListener() {
            @Override
            public void response(Response rawRseponse, String jsonString) {
                if (rawRseponse.isSuccessful()) {
                    BaseApplication.showToast(R.string.data_updated);
                }
            }
        });
    }

    /**
     * 开启 service 同时开启一个定时定时同步数据任务
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startCronSync() {
        syncDatabase();
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + durTime, pendingIntent);
    }

    private void stopCronSync() {
        alarmManager.cancel(pendingIntent);
    }

    /**
     * 过滤返回结果，有超标的情况直接发送通知
     *
     * @param beans 传入接收的所有参数
     */
    private void sendNotification(List<OverallDataBean> beans) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventType eventType) {
        switch (eventType.messageCode) {
            case EventType.OVERALL_TO_SERVICE:
                if (eventType.isSuccessful()) {
                    syncDatabase();
                }
                break;
            default:
                break;
        }
    }
}
