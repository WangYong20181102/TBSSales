package com.tbs.sales.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tbs.sales.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
                break;
        }
    }
}
