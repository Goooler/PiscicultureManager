package io.goooler.pisciculturemanager.base;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import io.goooler.pisciculturemanager.model.Constants;
import io.goooler.pisciculturemanager.util.LogUtil;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(Constants.BASE_ACTIVITY, getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    public <T extends View> T find(int resId) {
        return (T) super.findViewById(resId);
    }
}
