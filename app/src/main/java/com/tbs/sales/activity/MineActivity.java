package com.tbs.sales.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbs.sales.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr.Wang on 2019/2/20 14:13.
 * 我的
 */
public class MineActivity extends BaseActivity {

    @BindView(R.id.image_head)
    ImageView imageHead;
    @BindView(R.id.relative_change_head)
    RelativeLayout relativeChangeHead;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.relative_help_center)
    RelativeLayout relativeHelpCenter;
    @BindView(R.id.relative_number_safety)
    RelativeLayout relativeNumberSafety;
    @BindView(R.id.text_version_code)
    TextView textVersionCode;
    @BindView(R.id.btn_exit_login)
    Button btnExitLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.image_head, R.id.relative_change_head, R.id.relative_help_center, R.id.relative_number_safety, R.id.btn_exit_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_head:   //头像
            case R.id.relative_change_head:
                startActivity(new Intent(MineActivity.this,PeopleMessageActivity.class));
                break;
            case R.id.relative_help_center: //帮助中心
                break;
            case R.id.relative_number_safety:   //账号安全
                startActivity(new Intent(MineActivity.this,AccountSecurityActivity.class));
                break;
            case R.id.btn_exit_login:   //退出登录
                break;
        }
    }
}
