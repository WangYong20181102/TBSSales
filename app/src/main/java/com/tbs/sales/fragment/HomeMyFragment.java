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
import android.widget.Toast;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.adapter.HomeMineFragmentAdapter;
import com.tbs.sales.bean.Event;
import com.tbs.sales.bean.HomeDataBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.EC;
import com.tbs.sales.utils.OkHttpUtils;

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
 * Created by Mr.Wang on 2019/2/22 11:07.
 */
public class HomeMyFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    private LinearLayoutManager layoutManager;
    private HomeMineFragmentAdapter adapter;
    private Gson gson;
    private int mPage = 0;
    private int pageSize = 20;
    private boolean isDownRefresh = false;//是否是下拉刷新
    private List<HomeDataBean.ListBean> beanList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_mine, null);
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
        params.put("page_size", pageSize);
        params.put("list_type", "all");
        params.put("co_type", "-1");
        params.put("token", AppInfoUtils.getToekn(getActivity()));
        OkHttpUtils.post(Constant.SALE_GETCOMLIST, params, new Callback() {
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
                        HomeDataBean dataBean = gson.fromJson(jsonObject.optString("data"), HomeDataBean.class);
                        beanList.addAll(dataBean.getList());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (adapter == null) {
                                    adapter = new HomeMineFragmentAdapter(getActivity(), beanList);
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
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

        //计算recycleView滑动距离
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();//获取LayoutManager
                //找到即将移出屏幕Item的position,position是移出屏幕item的数量
                int firstPosition = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
                //根据position找到这个Item
                View firstVisiableChildView = manager.findViewByPosition(firstPosition);
                //获取Item的高
                int itemHeight = firstVisiableChildView.getHeight();
                //算出该Item还未移出屏幕的高度
                int itemTop = firstVisiableChildView.getTop();
                //position移出屏幕的数量*高度得出移动的距离
                int iposition = firstPosition * itemHeight;
                //减去该Item还未移出屏幕的部分可得出滑动的距离
                int iResult = iposition - itemTop;
                if (adapter != null) {
                    int tittleHeight = adapter.getTittleHeight();
                    if (iResult > tittleHeight + 50) {
                        if (onLineShowHint != null) {
                            onLineShowHint.onLineShowHint(true);
                        }
                    } else if (iResult < tittleHeight + 50) {
                        if (onLineShowHint != null) {
                            onLineShowHint.onLineShowHint(false);
                        }
                    }
                }

            }
        });

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

    private static OnLineShowHintListener onLineShowHint = null;

    public static interface OnLineShowHintListener {
        void onLineShowHint(boolean b);
    }

    public static void setOnLineShowHintListener(OnLineShowHintListener onLineShowHintListener) {
        onLineShowHint = onLineShowHintListener;
    }
}
