package com.tbs.sales.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tbs.sales.MainActivity;
import com.tbs.sales.R;
import com.tbs.sales.adapter.FilterCityAdapter;
import com.tbs.sales.adapter.FilterClientTypeAdapter;
import com.tbs.sales.adapter.FilterNextVisitAdapter;
import com.tbs.sales.adapter.HomeMineFragmentAdapter;
import com.tbs.sales.adapter.HomeViewPagerAdapter;
import com.tbs.sales.bean.CityBean;
import com.tbs.sales.bean.Event;
import com.tbs.sales.bean.KeyValueDataBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.fragment.HomeBriefingFragment;
import com.tbs.sales.fragment.HomeEarlyWarningFragment;
import com.tbs.sales.fragment.HomeMyFragment;
import com.tbs.sales.fragment.HomeTodayFragment;
import com.tbs.sales.manager.AppManager;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.DialogUtils;
import com.tbs.sales.utils.EC;
import com.tbs.sales.utils.KeyValueUtils;
import com.tbs.sales.utils.OkHttpUtils;
import com.tbs.sales.utils.TabLayoutUtils;

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
 * Created by Mr.Wang on 2019/2/20 14:11.
 * 首页
 */
public class HomePagerActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, HomeMineFragmentAdapter.OnFilterClickListener, View.OnClickListener {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.image_right_message)
    ImageView imageRightMessage;
    @BindView(R.id.view_pager_home)
    ViewPager viewPagerHome;
    @BindView(R.id.view_top)
    View viewTop;   //主要用于popupwindow位置显示
    @BindView(R.id.view_line)
    View viewLine;  //首页导航栏下划线
    private String[] titles = new String[]{"跟进", "今日", "预警", "简报"};
    private List<Fragment> fragmentList;
    private HomeViewPagerAdapter adapter;

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
    private List<CityBean> beanList;
    //下次拜访
    private GridView gridViewNextVisit;
    private FilterNextVisitAdapter adapterNextVisit;
    private List<String> listNextVisit;
    private String[] strNextVisit = {"全部", "今天", "7天内", "两周内", "一个月内"};
    //重置
    private TextView textReset;
    //确定
    private TextView textSure;
    private boolean bLine = false;//用于控制标题栏下划线
    private HomeMyFragment myFragment;
    private int clientType = -1;//客户类型
    private String city = "";//城市
    private String timeRange = "";//下次拜访
    private HomeBriefingFragment briefingFragment;
    private Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepager);
        ButterKnife.bind(this);
        gson = new Gson();
        initView();
        initTab();
        initCity(AppInfoUtils.getUserCity(this));
        initPopupWindow();
    }

    /**
     * 初始化城市信息
     */
    private void initCity(final String data) {
        beanList = new ArrayList<>();
        //获取登录成功保存到本地的城市id
        if (!TextUtils.isEmpty(data)) {
            //默认第一个为 全部 按钮
            beanList.add(new CityBean("01", "全部"));
//            //字符串转化为集合
//            String regex = "^,*|,*$";
//            String string = data.replace("|", ",");
//            String str1 = string.replaceAll(regex, "");
//            final List<String> list = Arrays.asList(str1.split(","));
//            //本地assets城市列表
//            final List<CityListBean> cityListBeans = CityUtils.getAllCity(HomePagerActivity.this);
//            for (int i = 0; i < list.size(); i++) {
//                for (int j = 0; j < cityListBeans.size(); j++) {
//                    if (list.get(i).equals(cityListBeans.get(j).getId())) {
//                        beanList.add(cityListBeans.get(j));
//                    }
//                }
//            }
            //跟本地保存城市信息对比，得到对应城市名称放入集合中
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray jsonArray = new JSONArray(data);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            beanList.add(gson.fromJson(jsonArray.get(i).toString(), CityBean.class));
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

    @Subscribe
    @Override
    public boolean isRegisterEventBus() {
        return true;
    }

    @Subscribe
    @Override
    public void receiveEvent(Event event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case EC.EventCode.UPDATE_CITY_MESSAGE: //登录成功更新城市信息
                requestCityMessage();
                break;
            case EC.EventCode.MAIN_SELECT:
                viewPagerHome.setCurrentItem(0);
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
                                AppInfoUtils.setUserCity(HomePagerActivity.this, jsonObject.optString("data"));
                                if (beanList != null) {
                                    beanList = null;
                                    initCity(jsonObject.optString("data"));
                                }
                                if (adapterCity != null) {
                                    adapterCity.setChangeCityData(beanList);
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
        HomeMineFragmentAdapter.setOnFilterClickListener(this);
        //筛选弹框布局
        View view = LayoutInflater.from(this).inflate(R.layout.filter_client_layout, null);
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
        listClientType = KeyValueUtils.getHomeClientType();
        //下次拜访数据集
        listNextVisit = new ArrayList<>();
        for (int i = 0; i < strNextVisit.length; i++) {
            listNextVisit.add(strNextVisit[i]);
        }
        adapterClientType = new FilterClientTypeAdapter(this, listClientType, 0);
        adapterCity = new FilterCityAdapter(this, beanList, 0);
        adapterNextVisit = new FilterNextVisitAdapter(this, listNextVisit, 0);
        gridViewClientType.setAdapter(adapterClientType);
        gridViewCity.setAdapter(adapterCity);
        gridViewNextVisit.setAdapter(adapterNextVisit);
        //下次拜访点击事件
        gridViewNextVisit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterNextVisit.setSelectPosition(position);
                adapterNextVisit.notifyDataSetChanged();
                if (position == 0) {
                    timeRange = "";
                } else {
                    timeRange = position + "";
                }
            }
        });
        //客户类型点击事件
        gridViewClientType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterClientType.setSelectPosition(position);
                adapterClientType.notifyDataSetChanged();
                clientType = listClientType.get(position).getId();
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
                if (beanList.size() > 6) {
                    if (position == 5) {    //更多按钮点击弹出城市列表对话框
                        DialogUtils.getInstances().showCityMessage(HomePagerActivity.this, beanList,onCityResultListener);
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
            for (int i = 0; i < beanList.size(); i++) {
                if (cityData.equals(beanList.get(i))) {
                    if (i < 5) {
                        adapterCity.changeCityMessage(beanList, i);
                    } else {    //移除集合中指定位置item，并添加到第一个显示
                        beanList.remove(i);
                        beanList.add(1, cityData);
                        adapterCity.changeCityMessage(beanList, 1);
                    }
                    adapterCity.notifyDataSetChanged();
                    break;
                }
            }
        }
    };

    /**
     * 初始化视图
     */
    private void initView() {
        myFragment = new HomeMyFragment();
        briefingFragment = new HomeBriefingFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(myFragment);
        fragmentList.add(new HomeTodayFragment());
        fragmentList.add(new HomeEarlyWarningFragment());
        fragmentList.add(briefingFragment);
        viewPagerHome.setOffscreenPageLimit(fragmentList.size());
        adapter = new HomeViewPagerAdapter(getSupportFragmentManager(), this, fragmentList);
        viewPagerHome.setAdapter(adapter);

        //viewpager滑动监听
        viewPagerHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    if (bLine) {
                        viewLine.setVisibility(View.VISIBLE);
                    } else {
                        viewLine.setVisibility(View.GONE);
                    }
                } else {
                    viewLine.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    /**
     * 初始化tabLayout
     */
    private void initTab() {

        //更改下划线宽度
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                TabLayoutUtils.setIndicator(tabLayout, 30, 30);
            }
        });

        //将tab和Viewpager绑定
        tabLayout.setupWithViewPager(viewPagerHome);

        //关联之后设置tab的名称在这之前要完成Viewpager的实例化不然会报空指针异常
        for (int i = 0; i < titles.length; i++) {
            tabLayout.getTabAt(i).setText(titles[i]).setCustomView(getTabView(i));
        }
        //默认选中第一个
        TabLayoutUtils.updateTabView(tabLayout.getTabAt(0), true);

        //tabLayout监听
        tabLayout.setOnTabSelectedListener(this);


        //标题下划线监听事件
        HomeMyFragment.setOnLineShowHintListener(new HomeMyFragment.OnLineShowHintListener() {
            @Override
            public void onLineShowHint(boolean b) {
                bLine = b;
                if (b) {
                    viewLine.setVisibility(View.VISIBLE);
                } else {
                    viewLine.setVisibility(View.GONE);
                }
            }
        });


    }

    /**
     * 获取view视图
     *
     * @param currentPosition
     * @return
     */
    private View getTabView(int currentPosition) {
        View view = LayoutInflater.from(this).inflate(R.layout.text_style_layout, null);
        TextView textView = (TextView) view.findViewById(R.id.tab_item_textview);
        textView.setText(titles[currentPosition]);
        return view;
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        TabLayoutUtils.updateTabView(tab, true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        TabLayoutUtils.updateTabView(tab, false);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


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
                    clientType = -1;
                }
                if (adapterCity != null) {
                    adapterCity.setSelectPosition(0);
                    adapterCity.notifyDataSetChanged();
                    city = "";
                }
                if (adapterNextVisit != null) {
                    adapterNextVisit.setSelectPosition(0);
                    adapterNextVisit.notifyDataSetChanged();
                    timeRange = "";
                }

                break;
            case R.id.text_sure:    //确定
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                myFragment.filterHttpRequest(clientType, city, timeRange);
                break;
        }
    }

    @OnClick(R.id.image_right_message)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_right_message:  //消息通知
                if (TextUtils.isEmpty(AppInfoUtils.getId(this))) {
                    startActivity(new Intent(HomePagerActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(HomePagerActivity.this, MyMessageActivity.class));
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            new AlertDialog.Builder(this)
                    .setMessage("确定要退出吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AppManager.getInstances().AppExit(HomePagerActivity.this);
                        }
                    })
                    .setNegativeButton("再看看", null).show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
