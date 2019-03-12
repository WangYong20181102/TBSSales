package com.tbs.sales.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tbs.sales.R;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.OkHttpUtils;
import com.tbs.sales.widget.AccountSecurityWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Mr.Wang on 2019/2/21 15:25.
 * 账号安全
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
                if (TextUtils.isEmpty(aswOldPassword.getText()) || TextUtils.isEmpty(aswNewPassword.getText()) || TextUtils.isEmpty(aswSurePassword.getText())) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    initHttpRequest();
                }
                break;
        }
    }

    /**
     * 网络请求
     */
    private void initHttpRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppInfoUtils.getToekn(this));
        params.put("pwd", aswOldPassword.getText());
        params.put("new_pwd", aswNewPassword.getText());
        params.put("again_pwd", aswSurePassword.getText());
        OkHttpUtils.post(Constant.USER_CHANGEPWD, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    final JSONObject jsonObject = new JSONObject(json);
                    String code = jsonObject.optString("code");
                    if (code.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AccountSecurityActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                                getSharedPreferences("userInfo", 0).edit().clear().commit();
                                finish();
                                startActivity(new Intent(AccountSecurityActivity.this, LoginActivity.class));
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AccountSecurityActivity.this, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
