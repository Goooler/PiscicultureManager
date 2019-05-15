package io.goooler.pisciculturemanager.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class BaseActivity extends AppCompatActivity {
    public final String NULL_STRING = "";
    public final String NULL_OBJECT = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity", getClass().getSimpleName());
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
