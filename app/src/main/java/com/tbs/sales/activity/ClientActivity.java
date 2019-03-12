package com.tbs.sales.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.adapter.ClientViewPagerAdapter;
import com.tbs.sales.bean.ClientLeaderboardBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.DateTimeUtils;
import com.tbs.sales.utils.OkHttpUtils;
import com.tbs.sales.widget.ClientItemWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    private String time = "";   //系统当前日期
    private Gson gson;
    private List<ClientLeaderboardBean> listList;//排行榜列表

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        ButterKnife.bind(this);
        gson = new Gson();
        initData();
        initPoint();
        initNetWork();
    }

    /**
     * 初始化网络请求
     */
    private void initNetWork() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("area_id", "-1");
        params.put("time", time);
        params.put("token", AppInfoUtils.getToekn(this));
        params.put("device", "h5");
        OkHttpUtils.post(Constant.STATS_GETSTATSINDEX, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = new String(response.body().string());
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String code = jsonObject.optString("code");
                    if (code.equals("0")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        listList.add(gson.fromJson(jsonObject1.optString("whole_billboard"), ClientLeaderboardBean.class));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (adapter == null) {
                                    adapter = new ClientViewPagerAdapter(ClientActivity.this, listList);
                                    viewpagerClient.setAdapter(adapter);
                                } else {
                                    viewpagerClient.notify();
                                }
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
        //获取系统当前日期
        time = DateTimeUtils.getYYMM();
        listList = new ArrayList<>();

        viewpagerClient.setCurrentItem(3);
        viewpagerClient.addOnPageChangeListener(this);//滑动监听

    }

    @OnClick({R.id.ciw_my_client, R.id.ciw_public_client, R.id.ciw_invalid_customer, R.id.ciw_follow_up_record, R.id.tv_list, R.id.image_right_side, R.id.ciw_customer_follow_up, R.id.ciw_outbound_statistics})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ciw_my_client:    //我的客户
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_CUSTOMER_MY);
                break;
            case R.id.ciw_public_client://公共客户
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_CUSTOMER_COMMON);
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
            case R.id.tv_list:  //榜单详情页
            case R.id.image_right_side:
                intent = new Intent(this, WebViewActivity.class);
                String str = tvList.getText().toString().trim();
                if (str.equals("成交榜")) {
                    intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_FINISH);
                } else if (str.equals("跟进榜")) {
                    intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_FOLLOW);
                } else {
                    intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_ADD);
                }
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


    @Override
    public void onPageScrollStateChanged(int state) {

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
        switch (position) {
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
}
