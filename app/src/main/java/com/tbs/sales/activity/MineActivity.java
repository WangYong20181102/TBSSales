package com.tbs.sales.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tbs.sales.R;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.OkHttpUtils;
import com.tbs.sales.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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


    @Override
    protected void onResume() {
        super.onResume();
        tvNickname.setText(AppInfoUtils.getUserNickname(this));
        tvPosition.setText(AppInfoUtils.getUserNickname(this));
        textVersionCode.setText("v" + AppInfoUtils.getAppVersionName(this));
    }

    @OnClick({R.id.image_head, R.id.relative_change_head, R.id.relative_help_center, R.id.relative_number_safety, R.id.btn_exit_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_head:   //头像
            case R.id.relative_change_head:
                if (TextUtils.isEmpty(AppInfoUtils.getId(this))) {
                    startActivity(new Intent(MineActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(MineActivity.this, PeopleMessageActivity.class));
                }
                break;
            case R.id.relative_help_center: //帮助中心

                break;
            case R.id.relative_number_safety:   //账号安全
                startActivity(new Intent(MineActivity.this, AccountSecurityActivity.class));
                break;
            case R.id.btn_exit_login:   //退出登录
                new AlertDialog.Builder(this)
                        .setMessage("确定要退出？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                exitHttpRequest();
                            }
                        }).show();
                break;
        }
    }

    /**
     * 退出登录网络请求
     */
    private void exitHttpRequest() {

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppInfoUtils.getToekn(this));
        OkHttpUtils.post(Constant.LOGIN_DOLOGOUT, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = new String(response.body().string());
                try {
                    final JSONObject jsonObject = new JSONObject(json);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MineActivity.this, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                            getSharedPreferences("userInfo", 0).edit().clear().commit();
                            onResume();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
