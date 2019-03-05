package com.tbs.sales.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.bean.Event;
import com.tbs.sales.bean.LoginSuccessBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.EC;
import com.tbs.sales.utils.EventBusUtil;
import com.tbs.sales.utils.OkHttpUtils;

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
    }

    /**
     * 初始化数据
     */
    private void initView() {
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
                    Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editPassword.getText().toString().trim())) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
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
        OkHttpUtils.post(Constant.LOGIN_DOLOGIN, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = Objects.requireNonNull(response.body().string());
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String code = jsonObject.optString("code");
                    if (code.equals("0")) {
                        successBean = gson.fromJson(jsonObject.optString("data"), LoginSuccessBean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideSystemKeyBroad();
                                saveUserInfo(successBean);
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
        AppInfoUtils.setUserCity(this, successBean.getUserinfo().getCity_id_list());
        AppInfoUtils.setUserNickname(this, successBean.getUserinfo().getReal_name());
        EventBusUtil.sendEvent(new Event(EC.EventCode.UPDATE_HOME_DATA));
        this.finish();
    }
    /**
     * 隐藏系统键盘
     */
    private void hideSystemKeyBroad() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
