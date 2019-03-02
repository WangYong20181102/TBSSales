package com.tbs.sales.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbs.sales.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        editSearch.addTextChangedListener(this);
    }

    @OnClick({R.id.image_search, R.id.image_clear, R.id.text_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_search: //搜索
                break;
            case R.id.image_clear:  //清理输入框
                editSearch.setText("");
                break;
            case R.id.text_cancel:  //取消
                if (textCancel.getText().toString().trim().equals("取消")){
                    finish();
                    hideSystemKeyBroad();
                }else {

                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s.toString().trim())){
            imageClear.setVisibility(View.VISIBLE);
        }else {
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
