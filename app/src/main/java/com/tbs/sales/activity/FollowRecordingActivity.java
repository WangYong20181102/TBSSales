package com.tbs.sales.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.adapter.FollowRecordingAdapter;
import com.tbs.sales.adapter.IntegratedQueryAdapter;
import com.tbs.sales.bean.FollowRecordingBean;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Mr.Wang on 2019/4/3 10:14.
 * 跟进记录
 */
public class FollowRecordingActivity extends BaseActivity {
    @BindView(R.id.relative_back)
    RelativeLayout relativeBack;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.linear_no_data)
    LinearLayout linearNoData;
    private LinearLayoutManager layoutManager;
    private FollowRecordingAdapter adapter;
    private Gson gson;
    private int mPage = 1;
    private int pageSize = 20;
    private boolean isDownRefresh = false;//是否是下拉刷新
    private List<FollowRecordingBean.ListBean> beanList;
    private boolean iMove;//屏幕滑动距离

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_recording);
        ButterKnife.bind(this);
        gson = new Gson();
        initView();
        initHttpRequest();
    }

    /**
     * 网络请求
     */
    private void initHttpRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppInfoUtils.getToekn(this));
        params.put("co_type", "-1");
        params.put("page_size", pageSize);
        params.put("page", mPage);
        OkHttpUtils.post(Constant.SALE_GETOWNERFOLLOWLIST, params, new Callback() {
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
                        final FollowRecordingBean dataBean = gson.fromJson(jsonObject.optString("data"), FollowRecordingBean.class);
                        beanList.addAll(dataBean.getList());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (adapter == null) {
                                    adapter = new FollowRecordingAdapter(FollowRecordingActivity.this, beanList);
                                    recyclerView.setAdapter(adapter);
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
                                        ToastUtils.toastShort(FollowRecordingActivity.this, "暂无更多数据");
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
                                ToastUtils.toastShort(FollowRecordingActivity.this, jsonObject.optString("message"));
                                swipeRefreshLayout.setRefreshing(false);
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

    @OnClick(R.id.relative_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_back://返回
                finish();
                break;
        }
    }
}
