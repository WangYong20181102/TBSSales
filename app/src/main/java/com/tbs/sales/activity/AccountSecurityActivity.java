package com.tbs.sales.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.widget.AccountSecurityWidget;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr.Wang on 2019/2/21 15:25.
 */
public class AccountSecurityActivity extends BaseActivity {
    @BindView(R.id.relative_back)
    RelativeLayout relativeBack;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.asw_old_password)
    AccountSecurityWidget aswOldPassword;
    @BindView(R.id.asw_new_password)
    AccountSecurityWidget aswNewPassword;
    @BindView(R.id.asw_sure_password)
    AccountSecurityWidget aswSurePassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_security);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.relative_back, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_back:    //返回
                finish();
                break;
            case R.id.tv_sure://提交
                break;
        }
    }
}
