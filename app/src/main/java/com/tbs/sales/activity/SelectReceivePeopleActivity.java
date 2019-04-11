package com.tbs.sales.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.adapter.PeopleChoseAdapter;
import com.tbs.sales.bean.Event;
import com.tbs.sales.bean.PeopleBean;
import com.tbs.sales.bean.UserInfoDataBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.EC;
import com.tbs.sales.utils.KeyBoardUtil;
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
 * Created by Mr.Wang on 2019/4/10 17:23.
 * 选取接收人
 */
public class SelectReceivePeopleActivity extends BaseActivity implements TextWatcher {
    @BindView(R.id.linear_back)
    LinearLayout linearBack;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.edit_people_message)
    EditText editPeopleMessage;
    @BindView(R.id.image_delete)
    ImageView imageDelete;
    private PeopleChoseAdapter choseAdapter;
    /**
     * 原数据
     */
    private List<PeopleBean> peopleList;
    private Gson gson;
    private UserInfoDataBean dataBean;
    /**
     * 搜索结果集
     */
    private List<PeopleBean> peopleListResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_receive_people);
        ButterKnife.bind(this);
        gson = new Gson();
        initIntent();
        initData();
        httpRequest();
    }

    /**
     * 上个界面带过来数据
     */
    private void initIntent() {
        dataBean = (UserInfoDataBean) getIntent().getSerializableExtra(UserInfoDataBean.class.getName());
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
            case EC.EventCode.CLOSE_DETAIL://关闭选择领取人界面
                finish();
                break;
        }
    }

    /**
     * 领取人列表请求
     */
    private void httpRequest() {
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
                                        peopleList.add(gson.fromJson(jsonArray.get(i).toString(), PeopleBean.class));
                                    }
                                    if (choseAdapter == null) {
                                        choseAdapter = new PeopleChoseAdapter(SelectReceivePeopleActivity.this, peopleList);
                                        choseAdapter.setListener(filterListener);
                                        listView.setAdapter(choseAdapter);
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

    private PeopleChoseAdapter.FilterListener filterListener = new PeopleChoseAdapter.FilterListener() {
        @Override
        public void getFilterData(List<PeopleBean> list) {
            peopleListResult = list;
        }
    };

    /**
     * 初始化数据
     */
    private void initData() {
        peopleList = new ArrayList<>();
        peopleListResult = new ArrayList<>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (peopleListResult.size() != 0) {//搜索结果集合
                    if (peopleListResult.get(position).getCan_grab() == 0) {
                        ToastUtils.toastShort(SelectReceivePeopleActivity.this, "客户数已满额");
                    } else {
                        Intent intent = new Intent(SelectReceivePeopleActivity.this,TransferActivity.class);
                        intent.putExtra(UserInfoDataBean.class.getName(),dataBean);
                        intent.putExtra(PeopleBean.class.getName(),peopleListResult.get(position));
                        startActivity(intent);
                        KeyBoardUtil.hideKeyBoard(SelectReceivePeopleActivity.this);
                    }
                } else {//不是搜索结果结合
                    if (peopleList.get(position).getCan_grab() == 0) {
                        ToastUtils.toastShort(SelectReceivePeopleActivity.this, "客户数已满额");
                    } else {
                        Intent intent = new Intent(SelectReceivePeopleActivity.this,TransferActivity.class);
                        intent.putExtra(UserInfoDataBean.class.getName(),dataBean);
                        intent.putExtra(PeopleBean.class.getName(),peopleList.get(position));
                        startActivity(intent);
                    }
                }
            }
        });
        editPeopleMessage.addTextChangedListener(this);
    }

    @OnClick({R.id.linear_back, R.id.image_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linear_back://返回
                finish();
                break;
            case R.id.image_delete:
                editPeopleMessage.setText("");
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s.toString().trim())) {
            imageDelete.setVisibility(View.VISIBLE);
        } else {
            imageDelete.setVisibility(View.GONE);
        }
        if (choseAdapter != null) {
            choseAdapter.getFilter().filter(s);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
