package io.goooler.pisciculturemanager.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.ycbjie.notificationlib.NotificationUtils;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.activity.MainActivity;
import io.goooler.pisciculturemanager.model.Constants;

/**
 * Android 8.0 以上启用了通知渠道，需要在通知发出之前开启，
 * 这里方便起见调用了 cn.yc:notificationLib 这个库做个简单封装
 */

public class NotificationUtil {

    /**
     * 默认的通知样式，设置了默认图标，默认跳转事件，默认通知级别
     *
     * @param context 必须依赖传入一个 context，这里默认 intent 跳转主页，setAction 设置下发 fragment
     * @param title   通知标题
     * @param content 通知内容
     */
    public static void showDefault(Context context, String title, String content) {
        NotificationUtils notificationUtils = new NotificationUtils(context);
        Intent intent = new Intent(context, MainActivity.class).
                putExtra(Constants.GOTO_FRAGMENT_ID, Constants.NOTIFICATION_FRAGMENT_ID);
        PendingIntent notifyIntent = PendingIntent.getActivity(context, Constants.ACTIVITY_REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationUtils.setContentIntent(notifyIntent)
                .sendNotification(Constants.DEFAULT_NOTIFICATION_ID, title, content, R.mipmap.warnning);
    }
}
