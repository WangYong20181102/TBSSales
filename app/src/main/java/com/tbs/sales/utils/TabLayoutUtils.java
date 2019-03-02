package com.tbs.sales.utils;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbs.sales.R;

import java.lang.reflect.Field;

/**
 * Created by Mr.Wang on 2019/2/22 15:18.
 */
public class TabLayoutUtils {

    /**
     * 通过反射 改变tab的指示器的长度
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        tabs.setTabGravity(Gravity.CENTER);
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.leftMargin = left / 3;
            params.rightMargin = right / 3;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
    /**
     * 更改字体样式
     *
     * @param tab
     * @param isSelect
     */
    public static void updateTabView(TabLayout.Tab tab, boolean isSelect) {
        TextView tabSelect = (TextView) tab.getCustomView().findViewById(R.id.tab_item_textview);
        if (isSelect) {
            //选中加粗
            tabSelect.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tabSelect.setTextColor(Color.BLACK);
            tabSelect.setTextSize(17);
        } else {
            tabSelect.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tabSelect.setTextColor(Color.parseColor("#999999"));
            tabSelect.setTextSize(14);
        }
        tabSelect.setText(tab.getText());
    }
}
