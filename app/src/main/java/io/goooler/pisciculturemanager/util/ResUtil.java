package io.goooler.pisciculturemanager.util;

import android.content.res.Resources;

import io.goooler.pisciculturemanager.base.BaseApplication;

public class ResUtil {

    public static Resources getResources() {
        return BaseApplication.getContext().getResources();
    }

    public static String getString(int id) {
        return getResources().getString(id);
    }
}
