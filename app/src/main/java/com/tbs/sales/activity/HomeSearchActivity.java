package com.tbs.sales.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.adapter.HomeEarlyWarningFragmentAdapter;
import com.tbs.sales.adapter.HomeTodayFragmentAdapter;
import com.tbs.sales.adapter.SearchResultAdapter;
import com.tbs.sales.bean.HomeDataBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.OkHttpUtils;

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
 * Created by Mr.Wang on 2019/2/27 15:55.
 */
public class HomeSearchActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.image_search)
    ImageView imageSearch;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.image_clear)
    ImageView imageClear;
    @BindView(R.id.text_cancel)
    TextView textCancel;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.linear_no_data)
    LinearLayout linearNoData;
    @BindView(R.id.text_user_name)
    TextView textUserName;
    @BindView(R.id.linear_can_search)
    LinearLayout linearCanSearch;
    private LinearLayoutManager layoutManager;
    private SearchResultAdapter adapter;
    private Gson gson;
    private int mPage = 0;
    private int pageSize = 20;
    private List<HomeDataBean.ListBean> beanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        ButterKnife.bind(this);
        gson = new Gson();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mPage = 1;
        if (adapter != null) {
            adapter = null;
        }
        if (!beanList.isEmpty()) {
            beanList.clear();
        }
        serachHttpRequest();

    }

    /**
     * 初始化视图
     */
    private void initView() {
        editSearch.addTextChangedListener(this);
        beanList = new ArrayList<>();
        //recycleView
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOnScrollListener(onScrollListener);
    }

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
        serachHttpRequest();
    }

    @OnClick({R.id.image_search, R.id.image_clear, R.id.text_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_search: //搜索
                if (TextUtils.isEmpty(editSearch.getText().toString().trim())){
                    Toast.makeText(this,"请输入搜索内容",Toast.LENGTH_SHORT).show();
                }else {
                    initData();
                    hideSystemKeyBroad();
                }
                break;
            case R.id.image_clear:  //清理输入框
                editSearch.setText("");
                break;
            case R.id.text_cancel:  //取消
                if (textCancel.getText().toString().trim().equals("取消")) {
                    finish();
                    hideSystemKeyBroad();
                } else {

                }
                break;
        }
    }

    /**
     * 搜索网络请求
     */
    private void serachHttpRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("plat", "mm");
        params.put("list_type", "all");
        params.put("page_size", pageSize);
        params.put("co_type", "-1");
        params.put("page", mPage);
        params.put("keywords_type", "1");
        params.put("keywords", editSearch.getText().toString().trim());
        params.put("token", AppInfoUtils.getToekn(this));
        params.put("device", "h5");
        OkHttpUtils.post(Constant.SALE_GETCOMLIST, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setVisibility(View.VISIBLE);
                                linearCanSearch.setVisibility(View.GONE);
                                if (adapter == null) {
                                    adapter = new SearchResultAdapter(HomeSearchActivity.this, beanList);
                                    recyclerView.setAdapter(adapter);
                                } else {
                                    adapter.notifyItemInserted(beanList.size() - pageSize);
                                }
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(HomeSearchActivity.this, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s.toString().trim())) {
            imageClear.setVisibility(View.VISIBLE);
        } else {
            imageClear.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 隐藏系统键盘
     */
    private void hideSystemKeyBroad() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
