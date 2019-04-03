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
import android.widget.Toast;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.adapter.MyMessageAdapter;
import com.tbs.sales.bean.MyMessageBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.MoveDistanceUtils;
import com.tbs.sales.utils.OkHttpUtils;

import org.json.JSONArray;
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
 * Created by Mr.Wang on 2019/3/1 13:28.
 * 我的消息
 */
public class MyMessageActivity extends BaseActivity {
    @BindView(R.id.linear_back)
    LinearLayout linearBack;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private MyMessageAdapter adapter;
    private List<MyMessageBean> beanList;
    private LinearLayoutManager layoutManager;
    private int mPage = 1;
    private int pageSize = 20;
    private Gson gson;
    private boolean isDownRefresh = false;//是否是下拉刷新
    private boolean iMove;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        ButterKnife.bind(this);
        gson = new Gson();
        initView();
        initData();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        beanList = new ArrayList<>();
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
                    if (iMove){
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

    /**
     * 网络请求
     */
    private void initHttpRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppInfoUtils.getToekn(this));
        params.put("page", mPage);
        params.put("device_id", AppInfoUtils.getPushRegisterId(this));
        params.put("page_size", pageSize);
        OkHttpUtils.get(Constant.PUSH_RECORD, params, new Callback() {
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
                String json = response.body().string();
                try {
                    final JSONObject jsonObject = new JSONObject(json);
                    String code = jsonObject.optString("code");
                    if (code.equals("0")) {
                        JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                        final JSONArray jsonArray = new JSONArray(jsonObject1.optString("list"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            MyMessageBean myMessageBean = gson.fromJson(jsonArray.get(i).toString(), MyMessageBean.class);
                            beanList.add(myMessageBean);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (adapter == null) {
                                    adapter = new MyMessageAdapter(MyMessageActivity.this, beanList);
                                    recyclerView.setAdapter(adapter);
                                }
                                if (isDownRefresh) {
                                    isDownRefresh = false;
                                    recyclerView.scrollToPosition(0);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    adapter.notifyItemInserted(beanList.size() - pageSize);
                                }
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MyMessageActivity.this, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
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
     * 初始化数据
     */
    private void initData() {
        mPage = 1;
        isDownRefresh = true;
        swipeRefreshLayout.setRefreshing(true);
        if (adapter != null) {
            adapter = null;
        }
        if (beanList.size() != 0) {
            beanList.clear();
        }
        initHttpRequest();

    }

    @OnClick(R.id.linear_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linear_back:  //返回
                finish();
                break;
        }
    }
}
