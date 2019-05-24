package com.tbs.sales.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.adapter.SeperateCitySelectAdapter;
import com.tbs.sales.bean.SeperateCityListBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.LogUtils;
import com.tbs.sales.utils.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Mr.Wang on 2019/5/23 15:28.
 */
public class SeperateCitySelectActivity extends BaseActivity {
    @BindView(R.id.relative_back)
    RelativeLayout relativeBack;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.linear_clear)
    LinearLayout linearClear;
    @BindView(R.id.linear_sure)
    LinearLayout linearSure;
    @BindView(R.id.ll_bottom_btn)
    LinearLayout llBottomBtn;
    private List<SeperateCityListBean.ListBean> beanList;
    private Gson gson;
    private SeperateCitySelectAdapter adapter;
    /**
     * 城市集合
     */
    private List<String> cityList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seperate_city);
        ButterKnife.bind(this);
        gson = new Gson();
        initIntent();
        initData();
        initHttpRequest();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        beanList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }

    /**
     * 初始化网络请求
     */
    private void initHttpRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppInfoUtils.getToekn(this));
        OkHttpUtils.get(Constant.USER_GETUSERCITY, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                LogUtils.logE(json);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String code = jsonObject.optString("code");
                    if (code.equals("0")) {
                        final SeperateCityListBean cityListBean = gson.fromJson(jsonObject.optString("data"), SeperateCityListBean.class);
                        beanList.addAll(cityListBean.getList());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (adapter == null) {
                                    adapter = new SeperateCitySelectAdapter(SeperateCitySelectActivity.this, beanList,cityListBean);
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
     * 初始化intent
     */
    private void initIntent() {
        cityList = Arrays.asList(getIntent().getStringExtra("cityList").split(","));
    }

    @OnClick({R.id.relative_back, R.id.linear_clear, R.id.linear_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_back://返回
                finish();
                break;
            case R.id.linear_clear://清空
                break;
            case R.id.linear_sure://确定
                break;
        }
    }
}