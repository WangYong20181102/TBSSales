package com.tbs.sales.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbs.sales.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Wang on 2019/3/20 16:34.
 * 引导页
 */
public class GuidePageActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_btn)
    TextView btnGlide;
    @BindView(R.id.linear_dots)
    LinearLayout linearDots;
    private MyPagerAdapter adapter;
    private List<Integer> integerList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_pager);
        ButterKnife.bind(this);
        initView();
        initData();
        initDots();

    }

    /**
     * 初始化dot
     */
    private void initDots() {
        linearDots.removeAllViews();
        for (int i = 0; i < integerList.size(); i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.rightMargin = 9;
            layoutParams.leftMargin = 9;
            imageView.setLayoutParams(layoutParams);
            imageView.setBackgroundResource(R.drawable.guide_dot_bg);
            linearDots.addView(imageView);
        }
        updateDots();
    }

    /**
     * 更新点
     */
    private void updateDots() {
        if (integerList.size() == 0) {
            return;
        }
        int currentPage = viewPager.getCurrentItem() % integerList.size();
        for (int i = 0; i < linearDots.getChildCount(); i++) {
            linearDots.getChildAt(i).setEnabled(i == currentPage);// 设置setEnabled为true的话
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.rightMargin = 9;
            layoutParams.leftMargin = 9;
            linearDots.getChildAt(i).setLayoutParams(layoutParams);
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        btnGlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuidePageActivity.this, LoginActivity.class));
                finish();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == integerList.size() - 1) {
                    btnGlide.setVisibility(View.VISIBLE);
                } else {
                    btnGlide.setVisibility(View.GONE);
                }
                updateDots();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        integerList = new ArrayList<>(Arrays.asList(R.mipmap.guide1,R.mipmap.guide2,R.mipmap.guide3,R.mipmap.guide4));
        adapter = new MyPagerAdapter(this);
        viewPager.setAdapter(adapter);
    }

    class MyPagerAdapter extends PagerAdapter {
        private Context context;

        public MyPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return integerList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = View.inflate(context, R.layout.glide_pager_layout, null);
            ImageView imageView = view.findViewById(R.id.image_bg);
            imageView.setImageResource(integerList.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
