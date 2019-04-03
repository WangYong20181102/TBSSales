package com.tbs.sales.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.adapter.TransactionClientAdapter;
import com.tbs.sales.bean.HomeDataBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.LogUtils;
import com.tbs.sales.utils.MoveDistanceUtils;
import com.tbs.sales.utils.OkHttpUtils;
import com.tbs.sales.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
 * Created by Mr.Wang on 2019/4/2 09:27.
 * 成交客户
 */
public class TransactionClientActivity extends BaseActivity implements TransactionClientAdapter.OnFilterClickListener {
    @BindView(R.id.relative_back)
    RelativeLayout relativeBack;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.linear_no_data)
    LinearLayout linearNoData;
    private Gson gson;
    private int mPage = 1;
    private int pageSize = 20;
    private boolean isDownRefresh = false;//是否是下拉刷新
    private List<HomeDataBean.ListBean> beanList;
    private boolean iMove;//屏幕滑动距离
    private LinearLayoutManager layoutManager;
    private TransactionClientAdapter adapter;
    private String time_m;  //当前日期
    private TimePickerView pvTime;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_client);
        ButterKnife.bind(this);
        gson = new Gson();
        initView();
        initTimePicker();
        initHttpRequest();
    }

    /**
     * 日期选择工具
     */
    private void initTimePicker() {
        TransactionClientAdapter.setOnFilterClickListener(this);
        dateFormat = new SimpleDateFormat("yyyy-MM");
        String format = dateFormat.format(System.currentTimeMillis());
        time_m = format;
        Calendar endDate = Calendar.getInstance();
        String[] split = format.split("-");
        endDate.set(Integer.parseInt(split[0]), Integer.parseInt(split[1]), 0);//设置结束年份
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                time_m = dateFormat.format(date);
                initData();
            }
        }).setType(new boolean[]{true, true, false, false, false, false})  //年、月、日、时、分、秒，true显示、false不显示
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setRangDate(null, endDate)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
            }
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mPage = 1;
        isDownRefresh = true;
        swipeRefreshLayout.setRefreshing(true);
        if (adapter != null) {
            adapter = null;
        }
        if (!beanList.isEmpty()) {
            beanList.clear();
        }
        initHttpRequest();

    }

    /**
     * 初始化视图
     */
    private void initView() {

        beanList = new ArrayList<>();
        //recycleView
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOnTouchListener(onTouchListener);
        recyclerView.setOnScrollListener(onScrollListener);
        new MoveDistanceUtils().setOnMoveDistanceListener(recyclerView, new MoveDistanceUtils.OnMoveDistanceListener() {
            @Override
            public void onMoveDistance(boolean b) {
                iMove = b;
            }
        });


        //初始化下拉刷新视图
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(swipeLister);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
    }

    //下拉刷新监听事件
    private SwipeRefreshLayout.OnRefreshListener swipeLister = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            initData();
        }
    };

    //上拉加载更多
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (adapter != null) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && layoutManager.findLastVisibleItemPosition() + 1 == adapter.getItemCount()) {
                    if (iMove) {
                        LoadMore();
                    }
                }
            }
        }

    };


    //加载更多数据
    private void LoadMore() {
        mPage++;
        initHttpRequest();
    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //处于下拉刷新时列表不允许点击  死锁问题
            if (swipeRefreshLayout.isRefreshing()) {
                return true;
            } else {
                return false;
            }
        }
    };

    /**
     * 初始化网络请求
     */
    private void initHttpRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("page", mPage);
        params.put("page_size", pageSize);
        params.put("list_type", "finish");
        params.put("time_m", time_m);
        params.put("co_type", "-1");
        params.put("token", AppInfoUtils.getToekn(this));
        OkHttpUtils.get(Constant.SALE_GETCOMLIST, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isDownRefresh = false;
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = new String(response.body().string());
                try {
                    final JSONObject jsonObject = new JSONObject(json);
                    String code = jsonObject.optString("code");
                    LogUtils.logE("==========>>请求成功" + jsonObject.toString());
                    if (code.equals("0")) {
                        final HomeDataBean dataBean = gson.fromJson(jsonObject.optString("data"), HomeDataBean.class);
                        final int totalNum = dataBean.getAll_count();
                        beanList.addAll(dataBean.getList());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (adapter == null) {
                                    adapter = new TransactionClientAdapter(TransactionClientActivity.this, beanList, time_m, totalNum);
                                    recyclerView.setAdapter(adapter);
                                } else {
                                    adapter.updateDateTime(time_m, totalNum);
                                }
                                if (dataBean.getList().size() != 0) {
                                    linearNoData.setVisibility(View.GONE);
                                    if (isDownRefresh) {
                                        isDownRefresh = false;
                                        recyclerView.scrollToPosition(0);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        adapter.notifyItemInserted(beanList.size() - pageSize);
                                    }
                                } else {
                                    if (mPage != 1) {
                                        ToastUtils.toastShort(TransactionClientActivity.this, "暂无更多数据");
                                    } else {
                                        if (beanList.isEmpty()) {
                                            linearNoData.setVisibility(View.VISIBLE);
                                        } else {
                                            linearNoData.setVisibility(View.GONE);
                                        }
                                    }
                                }
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.toastShort(TransactionClientActivity.this, jsonObject.optString("message"));
                                swipeRefreshLayout.setRefreshing(false);
                                if (adapter == null) {
                                    adapter = new TransactionClientAdapter(TransactionClientActivity.this, beanList, time_m, 0);
                                    recyclerView.setAdapter(adapter);
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

    @OnClick(R.id.relative_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_back://返回
                finish();
                break;
        }
    }

    @Override
    public void onFilterClick(View view) {
        switch (view.getId()) {
            case R.id.linear_date_time:
                pvTime.show();
                break;
        }
    }
}
