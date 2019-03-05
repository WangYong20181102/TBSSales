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

import com.tbs.sales.R;
import com.tbs.sales.adapter.MyMessageAdapter;
import com.tbs.sales.bean.MyMessageBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private int mPage = 0;
    private int pageSize = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOnTouchListener(onTouchListener);
        recyclerView.setOnScrollListener(onScrollListener);

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
//            initData();
        }
    };
    //上拉加载更多
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (adapter != null) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && layoutManager.findLastVisibleItemPosition() + 1 == adapter.getItemCount()) {
                    LoadMore();
                }
            }
        }
    };

    //加载更多数据
    private void LoadMore() {
        mPage++;
//        initHttpRequest();
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
        beanList = new ArrayList<>();
        MyMessageBean myMessageBean = null;
        for (int i = 0; i < 15; i++) {
            myMessageBean = new MyMessageBean();
            myMessageBean.setCity("城市: 深圳市");
            myMessageBean.setClientId("客户ID: 123456");
            myMessageBean.setContent("你有一个合同审批待处理");
            myMessageBean.setDate("2019/3/1 14:09");
            myMessageBean.setName("客户");
            beanList.add(myMessageBean);
        }
        adapter = new MyMessageAdapter(this, beanList);
        recyclerView.setAdapter(adapter);
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
