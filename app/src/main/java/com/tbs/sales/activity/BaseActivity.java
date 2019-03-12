package com.tbs.sales.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tbs.sales.bean.Event;
import com.tbs.sales.manager.AppManager;
import com.tbs.sales.utils.EventBusUtil;

/**
 * Created by Mr.Wang on 2019/2/19 15:58.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstances().addActivity(this);
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
    }

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    public boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    public void receiveEvent(Event event) {

    }
}
