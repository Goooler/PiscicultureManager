package io.goooler.pisciculturemanager.util;

import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.RequiresApi;

import io.goooler.pisciculturemanager.base.BaseApplication;

/**
 * 获取资源的工具封装，可以在任何控件中直接获取
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class ResUtil {

    public static Resources getResources() {
        return BaseApplication.getContext().getResources();
    }

    public static String getString(int id) {
        return getResources().getString(id);
    }

    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    public static int getColor(int id) {
        return getResources().getColor(id, null);
    }
}
