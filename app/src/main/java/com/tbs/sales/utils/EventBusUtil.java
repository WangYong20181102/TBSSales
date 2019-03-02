package com.tbs.sales.utils;

import com.tbs.sales.bean.Event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Mr.Wang on 2019/3/2 14:31.
 */
public class EventBusUtil {
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    //发送普通事件
    public static void sendEvent(Event event) {
        EventBus.getDefault().post(event);
    }

    //发送粘性事件
    public static void sendStickyEvent(Event event) {
        EventBus.getDefault().postSticky(event);
    }
}
