package io.goooler.pisciculturemanager.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import io.goooler.pisciculturemanager.model.Constants;
import io.goooler.pisciculturemanager.util.LogUtil;

public class ActivityCollector {
    public static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activityList.clear();
        //android.os.Process.killProcess(android.os.Process.myPid());
        LogUtil.d(Constants.BASE_ACTIVITY, Constants.FINISH_ALL_ACTIVITY);
    }
}