package com.tbs.sales.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.utils.StringUtils;

/**
 * Created by Mr.Wang on 2019/3/30 16:42.
 */
public class AddClientItem extends LinearLayout {
    private TextView tvLeftIcon;
    private TextView tvLeft;
    private ClearEditText cetText;

    public AddClientItem(Context context, @Nullable AttributeSet attrs) {
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
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AddClientItem);
        //左侧*标志
        boolean leftIcon = array.getBoolean(R.styleable.AddClientItem_must_input_icon, false);
        if (leftIcon) {
            tvLeftIcon.setVisibility(VISIBLE);
        } else {
            tvLeftIcon.setVisibility(GONE);
        }
        //左侧文字
        CharSequence leftText = array.getText(R.styleable.AddClientItem_add_left_text);
        tvLeft.setText(leftText);
        //右侧文字
        CharSequence rightText = array.getText(R.styleable.AddClientItem_right_text);
        //输入最多字符
        int maxEms = array.getInteger(R.styleable.AddClientItem_max_ems, Integer.MAX_VALUE);
        //输入类型
        boolean inputType = array.getBoolean(R.styleable.AddClientItem_input_type, false);
        //默认文案
        CharSequence inputHint = array.getText(R.styleable.AddClientItem_input_hint);
        cetText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxEms)});
        cetText.setHint(inputHint);
        if (inputType) {
            cetText.setInputType(InputType.TYPE_CLASS_PHONE);
        } else {
            cetText.setInputType(InputType.TYPE_CLASS_TEXT);
        }


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
        View view = LayoutInflater.from(context).inflate(R.layout.item_add_client, this, true);
        tvLeft = view.findViewById(R.id.tv_left);
        tvLeftIcon = view.findViewById(R.id.tv_left_icon);
        cetText = view.findViewById(R.id.cet_text);
    }

    public String getText() {
        return cetText.getText().toString().trim();
    }

    /**
     * 设置文本内容
     */
    public void setText(String str) {
        cetText.setText(str);
    }

    /**
     * 设置座机格式
     */
    public void setPhone() {
        cetText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    int length = s.toString().length();
                    if (length == 4) {
                        cetText.setText(s + "-");
                        cetText.setSelection(cetText.getText().toString().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
