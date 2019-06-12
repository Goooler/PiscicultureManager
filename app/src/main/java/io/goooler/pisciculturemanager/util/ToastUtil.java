package io.goooler.pisciculturemanager.util;

import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import io.goooler.pisciculturemanager.base.BaseApplication;

/**
 * Toast 简单封装，可在子线程直接使用
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class ToastUtil {

    /**
     * 默认长度 Toast.LENGTH_SHORT，
     *
     * @param text string 文本
     */
    public static void showToast(String text) {
        Toast.makeText(BaseApplication.getContext(), text, Toast.LENGTH_SHORT).show();
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
