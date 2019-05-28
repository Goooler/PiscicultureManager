package io.goooler.pisciculturemanager.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.RequiresApi;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.base.BaseApplication;
import io.goooler.pisciculturemanager.model.Constants;
import io.goooler.pisciculturemanager.model.EventType;
import io.goooler.pisciculturemanager.model.OverallDataBean;
import io.goooler.pisciculturemanager.model.RequestDataBean;
import io.goooler.pisciculturemanager.model.WarnningDataBean;
import io.goooler.pisciculturemanager.util.CalculateUtil;
import io.goooler.pisciculturemanager.util.DatabaseUtil;
import io.goooler.pisciculturemanager.util.EventBusUtil;
import io.goooler.pisciculturemanager.util.JsonUtil;
import io.goooler.pisciculturemanager.util.NotificationUtil;
import io.goooler.pisciculturemanager.util.RequestUtil;
import io.goooler.pisciculturemanager.util.ResUtil;
import io.goooler.pisciculturemanager.util.ServiceRequestUtil;
import okhttp3.Response;

/**
 * 请求方法都封装到 service 后台处理，同时处理数据库插入
 */

public class RequestService extends Service {

    private List<OverallDataBean> beans;

    //AlarmManager 系统定时服务，效果好于其它定时任务
    private AlarmManager alarmManager;
    private PendingIntent syncIntent;
    private Handler handler;

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
        handler = new Handler();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        syncIntent = PendingIntent.getService(this, Constants.SERVICE_REQUEST_CODE,
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
        handler.removeCallbacksAndMessages(null);
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
                    filterAndNotify(beans);
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
        syncDatabase(DatabaseUtil.getLatestOverallOne().getId());
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
                System.currentTimeMillis() + Constants.ONE_HOUR_MILLISECONDS, syncIntent);
    }

    private void stopCronSync() {
        alarmManager.cancel(syncIntent);
    }

    /**
     * 过滤返回结果，有超标的情况直接发送通知
     * 这里的数据处理过程放入子线程
     *
     * @param beans 传入接收的所有参数
     */
    private void filterAndNotify(List<OverallDataBean> beans) {
        List<WarnningDataBean> warnningDataBeans = new ArrayList<>();
        String[] paramNames = ResUtil.getStringArray(R.array.overall_data_single);
        String[] unitNames = ResUtil.getStringArray(R.array.overall_unit_single);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (OverallDataBean overallDataBean : beans) {
                    double oxygen = overallDataBean.getOxygen();
                    double temperature = overallDataBean.getTemperature();
                    double ph = overallDataBean.getPh();
                    double nitrogen = overallDataBean.getNitrogen();
                    double nitrite = overallDataBean.getNitrite();
                    if (CalculateUtil.isNotIn(5, 8, oxygen)) {
                        warnningDataBeans.add(new WarnningDataBean(null,
                                overallDataBean.getTimestamp(), paramNames[0],
                                overallDataBean.getOxygen(), unitNames[0]));
                    }
                    if (CalculateUtil.isNotIn(20, 40, temperature)) {
                        warnningDataBeans.add(new WarnningDataBean(null,
                                overallDataBean.getTimestamp(), paramNames[1],
                                overallDataBean.getTemperature(), unitNames[1]));
                    }
                    if (CalculateUtil.isNotIn(6.5, 8, ph)) {
                        warnningDataBeans.add(new WarnningDataBean(null,
                                overallDataBean.getTimestamp(), paramNames[2],
                                overallDataBean.getPh(), unitNames[2]));
                    }
                    if (CalculateUtil.isNotIn(0, 0.015, nitrogen)) {
                        warnningDataBeans.add(new WarnningDataBean(null,
                                overallDataBean.getTimestamp(), paramNames[3],
                                overallDataBean.getNitrogen(), unitNames[3]));
                    }
                    if (CalculateUtil.isNotIn(0, 0.5, nitrite)) {
                        warnningDataBeans.add(new WarnningDataBean(null,
                                overallDataBean.getTimestamp(), paramNames[4],
                                overallDataBean.getNitrite(), unitNames[4]));
                    }
                }
                //size 不为0说明过滤出超标参数，然后切换主线程发送通知
                if (warnningDataBeans.size() != 0) {
                    DatabaseUtil.insert(warnningDataBeans);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String title = ResUtil.getString(R.string.quality_warnning);
                            String content = ResUtil.getString(R.string.some_data_over_standard);
                            if (warnningDataBeans.size() == 1) {
                                WarnningDataBean bean = warnningDataBeans.get(0);
                                content = ResUtil.getString(R.string.current_value) +
                                        bean.getValue() + bean.getUnit();
                            }
                            NotificationUtil.showDefault(RequestService.this, title, content);
                        }
                    });
                }
            }
        }).start();
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
