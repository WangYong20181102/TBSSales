package com.tbs.sales.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbs.sales.R;

/**
 * Created by Mr.Wang on 2019/3/30 16:42.
 */
public class AddClientItem1 extends LinearLayout {
    private TextView tvLeftIcon;
    private TextView tvLeft;
    private TextView cetText;

    public AddClientItem1(Context context, @Nullable AttributeSet attrs) {
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
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AddClientItem1);
        //左侧*标志
        boolean leftIcon = array.getBoolean(R.styleable.AddClientItem1_must_input, false);
        if (leftIcon) {
            tvLeftIcon.setVisibility(VISIBLE);
        } else {
            tvLeftIcon.setVisibility(GONE);
        }
        //左侧文字
        CharSequence leftText = array.getText(R.styleable.AddClientItem1_left_show_content);
        tvLeft.setText(leftText);

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
        View view = LayoutInflater.from(context).inflate(R.layout.item_add_client1, this, true);
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
}
