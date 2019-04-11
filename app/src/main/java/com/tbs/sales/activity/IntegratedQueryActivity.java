package com.tbs.sales.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.adapter.FilterCityAdapter;
import com.tbs.sales.adapter.FilterClientTypeAdapter;
import com.tbs.sales.adapter.IntegratedQueryAdapter;
import com.tbs.sales.adapter.PeopleAdapter;
import com.tbs.sales.bean.CityBean;
import com.tbs.sales.bean.Event;
import com.tbs.sales.bean.HomeDataBean;
import com.tbs.sales.bean.KeyValueDataBean;
import com.tbs.sales.bean.PeopleBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.DialogUtils;
import com.tbs.sales.utils.EC;
import com.tbs.sales.utils.KeyValueUtils;
import com.tbs.sales.utils.LogUtils;
import com.tbs.sales.utils.MoveDistanceUtils;
import com.tbs.sales.utils.OkHttpUtils;
import com.tbs.sales.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
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
 * Created by Mr.Wang on 2019/4/2 12:10.
 * 综合查询
 */
public class IntegratedQueryActivity extends BaseActivity implements IntegratedQueryAdapter.OnFilterClickListener, View.OnClickListener {
    @BindView(R.id.relative_back)
    RelativeLayout relativeBack;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.linear_no_data)
    LinearLayout linearNoData;
    @BindView(R.id.view_top)
    View viewTop;   //主要用于popupwindow位置显示
    /*********筛选控件************/
    private PopupWindow popupWindow;
    private LinearLayout linearPopBg;   //筛选弹框父布局
    //客户类型
    private GridView gridViewClientType;
    private FilterClientTypeAdapter adapterClientType;
    private List<KeyValueDataBean> listClientType;
    //城市
    private GridView gridViewCity;
    private FilterCityAdapter adapterCity;
    private List<CityBean> cityBeanList;
    //领取人
    private GridView gridViewNextVisit;
    private PeopleAdapter peopleAdapder;
    private List<PeopleBean> peopleBeanList;
    //重置
    private TextView textReset;
    //确定
    private TextView textSure;
    private LinearLayoutManager layoutManager;
    private IntegratedQueryAdapter adapter;
    private Gson gson;
    private int mPage = 1;
    private int pageSize = 20;
    private boolean isDownRefresh = false;//是否是下拉刷新
    private List<HomeDataBean.ListBean> beanList;
    private int coType = -1; //客户类型
    private String city = "";//城市
    private boolean iMove;//屏幕滑动距离
    private String respond = "";//领取人账号id

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integrated_query);
        ButterKnife.bind(this);
        gson = new Gson();
        initView();
        initHttpRequest();
        initRequestPeople();
        initCity(AppInfoUtils.getUserCity(this));
        initPopupWindow();
    }

    /**
     * 获取领取人列表
     */
    private void initPeople(final String people) {
        peopleBeanList = new ArrayList<>();
        //默认第一个为 全部 按钮
        peopleBeanList.add(new PeopleBean(-1, "全部"));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONArray jsonArray = new JSONArray(people);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        peopleBeanList.add(gson.fromJson(jsonArray.get(i).toString(), PeopleBean.class));
                    }
                    if (peopleAdapder == null) {
                        peopleAdapder = new PeopleAdapter(IntegratedQueryActivity.this, peopleBeanList, 0);
                        gridViewNextVisit.setAdapter(peopleAdapder);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取领取人列表
     */
    private void initRequestPeople() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppInfoUtils.getToekn(this));
        OkHttpUtils.get(Constant.ORGAN_GETALLSTAFF, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    final JSONObject jsonObject = new JSONObject(json);
                    String code = jsonObject.optString("code");
                    if (code.equals("0")) {
                        final JSONObject data = jsonObject.optJSONObject("data");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initPeople(data.optString("list"));
                                if (peopleAdapder != null) {
                                    peopleAdapder.setChangeCityData(peopleBeanList);
                                    peopleAdapder.notifyDataSetChanged();
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
     * 初始化城市信息
     */
    private void initCity(final String data) {
        cityBeanList = new ArrayList<>();
        //获取登录成功保存到本地的城市id
        if (!TextUtils.isEmpty(data)) {
            //默认第一个为 全部 按钮
            cityBeanList.add(new CityBean("01", "全部"));
            //跟本地保存城市信息对比，得到对应城市名称放入集合中
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray jsonArray = new JSONArray(data);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            cityBeanList.add(gson.fromJson(jsonArray.get(i).toString(), CityBean.class));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            requestCityMessage();
        }
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
            case EC.EventCode.UPDATE_MINE_CLIENT:
                initData();
                break;
        }
    }

    /**
     * 请求城市信息
     */
    public void requestCityMessage() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppInfoUtils.getToekn(this));
        OkHttpUtils.get(Constant.USER_GETUSERCITYLIST, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    final JSONObject jsonObject = new JSONObject(json);
                    String code = jsonObject.optString("code");
                    if (code.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AppInfoUtils.setUserCity(IntegratedQueryActivity.this, jsonObject.optString("data"));
                                if (cityBeanList != null) {
                                    cityBeanList = null;
                                    initCity(jsonObject.optString("data"));
                                }
                                if (adapterCity != null) {
                                    adapterCity.setChangeCityData(cityBeanList);
                                    adapterCity.notifyDataSetChanged();
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
     * 初始化popupWindow
     */
    private void initPopupWindow() {
        //筛选回调事件
        IntegratedQueryAdapter.setOnFilterClickListener(this);
        //筛选弹框布局
        View view = LayoutInflater.from(this).inflate(R.layout.integrated_query_layout, null);
        linearPopBg = view.findViewById(R.id.linear_bg);
        gridViewClientType = view.findViewById(R.id.grid_view_client_type);
        gridViewCity = view.findViewById(R.id.grid_view_city);
        gridViewNextVisit = view.findViewById(R.id.grid_view_next_visit);
        textReset = view.findViewById(R.id.text_reset);
        textSure = view.findViewById(R.id.text_sure);
        linearPopBg.setOnClickListener(this);
        textReset.setOnClickListener(this);
        textSure.setOnClickListener(this);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80ffffff")));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();

        initPopupData();
    }

    /**
     * popup数据
     */
    private void initPopupData() {
        //客户类型数据集
        listClientType = KeyValueUtils.getIQClientType();
        adapterClientType = new FilterClientTypeAdapter(this, listClientType, 0);
        adapterCity = new FilterCityAdapter(this, cityBeanList, 0);
        gridViewClientType.setAdapter(adapterClientType);
        gridViewCity.setAdapter(adapterCity);
        //领取人点击事件
        gridViewNextVisit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //==0全部
                if (position == 0) {
                    respond = "";
                } else {
                    respond = ((PeopleBean) peopleAdapder.getItem(position)).getId() + "";
                }
                //大于6显示更多按钮
                if (peopleBeanList.size() > 6) {
                    if (position == 5) {
                        DialogUtils.getInstances().showPeopleMessage(IntegratedQueryActivity.this, peopleBeanList, onPeopleResultListener);
                    } else {
                        peopleAdapder.setSelectPosition(position);
                        peopleAdapder.notifyDataSetChanged();
                    }
                } else {
                    peopleAdapder.setSelectPosition(position);
                    peopleAdapder.notifyDataSetChanged();
                }
            }
        });
        //客户类型点击事件
        gridViewClientType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterClientType.setSelectPosition(position);
                adapterClientType.notifyDataSetChanged();
                coType = listClientType.get(position).getId();
            }
        });
        //城市点击事件
        gridViewCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //==0全部
                if (position == 0) {
                    city = "";
                } else {
                    city = ((CityBean) adapterCity.getItem(position)).getId();
                }
                //大于6显示更多按钮
                if (cityBeanList.size() > 6) {
                    if (position == 5) {    //更多按钮点击弹出城市列表对话框
                        DialogUtils.getInstances().showCityMessage(IntegratedQueryActivity.this, cityBeanList, onCityResultListener);
                    } else {
                        adapterCity.setSelectPosition(position);
                        adapterCity.notifyDataSetChanged();
                    }
                } else {
                    adapterCity.setSelectPosition(position);
                    adapterCity.notifyDataSetChanged();
                }
            }
        });

    }

    //城市   更多点击回调更改listview数据结构
    DialogUtils.OnCityResultListener onCityResultListener = new DialogUtils.OnCityResultListener() {
        @Override
        public void onCityResult(CityBean cityData) {
            city = cityData.getId();
            for (int i = 0; i < cityBeanList.size(); i++) {
                if (cityData.equals(cityBeanList.get(i))) {
                    if (i < 5) {
                        adapterCity.changeCityMessage(cityBeanList, i);
                    } else {    //移除集合中指定位置item，并添加到第一个显示
                        cityBeanList.remove(i);
                        cityBeanList.add(1, cityData);
                        adapterCity.changeCityMessage(cityBeanList, 1);
                    }
                    adapterCity.notifyDataSetChanged();
                    break;
                }
            }
        }
    };
    //领取人   更多点击回调更改listview数据结构
    DialogUtils.OnPeopleResultListener onPeopleResultListener = new DialogUtils.OnPeopleResultListener() {

        @Override
        public void onPeopleResult(PeopleBean peopleBean) {
            respond = peopleBean.getId() + "";
            for (int i = 0; i < peopleBeanList.size(); i++) {
                if (peopleBean.equals(peopleBeanList.get(i))) {
                    if (i < 5) {
                        peopleAdapder.changeCityMessage(peopleBeanList, i);
                    } else {    //移除集合中指定位置item，并添加到第一个显示
                        peopleBeanList.remove(i);
                        peopleBeanList.add(1, peopleBean);
                        peopleAdapder.changeCityMessage(peopleBeanList, 1);
                    }
                    peopleAdapder.notifyDataSetChanged();
                    break;
                }
            }
        }
    };

    @OnClick({R.id.relative_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_back:    //返回
                finish();
                break;
        }
    }

    /**
     * 初始化网络请求
     */
    private void initHttpRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("respond", respond);
        params.put("page", mPage);
        params.put("page_size", pageSize);
        params.put("city", city);
        params.put("co_type", coType);
        params.put("token", AppInfoUtils.getToekn(this));
        OkHttpUtils.get(Constant.SALE_GETCOMPOSITELIST, params, new Callback() {
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
                        beanList.addAll(dataBean.getList());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (adapter == null) {
                                    adapter = new IntegratedQueryAdapter(IntegratedQueryActivity.this, beanList);
                                    recyclerView.setAdapter(adapter);
                                }
                                if (dataBean.getList().size() != 0) {
                                    linearNoData.setVisibility(View.GONE);
                                    if (isDownRefresh) {
                                        isDownRefresh = false;
                                        recyclerView.scrollToPosition(0);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        adapter.notifyItemInserted(beanList.size() - pageSize + 1);
                                    }
                                } else {
                                    if (mPage != 1) {
                                        ToastUtils.toastShort(IntegratedQueryActivity.this, "暂无更多数据");
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
                                ToastUtils.toastShort(IntegratedQueryActivity.this, jsonObject.optString("message"));
                                swipeRefreshLayout.setRefreshing(false);
                                //主要用于显示搜索框
                                if (adapter == null) {
                                    adapter = new IntegratedQueryAdapter(IntegratedQueryActivity.this, beanList);
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

    @Override
    public void onFilterClick(View view) {
        switch (view.getId()) {
            case R.id.image_filter: //筛选按钮回调
                popupWindow.showAsDropDown(viewTop);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_bg:    //筛选功能灰色背景
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;

            case R.id.text_reset://重置
                if (adapterClientType != null) {
                    adapterClientType.setSelectPosition(0);
                    adapterClientType.notifyDataSetChanged();
                    coType = -1;
                }
                if (adapterCity != null) {
                    adapterCity.setSelectPosition(0);
                    adapterCity.notifyDataSetChanged();
                    city = "";
                }
                if (peopleAdapder != null) {
                    peopleAdapder.setSelectPosition(0);
                    peopleAdapder.notifyDataSetChanged();
                    respond = "";
                }

                break;
            case R.id.text_sure:    //确定
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                initData();
                break;
        }
    }
}
