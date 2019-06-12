package io.goooler.pisciculturemanager.util;

import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.base.BaseApplication;
import io.goooler.pisciculturemanager.model.Constants;
import io.goooler.pisciculturemanager.model.UserInfoStateBean;

import static android.content.Context.MODE_PRIVATE;

/**
 * SharedPreferences 几个工具的简单封装
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class SpUtil {

    /**
     * 获取 sp
     *
     * @param spName sp 文件名
     */
    public static SharedPreferences getSp(String spName) {
        return BaseApplication.getContext().getSharedPreferences(spName, MODE_PRIVATE);
    }

    /**
     * 获取 sp editer
     *
     * @param spName sp 文件名
     */
    public static SharedPreferences.Editor getSpEditer(String spName) {
        return getSp(spName).edit();
    }

    /**
     * 设置应用是否第一次启动的状态
     */
    public static void setFirstRunState(boolean firstRunState) {
        getSpEditer(Constants.SP_RUNINFO).putBoolean(Constants.SP_FIRST_RUN, firstRunState).apply();
    }

    /**
     * 判断应用是否第一次启动
     */
    public static boolean isFirstRun() {
        return getSp(Constants.SP_RUNINFO).getBoolean(Constants.SP_FIRST_RUN, true);
    }

    /**
     * 保存用户登录的状态
     *
     * @param userInfoStateBean 存储用户信息的实体
     */
    public static void setUserInfoState(UserInfoStateBean userInfoStateBean) {
        getSpEditer(Constants.SP_USERINFO).putString(ResUtil.getString(R.string.username_english), userInfoStateBean.getUsername()).
                putBoolean(ResUtil.getString(R.string.user_saved), userInfoStateBean.isSaved()).commit();
    }

    /**
     * 获取用户登录的状态
     *
     * @return 存储用户信息的实体
     */
    public static UserInfoStateBean getUserInfoState() {
        return new UserInfoStateBean(getSp(Constants.SP_USERINFO).getString(ResUtil.getString(R.string.username_english), Constants.NULL_STRING)
                , getSp(Constants.SP_USERINFO).getBoolean(ResUtil.getString(R.string.user_saved), false));
    }
}
