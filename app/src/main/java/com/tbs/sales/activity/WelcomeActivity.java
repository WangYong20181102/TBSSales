package com.tbs.sales.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.tbs.sales.MainActivity;
import com.tbs.sales.R;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;

/**
 * Created by Mr.Wang on 2019/3/18 10:10.
 */
public class WelcomeActivity extends BaseActivity {
    private MyHandler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!AppInfoUtils.getFirstLaunchGlidePage(this)) {//第一次启动
            AppInfoUtils.setFirstLaunchGlidePage(this, true);
            startActivity(new Intent(WelcomeActivity.this, GuidePageActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_welcome);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        handler = new MyHandler(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
                finish();
            }
        }, 3000);
    }

    static class MyHandler extends Handler {
        private Context context;

        public MyHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (!TextUtils.isEmpty(AppInfoUtils.getId(context))) {
                    context.startActivity(new Intent(context, MainActivity.class));
                } else {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        }
    }
}
