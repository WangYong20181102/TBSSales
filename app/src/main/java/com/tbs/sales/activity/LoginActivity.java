package com.tbs.sales.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.tbs.sales.MainActivity;
import com.tbs.sales.R;
import com.tbs.sales.bean.Event;
import com.tbs.sales.bean.LoginSuccessBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.manager.AppManager;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.EC;
import com.tbs.sales.utils.EventBusUtil;
import com.tbs.sales.utils.LogUtils;
import com.tbs.sales.utils.OkHttpUtils;
import com.tbs.sales.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Mr.Wang on 2019/2/20 14:54.
 * 登录
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.image_login_head)
    ImageView imageLoginHead;
    @BindView(R.id.edit_username)
    EditText editUsername;
    @BindView(R.id.image_password_del)
    ImageView imagePasswordDel;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.image_username_del)
    ImageView imageUsernameDel;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private Gson gson;
    private LoginSuccessBean successBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        gson = new Gson();
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        String userName = AppInfoUtils.getLoginAccound(this);
        if (!TextUtils.isEmpty(userName)) { //账号不为空，密码输入框获取焦点
            editPassword.requestFocus();
        }
        editUsername.setText(userName);
        imageUsernameDel.setVisibility(View.GONE);
    }

    /**
     * 初始化数据
     */
    private void initView() {
        editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!TextUtils.isEmpty(editPassword.getText().toString().trim())) {
                    imagePasswordDel.setVisibility(View.VISIBLE);
                }
                imageUsernameDel.setVisibility(View.GONE);
            }
        });
        editUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!TextUtils.isEmpty(editUsername.getText().toString().trim())) {
                    imageUsernameDel.setVisibility(View.VISIBLE);
                }
                imagePasswordDel.setVisibility(View.GONE);
            }
        });
        //用户名清除按钮
        editUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    imageUsernameDel.setVisibility(View.VISIBLE);
                } else {
                    imageUsernameDel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //密码清除按钮
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    imagePasswordDel.setVisibility(View.VISIBLE);
                } else {
                    imagePasswordDel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.image_password_del, R.id.image_username_del, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_password_del:   //密码
                editPassword.setText("");
                break;
            case R.id.image_username_del:   //用户名
                editUsername.setText("");
                break;
            case R.id.btn_login:    //登录
                if (TextUtils.isEmpty(editUsername.getText().toString().trim())) {
                    ToastUtils.toastShort(this, "请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(editPassword.getText().toString().trim())) {
                    ToastUtils.toastShort(this, "请输入密码");
                    return;
                }

                httpRequest();
                break;

        }
    }

    /**
     * 网络请求
     */
    private void httpRequest() {

        HashMap<String, Object> params = new HashMap<>();
        params.put("username", editUsername.getText().toString().trim());
        params.put("password", editPassword.getText().toString().trim());
        params.put("plat", "android");
        params.put("device_id", AppInfoUtils.getPushRegisterId(this));
        LogUtils.logE(AppInfoUtils.getPushRegisterId(this));
        OkHttpUtils.post(Constant.LOGIN_DOLOGIN, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = Objects.requireNonNull(response.body().string());
                try {
                    final JSONObject jsonObject = new JSONObject(json);
                    String code = jsonObject.optString("code");
                    if (code.equals("0")) { //登录成功
                        successBean = gson.fromJson(jsonObject.optString("data"), LoginSuccessBean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideSystemKeyBroad();
                                saveUserInfo(successBean);
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.toastShort(LoginActivity.this, jsonObject.optString("message"));
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 保存用户信息
     *
     * @param successBean
     */
    private void saveUserInfo(LoginSuccessBean successBean) {
        AppInfoUtils.setId(this, successBean.getUserinfo().getId());
        AppInfoUtils.setToken(this, successBean.getToken());
        AppInfoUtils.setUserMd5PassWord(this, successBean.getUserinfo().getPassword());
        AppInfoUtils.setCellPhone(this, successBean.getUserinfo().getPhone());
        AppInfoUtils.setUserNickname(this, successBean.getUserinfo().getReal_name());
        AppInfoUtils.setUserSex(this, successBean.getUserinfo().getSex());
        AppInfoUtils.setUserIcon(this, successBean.getUserinfo().getIcon());
        AppInfoUtils.setUserRoleDesc(this, successBean.getUserinfo().getRole_desc());
        AppInfoUtils.setLoginAccount(this, editUsername.getText().toString().trim());
        if (AppManager.getInstances().isActivityExist(MainActivity.class)) {    //如果MainActivity，登录成功发送广播通知跟新数据
            EventBusUtil.sendEvent(new Event(EC.EventCode.UPDATE_HOME_DATA));   //更新首页数据
            EventBusUtil.sendEvent(new Event(EC.EventCode.UPDATE_CLIENT_DATA));//更新 客户 数据
            EventBusUtil.sendEvent(new Event(EC.EventCode.UPDATE_USERINFO));//更新用户数据
            EventBusUtil.sendEvent(new Event(EC.EventCode.MAIN_SELECT));//选择首页
        } else {//不存在开启MainActivity
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        finish();
    }

    /**
     * 隐藏系统键盘
     */
    private void hideSystemKeyBroad() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示隐藏密码
     */
    private void showOrHidePassword() {
        //密码设置为明文
        editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        //光标移动到内容最后
        editPassword.setSelection(editPassword.getText().toString().length());


        //密码设置为密文
        editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        //光标移动到内容最后
        editPassword.setSelection(editPassword.getText().toString().length());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            /**
             * 退出应用
             */
            AppManager.getInstances().AppExit(LoginActivity.this);
        }
        return false;
    }
}
