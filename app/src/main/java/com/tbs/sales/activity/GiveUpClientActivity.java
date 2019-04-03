package com.tbs.sales.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.bean.KeyValueDataBean;
import com.tbs.sales.utils.DialogUtils;
import com.tbs.sales.utils.KeyValueUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr.Wang on 2019/4/1 16:13.
 * 放弃客户
 */
public class GiveUpClientActivity extends BaseActivity implements TextWatcher {
    @BindView(R.id.relative_back)
    RelativeLayout relativeBack;
    @BindView(R.id.tv_give_up_type)
    TextView tvGiveUpType;
    @BindView(R.id.tv_right_give_up_type)
    TextView tvRightGiveUpType;
    @BindView(R.id.image_give_up_type)
    ImageView imageGiveUpType;
    @BindView(R.id.rl_give_up_type)
    RelativeLayout rlGiveUpType;
    @BindView(R.id.tv_client_type)
    TextView tvClientType;
    @BindView(R.id.tv_right_client_type)
    TextView tvRightClientType;
    @BindView(R.id.image_right_side)
    ImageView imageRightSide;
    @BindView(R.id.rl_client_type)
    RelativeLayout rlClientType;
    @BindView(R.id.et_give_up)
    EditText etGiveUp;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.btn_sure)
    LinearLayout btnSure;
    private List<KeyValueDataBean> giveUpList;
    private List<KeyValueDataBean> clientTypeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_up_client);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        giveUpList = KeyValueUtils.getGiveUpType();
        clientTypeList = KeyValueUtils.getClientType();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        etGiveUp.addTextChangedListener(this);
    }

    @OnClick({R.id.relative_back, R.id.rl_give_up_type, R.id.rl_client_type, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_back://返回
                finish();
                break;
            case R.id.rl_give_up_type://放弃类型
                DialogUtils.getInstances().showBottomSelect(this, giveUpList, new DialogUtils.OnBottomItemSelectListener() {
                    @Override
                    public void onItemSelect(int position) {
                        tvRightGiveUpType.setText(giveUpList.get(position).getName());
                    }
                });
                break;
            case R.id.rl_client_type://客户类型
                DialogUtils.getInstances().showBottomSelect(this, clientTypeList, new DialogUtils.OnBottomItemSelectListener() {
                    @Override
                    public void onItemSelect(int position) {
                        tvRightClientType.setText(clientTypeList.get(position).getName());
                    }
                });
                break;
            case R.id.btn_sure:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        tvNum.setText(s.length() + "");
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
