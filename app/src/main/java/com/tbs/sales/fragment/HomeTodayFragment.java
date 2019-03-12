package com.tbs.sales.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.adapter.HomeTodayFragmentAdapter;
import com.tbs.sales.bean.Event;
import com.tbs.sales.bean.HomeDataBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.EC;
import com.tbs.sales.utils.OkHttpUtils;
import com.tbs.sales.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Mr.Wang on 2019/2/22 11:08.
 */
public class HomeTodayFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.linear_no_data)
    LinearLayout linearNoData;
    private LinearLayoutManager layoutManager;
    private HomeTodayFragmentAdapter adapter;
    private Gson gson;
    private int mPage = 1;
    private int pageSize = 20;
    private boolean isDownRefresh = false;//是否是下拉刷新
    private List<HomeDataBean.ListBean> beanList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_today, null);
        unbinder = ButterKnife.bind(this, view);
        gson = new Gson();
        initView();
        initData();
        initHttpRequest();
        return view;
    }

    @Override
    public boolean isRegisterEventBus() {
        return true;
    }

    @Subscribe
    @Override
    public void receiveEvent(Event event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case EC.EventCode.UPDATE_HOME_DATA:
                initData();
                break;
        }
    }

    /**
     * 初始化网络请求
     */
    private void initHttpRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("page", mPage);
        params.put("plat", "mm");
        params.put("page_size", pageSize);
        params.put("list_type", "today");
        params.put("co_type", "-1");
        params.put("device", "h5");
        params.put("token", AppInfoUtils.getToekn(getActivity()));
        OkHttpUtils.get(Constant.SALE_GETCOMLIST, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
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
                    if (code.equals("0")) {
                        final HomeDataBean dataBean = gson.fromJson(jsonObject.optString("data"), HomeDataBean.class);
                        beanList.addAll(dataBean.getList());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (adapter == null) {
                                    adapter = new HomeTodayFragmentAdapter(getActivity(), beanList);
                                    recyclerView.setAdapter(adapter);
                                }
                                if (dataBean.getList().size() != 0) {
                                    if (isDownRefresh) {
                                        isDownRefresh = false;
                                        recyclerView.scrollToPosition(0);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        adapter.notifyItemInserted(beanList.size() - pageSize);
                                    }
                                } else {
                                    if (mPage != 1) {
                                        Toast.makeText(getActivity(), "暂无更多数据", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                if (beanList.size() == 0) {
                                    linearNoData.setVisibility(View.VISIBLE);
                                } else {
                                    linearNoData.setVisibility(View.GONE);
                                }
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.toastShort(getActivity(), jsonObject.optString("message"));
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
        layoutManager = new LinearLayoutManager(getActivity());
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
                    LoadMore();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
