package com.tbs.sales.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.tbs.sales.R;

import java.util.ArrayList;
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
    @BindView(R.id.btn_glide)
    Button btnGlide;
    private MyPagerAdapter adapter;
    private List<Integer> integerList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_pager);
        ButterKnife.bind(this);
        initView();
        initData();
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
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        integerList = new ArrayList<>();
        integerList.add(R.mipmap.kehuguanli);
        integerList.add(R.mipmap.add);
        integerList.add(R.mipmap.already_divided);
        integerList.add(R.mipmap.benyuepaihang);
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
