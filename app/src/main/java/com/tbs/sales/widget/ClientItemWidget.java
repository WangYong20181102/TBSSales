package com.tbs.sales.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbs.sales.R;

/**
 * Created by Mr.Wang on 2019/2/21 16:08.
 */
public class ClientItemWidget extends LinearLayout {

    /**
     * 左侧显示文案
     */
    private TextView tvLeft;
    public ClientItemWidget(Context context, @Nullable AttributeSet attrs) {
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
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ClientItemWidget);
        //左侧显示文案
        CharSequence leftShowText = array.getText(R.styleable.ClientItemWidget_left_show_text);
        tvLeft.setText(leftShowText);
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
        View view = LayoutInflater.from(context).inflate(R.layout.client_item_widget,this,true);
        tvLeft = view.findViewById(R.id.tv_left);
    }
}
