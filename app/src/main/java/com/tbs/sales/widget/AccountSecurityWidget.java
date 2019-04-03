package com.tbs.sales.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbs.sales.R;


/**
 * Created by Mr.Wang on 2019/2/21 15:38.
 */
public class AccountSecurityWidget extends LinearLayout{

    /**
     * 左侧显示文案
     */
    private TextView tvPassword;
    /**
     * 输入框
     */
    private ClearEditText etPassword;

    public AccountSecurityWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initData(context, attrs);
    }

    /**
     * 初始化数据
     *
     * @param context
     * @param attrs
     */
    private void initData(Context context, AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AccountSecurityWidget);
        //左边提示文案
        CharSequence leftText = array.getText(R.styleable.AccountSecurityWidget_left_text);
        tvPassword.setText(leftText);
        //输入框默认提示文案
        CharSequence inputHintText = array.getText(R.styleable.AccountSecurityWidget_input_hint_text);
        etPassword.setHint(inputHintText);
        array.recycle();
    }

    /**
     * 初始化视图
     *
     * @param context
     */
    private void initView(Context context) {
        if (isInEditMode()) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.account_security_widget, this, true);
        tvPassword = view.findViewById(R.id.tv_password);
        etPassword = view.findViewById(R.id.et_password);
    }

    public String getText() {
        return etPassword.getText().toString().trim();
    }
}
