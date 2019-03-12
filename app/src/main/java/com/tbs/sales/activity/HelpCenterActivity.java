package com.tbs.sales.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.tbs.sales.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr.Wang on 2019/3/6 14:34.
 * 帮助中心
 */
public class HelpCenterActivity extends BaseActivity {
    @BindView(R.id.linear_back)
    LinearLayout linearBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.linear_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linear_back://返回
                finish();
                break;
        }
    }
}
