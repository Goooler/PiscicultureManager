package io.goooler.pisciculturemanager.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.model.DaoMaster;
import io.goooler.pisciculturemanager.model.DaoSession;
import io.goooler.pisciculturemanager.model.UserInfoStateBean;

import static io.goooler.pisciculturemanager.model.Constants.SP_USERINFO;

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
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "Manager.db");
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

    public static SharedPreferences getSp(String spName) {
        return context.getSharedPreferences(spName, MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getSpEditer(String spName) {
        return context.getSharedPreferences(spName, MODE_PRIVATE).edit();
    }

    //保存用户登录的状态
    public static void setUserInfoState(UserInfoStateBean userInfoStateBean) {
        getSpEditer(SP_USERINFO).putString(context.getString(R.string.username_english), userInfoStateBean.getUsername()).
                putBoolean(context.getString(R.string.user_saved), userInfoStateBean.isSaved()).commit();
    }

    //获取用户登录的状态
    public static UserInfoStateBean getUserInfoState() {
        return new UserInfoStateBean(getSp(SP_USERINFO).getString(context.getString(R.string.username_english), "")
                , getSp(SP_USERINFO).getBoolean(context.getString(R.string.user_saved), false));
    }
}
