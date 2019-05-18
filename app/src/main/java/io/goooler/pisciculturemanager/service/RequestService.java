package io.goooler.pisciculturemanager.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.base.BaseApplication;
import io.goooler.pisciculturemanager.model.Constants;
import io.goooler.pisciculturemanager.model.OverallDataBean;

public class RequestService extends Service {
    public RequestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("666", "binded");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("666", "created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("666", "started");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("666", "destroyed");
    }

    /**
     * 往 OverallDataBean 表里写入一组测试数据, 注意这里 json 内指定了 id 为 0，
     * 如果重复写入同一条 id 的数据会让 GreenDao 报错闪退
     */
    private void createTestOverallData() {
        JSONObject jsonObject = JSONObject.parseObject(BaseApplication.
                readJsonFromAssets(Constants.OVERALL_DATA_TEST_JSON)).getJSONObject(Constants.PARAMS);
        String[] keys = getResources().getStringArray(R.array.overall_data_params);
        OverallDataBean dataBean = new OverallDataBean(
                jsonObject.getLong(keys[0]),
                jsonObject.getLong(keys[1]),
                jsonObject.getDouble(keys[2]),
                jsonObject.getDouble(keys[3]),
                jsonObject.getDouble(keys[4]),
                jsonObject.getDouble(keys[5]),
                jsonObject.getDouble(keys[6]));
        BaseApplication.getDaoSession().getOverallDataBeanDao().insert(dataBean);
    }
}
