package com.tbs.sales.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tbs.sales.MainActivity;
import com.tbs.sales.R;
import com.tbs.sales.bean.Event;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.manager.AppManager;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.EC;
import com.tbs.sales.utils.EventBusUtil;
import com.tbs.sales.utils.GlideUtils;
import com.tbs.sales.utils.OkHttpUtils;
import com.tbs.sales.utils.StringUtils;
import com.tbs.sales.utils.ToastUtils;

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
    private String userIcon = "";   //用户头像

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
        tvPosition.setText(AppInfoUtils.getUserRoleDesc(this));
        textVersionCode.setText("v" + AppInfoUtils.getAppVersionName(this));
        userIcon = AppInfoUtils.getUserIcon(this);
        if (TextUtils.isEmpty(userIcon)) {
            imageHead.setImageResource(R.mipmap.img_moren);
        } else {
            GlideUtils.glideLoader(this, userIcon, 0, 0, imageHead, 0);
        }

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
                startActivity(new Intent(MineActivity.this, HelpCenterActivity.class));
                break;
            case R.id.relative_number_safety:   //账号安全
                if (TextUtils.isEmpty(AppInfoUtils.getId(this))) {
                    startActivity(new Intent(MineActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(MineActivity.this, AccountSecurityActivity.class));
                }
                break;
            case R.id.btn_exit_login:   //退出登录
                new AlertDialog.Builder(this)
                        .setMessage("确定要退出？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                exitHttpRequest();
                            }
                        })
                        .setNegativeButton("取消", null).show();
                break;
        }
    }

    /**
     * 退出登录网络请求
     */
    private void exitHttpRequest() {

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppInfoUtils.getToekn(this));
        params.put("plat", "android");
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
                            ToastUtils.toastShort(MineActivity.this, jsonObject.optString("message"));
                            getSharedPreferences("userInfo", 0).edit().clear().commit();
                            Intent intent = new Intent(MineActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            new AlertDialog.Builder(this)
                    .setMessage("确定要退出吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AppManager.getInstances().AppExit(MineActivity.this);
                        }
                    })
                    .setNegativeButton("再看看", null).show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
