package io.goooler.pisciculturemanager.base;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import io.goooler.pisciculturemanager.R;

public class BaseActivity extends AppCompatActivity {
    public final String NULL_STRING = "";
    public final String NULL_OBJECT = null;
    public final String SP_USERINFO = "user_config";

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

    public void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public <T extends View> T find(int resId) {
        return (T) super.findViewById(resId);
    }

    public SharedPreferences getSp(String spName) {
        return getSharedPreferences(spName, MODE_PRIVATE);
    }

    public SharedPreferences.Editor getSpEditer(String spName) {
       return getSharedPreferences(spName, MODE_PRIVATE).edit();
    }

    //保存用户登录的状态
    public void saveUserInfoState(String username) {
        getSpEditer(SP_USERINFO).putString(getString(R.string.username_english), username).
                putBoolean(getString(R.string.user_saved), true).commit();
    }

    //获取用户登录的状态
    public boolean getUserInfoState() {
        return getSp(SP_USERINFO).getBoolean(getString(R.string.user_saved), false);
    }
}
