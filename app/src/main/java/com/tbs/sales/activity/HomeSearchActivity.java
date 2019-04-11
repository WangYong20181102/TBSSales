package com.tbs.sales.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.adapter.SearchResultAdapter;
import com.tbs.sales.bean.HomeDataBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.MoveDistanceUtils;
import com.tbs.sales.utils.OkHttpUtils;
import com.tbs.sales.utils.ToastUtils;
import com.tbs.sales.widget.ClearEditText;

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
public class HomeSearchActivity extends BaseActivity implements TextView.OnEditorActionListener, TextWatcher {

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
    private boolean iMove;//屏幕滑动距离
    private String list_type = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        ButterKnife.bind(this);
        gson = new Gson();
        initIntent();
        initView();

    }

    private void initIntent() {
        list_type = getIntent().getStringExtra("list_type");
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
        //搜索结果请求
        searchHttpRequest();

    }

    /**
     * 初始化视图
     */
    private void initView() {
        editSearch.addTextChangedListener(this);//内容改变监听
        editSearch.setOnEditorActionListener(this);//系统软键盘搜索功能
        beanList = new ArrayList<>();
        //recycleView
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOnScrollListener(onScrollListener);
        new MoveDistanceUtils().setOnMoveDistanceListener(recyclerView, new MoveDistanceUtils.OnMoveDistanceListener() {
            @Override
            public void onMoveDistance(boolean b) {
                iMove = b;
            }
        });
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
        searchHttpRequest();
    }

    @OnClick({R.id.image_search, R.id.text_cancel, R.id.image_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_search: //搜索
                if (TextUtils.isEmpty(editSearch.getText().toString().trim())) {
                    ToastUtils.toastShort(this, "请输入搜索内容");
                } else {
                    initData();
                    hideSystemKeyBroad();
                }
                break;
            case R.id.text_cancel:  //取消
                if (textCancel.getText().toString().trim().equals("取消")) {
                    finish();
                    hideSystemKeyBroad();
                }
                break;
            case R.id.image_clear:  //清理输入框
                editSearch.setText("");
                break;
        }
    }

    /**
     * 搜索请求
     */
    private void searchHttpRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("list_type", list_type);
        params.put("page_size", pageSize);
        params.put("co_type", "-1");
        params.put("page", mPage);
        params.put("keywords", editSearch.getText().toString().trim());
        params.put("token", AppInfoUtils.getToekn(this));
        OkHttpUtils.get(Constant.SALE_GETCOMLIST, params, new Callback() {
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
                        final HomeDataBean dataBean = gson.fromJson(jsonObject.optString("data"), HomeDataBean.class);
                        beanList.addAll(dataBean.getList());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setVisibility(View.VISIBLE);
                                linearCanSearch.setVisibility(View.GONE);   //隐藏可以搜索提示
                                if (dataBean.getList().size() != 0) {
                                    if (adapter == null) {
                                        adapter = new SearchResultAdapter(HomeSearchActivity.this, beanList);
                                        recyclerView.setAdapter(adapter);
                                    } else {
                                        adapter.notifyItemInserted(beanList.size() - pageSize + 1);
                                    }
                                } else {//上拉加载无更多信息
                                    if (mPage != 1) {
                                        ToastUtils.toastShort(HomeSearchActivity.this, "暂无更多数据");
                                    }
                                }
                                if (beanList.size() == 0) {
                                    linearNoData.setVisibility(View.VISIBLE);
                                } else {
                                    linearNoData.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.toastShort(HomeSearchActivity.this, jsonObject.optString("message"));
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
     * 隐藏系统键盘
     */
    private void hideSystemKeyBroad() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //系统软键盘搜索功能
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {  //搜索
            if (TextUtils.isEmpty(editSearch.getText().toString().trim())) {
                ToastUtils.toastShort(this, "请输入搜索内容");
                return true;
            } else {
                initData();
                hideSystemKeyBroad();
                return true;
            }
        }
        return false;
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
            linearCanSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
