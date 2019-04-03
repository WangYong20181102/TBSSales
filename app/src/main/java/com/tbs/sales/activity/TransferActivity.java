package com.tbs.sales.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.bean.PeopleBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.DialogUtils;
import com.tbs.sales.utils.OkHttpUtils;

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
 * Created by Mr.Wang on 2019/4/3 11:22.
 * 转移客户
 */
public class TransferActivity extends BaseActivity {
    @BindView(R.id.relative_back)
    RelativeLayout relativeBack;
    @BindView(R.id.tv_right_give_up_type)
    TextView tvRightGiveUpType;
    @BindView(R.id.tv_old_name)
    TextView tvOldName;
    @BindView(R.id.tv_chose)
    TextView tvChose;
    @BindView(R.id.rl_transfer)
    RelativeLayout rlTransfer;
    @BindView(R.id.btn_sure)
    LinearLayout btnSure;
    private List<PeopleBean> peopleBeanList;
    private Gson gson;
    private String co_name;
    private String grab_desc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
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
        tvRightGiveUpType.setText(co_name);
        tvOldName.setText(grab_desc);
    }

    private void initIntent() {
        co_name = getIntent().getStringExtra("co_name");
        grab_desc = getIntent().getStringExtra("grab_desc");
    }

    /**
     * 初始化网络请求
     */
    private void initHttpRequest() {
        peopleBeanList = new ArrayList<>();
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppInfoUtils.getToekn(this));
        OkHttpUtils.get(Constant.ORGAN_GETTRANSUSER, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String code = jsonObject.optString("code");
                    if (code.equals("0")) {
                        final JSONObject data = jsonObject.optJSONObject("data");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONArray jsonArray = data.getJSONArray("list");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        peopleBeanList.add(gson.fromJson(jsonArray.get(i).toString(), PeopleBean.class));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
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

    @OnClick({R.id.relative_back, R.id.rl_transfer, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_back://返回
                finish();
                break;
            case R.id.rl_transfer:
                DialogUtils.getInstances().showPeopleList(this, peopleBeanList, new DialogUtils.OnPeopleResultListener() {
                    @Override
                    public void onPeopleResult(PeopleBean city) {
                        tvChose.setText(city.getName());
                    }
                });
                break;
            case R.id.btn_sure:
                break;
        }
    }
}
