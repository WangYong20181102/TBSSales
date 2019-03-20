package com.tbs.sales.application;

import android.app.Application;

import com.tbs.sales.utils.AppInfoUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Mr.Wang on 2019/2/19 15:52.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //极光推送
        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);
        //将推送的唯一标识存入本地
        AppInfoUtils.setPushRegisterId(this, JPushInterface.getRegistrationID(getApplicationContext()));
    }
}
