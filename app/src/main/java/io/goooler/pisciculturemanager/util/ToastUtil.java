package io.goooler.pisciculturemanager.util;

import android.widget.Toast;

import io.goooler.pisciculturemanager.base.BaseApplication;

/**
 * Toast 简单封装，可在子线程直接使用
 */

public class ToastUtil {

    /**
     * 默认长度 Toast.LENGTH_SHORT，
     * TODO:子线程里直接 toast 的方法待验证
     *
     * @param text string 文本
     */
    public static void showToast(String text) {
        BaseApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseApplication.getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 默认长度 Toast.LENGTH_SHORT
     *
     * @param stringId 文本资源 id
     */
    public static void showToast(int stringId) {
        showToast(ResUtil.getString(stringId));
    }
}
