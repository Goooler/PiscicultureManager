package io.goooler.pisciculturemanager.util;

import org.greenrobot.eventbus.EventBus;

import io.goooler.pisciculturemanager.model.EventType;

/**
 * EventBus 的简单封装，加入统一的判空等操作
 */

public class EventBusUtil {

    //注册 EventBus 之前先判断是否注册
    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    //注销 EventBus 之前先判断是否注册
    public static void unregister(Object subscriber) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }

    public static void post(EventType eventType) {
        EventBus.getDefault().post(eventType);
    }
}