package com.tbs.sales.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.bean.CityBean;
import com.tbs.sales.bean.Event;
import com.tbs.sales.bean.KeyValueDataBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.DialogUtils;
import com.tbs.sales.utils.EC;
import com.tbs.sales.utils.EventBusUtil;
import com.tbs.sales.utils.KeyValueUtils;
import com.tbs.sales.utils.OkHttpUtils;
import com.tbs.sales.utils.ToastUtils;
import com.tbs.sales.widget.AddClientItem;

import org.json.JSONArray;
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
 * Created by Mr.Wang on 2019/3/30 16:24.
 * 添加客户
 */
public class AddClientActivity extends BaseActivity{
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.aci_compass_name)
    AddClientItem aciCompassName;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_right_city)
    TextView tvRightCity;
    @BindView(R.id.image_right_side_city)
    ImageView imageRightSideCity;
    @BindView(R.id.rl_city)
    RelativeLayout rlCity;
    @BindView(R.id.aci_address)
    AddClientItem aciAddress;
    @BindView(R.id.aci_co_name)
    AddClientItem aciCoName;
    @BindView(R.id.tv_left_sex)
    TextView tvLeftSex;
    @BindView(R.id.text_sex)
    TextView textSex;
    @BindView(R.id.image_right_side_sex)
    ImageView imageRightSideSex;
    @BindView(R.id.rl_sex)
    RelativeLayout rlSex;
    @BindView(R.id.aci_tel_phone)
    AddClientItem aciTelPhone;
    @BindView(R.id.aci_phone)
    AddClientItem aciPhone;
    @BindView(R.id.aci_wechat_num)
    AddClientItem aciWechatNum;
    @BindView(R.id.aci_email_address)
    AddClientItem aciEmailAddress;
    private List<CityBean> beanList;
    private Gson gson;
    private String city_id;
    private int sex;
    private List<KeyValueDataBean> dataBeanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        ButterKnife.bind(this);
        gson = new Gson();
        initData();
        initCity(AppInfoUtils.getUserCity(this));
    }

    /**
     * 初始化数据
     */
    private void initData() {
        dataBeanList = KeyValueUtils.getSex();
        aciPhone.setPhone();
    }

    /**
     * 初始化城市信息
     */
    private void initCity(final String data) {
        beanList = new ArrayList<>();
        //获取登录成功保存到本地的城市id
        if (!TextUtils.isEmpty(data)) {
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
                                AppInfoUtils.setUserCity(AddClientActivity.this, jsonObject.optString("data"));
                                if (beanList != null) {
                                    beanList = null;
                                    initCity(jsonObject.optString("data"));
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

    @OnClick({R.id.tv_cancle, R.id.tv_sure, R.id.rl_city, R.id.rl_sex})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:    //取消
                finish();
                break;
            case R.id.tv_sure:
                if (TextUtils.isEmpty(aciCompassName.getText())) {
                    ToastUtils.toastShort(this, "请完善公司名称");
                    return;
                }
                if (TextUtils.isEmpty(tvRightCity.getText().toString().trim())) {
                    ToastUtils.toastShort(this, "请完善城市");
                    return;
                }
                if (TextUtils.isEmpty(aciAddress.getText())) {
                    ToastUtils.toastShort(this, "请完善详细地址");
                    return;
                }
                if (TextUtils.isEmpty(aciCoName.getText())) {
                    ToastUtils.toastShort(this, "请完善联系人");
                    return;
                }
                if (TextUtils.isEmpty(textSex.getText().toString().trim())) {
                    ToastUtils.toastShort(this, "请完善性别");
                    return;
                }
                if (TextUtils.isEmpty(aciTelPhone.getText()) && TextUtils.isEmpty(aciPhone.getText())) {
                    ToastUtils.toastShort(this, "请完善手机或者座机号码");
                    return;
                }

                requestAddClient();
                break;
            case R.id.rl_city:
                DialogUtils.getInstances().showCityList(AddClientActivity.this, beanList, onCityResultListener);
                break;
            case R.id.rl_sex://性别
                DialogUtils.getInstances().showBottomSelect(this, dataBeanList, new DialogUtils.OnBottomItemSelectListener() {
                    @Override
                    public void onItemSelect(int position) {
                        textSex.setText(dataBeanList.get(position).getName());
                        sex = dataBeanList.get(position).getId();
                    }
                });
                break;
        }
    }

    /**
     * 请求添加客户
     */
    private void requestAddClient() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("inlet", "private");
        params.put("token", AppInfoUtils.getToekn(this));
        params.put("co_name", aciCompassName.getText());
        params.put("phone", aciTelPhone.getText());
        params.put("tel", aciPhone.getText());
        params.put("name", aciCoName.getText());
        params.put("sex", sex);
        params.put("city", city_id);
        params.put("address", aciAddress.getText());
        params.put("weixin", aciWechatNum.getText());
        params.put("email", aciEmailAddress.getText());
        OkHttpUtils.post(Constant.SALE_ADDCOM, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String code = jsonObject.optString("code");
                    final String message = jsonObject.optString("message");
                    if (code.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.toastShort(AddClientActivity.this, message);
                                EventBusUtil.sendEvent(new Event(EC.EventCode.UPDATE_HOME_DATA));   //更新首页数据
                                EventBusUtil.sendEvent(new Event(EC.EventCode.UPDATE_MINE_CLIENT));   //我的客户
                                finish();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.toastShort(AddClientActivity.this, message);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //城市
    DialogUtils.OnCityResultListener onCityResultListener = new DialogUtils.OnCityResultListener() {
        @Override
        public void onCityResult(CityBean cityData) {
            city_id = cityData.getId();
            tvRightCity.setText(cityData.getName());
        }
    };
}
