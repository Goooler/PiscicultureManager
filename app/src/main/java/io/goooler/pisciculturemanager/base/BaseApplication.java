package io.goooler.pisciculturemanager.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.model.Constants;
import io.goooler.pisciculturemanager.model.DaoMaster;
import io.goooler.pisciculturemanager.model.DaoSession;
import io.goooler.pisciculturemanager.model.UserInfoStateBean;

/**
 * 封装通用方法和一些初始化的动作
 */
public class BaseApplication extends Application {
    private static Context context;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DB_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static Context getContext() {
        return context;
    }

    public static void showToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(int stringId) {
        Toast.makeText(context, context.getString(stringId), Toast.LENGTH_SHORT).show();
    }

    public static SharedPreferences getSp(String spName) {
        return context.getSharedPreferences(spName, MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getSpEditer(String spName) {
        return context.getSharedPreferences(spName, MODE_PRIVATE).edit();
    }

    public static void setFirstRunState(boolean firstRunState) {
        getSpEditer(Constants.SP_RUNINFO).putBoolean(Constants.SP_FIRST_RUN, firstRunState).apply();
    }

    public static boolean isFirstRun() {
        return getSp(Constants.SP_RUNINFO).getBoolean(Constants.SP_FIRST_RUN, true);
    }

    //保存用户登录的状态
    public static void setUserInfoState(UserInfoStateBean userInfoStateBean) {
        getSpEditer(Constants.SP_USERINFO).putString(context.getString(R.string.username_english), userInfoStateBean.getUsername()).
                putBoolean(context.getString(R.string.user_saved), userInfoStateBean.isSaved()).commit();
    }

    //获取用户登录的状态
    public static UserInfoStateBean getUserInfoState() {
        return new UserInfoStateBean(getSp(Constants.SP_USERINFO).getString(context.getString(R.string.username_english), Constants.NULL_STRING)
                , getSp(Constants.SP_USERINFO).getBoolean(context.getString(R.string.user_saved), false));
    }

    public static AssetManager getAssetsManager() {
        return context.getAssets();
    }

    //读取 assets 下的 LineChartTest.json
    public static String readJsonFromAssets(String fileName) {
        String jsonStrng = null;
        try {
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    BaseApplication.getAssetsManager().open(fileName)));
            while ((jsonStrng = reader.readLine()) != null) {
                builder.append(jsonStrng);
            }
            jsonStrng = builder.toString();
            reader.close();
        } catch (IOException e) {
        }
        return jsonStrng;
    }
}
