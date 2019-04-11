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
import com.tbs.sales.bean.Event;
import com.tbs.sales.bean.KeyValueDataBean;
import com.tbs.sales.bean.UserInfoDataBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.DialogUtils;
import com.tbs.sales.utils.EC;
import com.tbs.sales.utils.EventBusUtil;
import com.tbs.sales.utils.KeyValueUtils;
import com.tbs.sales.utils.OkHttpUtils;
import com.tbs.sales.utils.ToastUtils;

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
    private UserInfoDataBean dataBean;
    private int co_type = 0;//客户类型
    private int cancel_type = 0;//放弃类型

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_up_client);
        ButterKnife.bind(this);
        initIntent();
        initView();
        initData();
    }

    private void initIntent() {
        dataBean = (UserInfoDataBean) getIntent().getSerializableExtra(UserInfoDataBean.class.getName());
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
                        cancel_type = giveUpList.get(position).getId();
                        tvRightGiveUpType.setText(giveUpList.get(position).getName());
                    }
                });
                break;
            case R.id.rl_client_type://客户类型
                DialogUtils.getInstances().showBottomSelect(this, clientTypeList, new DialogUtils.OnBottomItemSelectListener() {
                    @Override
                    public void onItemSelect(int position) {
                        co_type = giveUpList.get(position).getId();
                        tvRightClientType.setText(clientTypeList.get(position).getName());
                    }
                });
                break;
            case R.id.btn_sure:
                if (cancel_type == 0) {
                    ToastUtils.toastShort(this, "请填写放弃类型");
                    return;
                }
                if (co_type == 0) {
                    ToastUtils.toastShort(this, "请填写客户类型");
                    return;
                }
                if (cancel_type == 9) {
                    if (TextUtils.isEmpty(etGiveUp.getText().toString().trim())) {
                        ToastUtils.toastShort(this, "请填写放弃说明");
                        return;
                    }
                }
                httpRequest();
                break;
        }
    }

    /**
     * 网络请求
     */
    private void httpRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppInfoUtils.getToekn(this));
        params.put("ids", dataBean.getCo_id());
        params.put("co_type", co_type);
        params.put("cancel_type", cancel_type);
        params.put("desc", etGiveUp.getText().toString().trim());
        OkHttpUtils.post(Constant.GRAB_CANCELORDER, params, new Callback() {
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
                                ToastUtils.toastShort(GiveUpClientActivity.this, jsonObject.optString("message"));
                                EventBusUtil.sendEvent(new Event(EC.EventCode.CLOSE_DETAIL));//关闭客户详情页
                                EventBusUtil.sendEvent(new Event(EC.EventCode.UPDATE_HOME_DATA));   //更新首页数据
                                EventBusUtil.sendEvent(new Event(EC.EventCode.UPDATE_MINE_CLIENT));   //我的客户
                                finish();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.toastShort(GiveUpClientActivity.this, jsonObject.optString("message"));
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
        tvNum.setText(s.length() + "");
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
