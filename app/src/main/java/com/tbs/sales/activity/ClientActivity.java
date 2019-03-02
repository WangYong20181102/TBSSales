package com.tbs.sales.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.adapter.ClientViewPagerAdapter;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.widget.ClientItemWidget;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr.Wang on 2019/2/20 14:12.
 * 客户
 */
public class ClientActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.image_benyuepaihang)
    ImageView imageBenyuepaihang;
    @BindView(R.id.tv_list)
    TextView tvList;
    @BindView(R.id.image_right_side)
    ImageView imageRightSide;
    @BindView(R.id.ciw_my_client)
    ClientItemWidget ciwMyClient;
    @BindView(R.id.ciw_public_client)
    ClientItemWidget ciwPublicClient;
    @BindView(R.id.ciw_invalid_customer)
    ClientItemWidget ciwInvalidCustomer;
    @BindView(R.id.ciw_follow_up_record)
    ClientItemWidget ciwFollowUpRecord;
    @BindView(R.id.ciw_customer_follow_up)
    ClientItemWidget ciwCustomerFollowUp;
    @BindView(R.id.ciw_outbound_statistics)
    ClientItemWidget ciwOutboundStatistics;
    @BindView(R.id.viewpager_client)
    ViewPager viewpagerClient;
    @BindView(R.id.linear_point)
    LinearLayout linearPoint;
    private Intent intent;
    private ClientViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        ButterKnife.bind(this);
        initPoint();
        initData();
    }

    /**
     * 初始化小圆点
     */
    private void initPoint() {
        for (int i = 0; i < 3; i++) {
            //自定义view
            View view = new View(getApplicationContext());
            //为view对象设置背景
            view.setBackgroundResource(R.drawable.unselect_point);
            //为view对象设置容器
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.leftMargin = 26;
            view.setLayoutParams(params);
            linearPoint.addView(view);
        }
        selectPoint(0);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        adapter = new ClientViewPagerAdapter(this);
        viewpagerClient.setCurrentItem(3);
        viewpagerClient.setAdapter(adapter);
        viewpagerClient.addOnPageChangeListener(this);

    }

    @OnClick({R.id.ciw_my_client, R.id.ciw_public_client, R.id.ciw_invalid_customer, R.id.ciw_follow_up_record, R.id.ciw_customer_follow_up, R.id.ciw_outbound_statistics})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ciw_my_client:    //我的客户
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_CUSTOMER_MY);
                break;
            case R.id.ciw_public_client://公共客户
                break;
            case R.id.ciw_invalid_customer://无效客户
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_CUSTOMER_INVALID);
                break;
            case R.id.ciw_follow_up_record://跟进记录
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_CUSTOMER_FOLLOW);
                break;
            case R.id.ciw_customer_follow_up://客户跟进
                break;
            case R.id.ciw_outbound_statistics://外呼统计
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectPoint(position);
    }

    /**
     * 设置当前选中点
     *
     * @param position
     */
    private void selectPoint(int position) {
        for (int i = 0; i < 3; i++) {
            View view = linearPoint.getChildAt(i);
            view.setBackgroundResource(i == position % 3 ? R.drawable.select_point : R.drawable.unselect_point);
        }
        switch (position){
            case 0://成交榜
                tvList.setText("成交榜");
                break;
            case 1:
                tvList.setText("跟进榜");
                break;
            case 2:
                tvList.setText("新增榜");
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
