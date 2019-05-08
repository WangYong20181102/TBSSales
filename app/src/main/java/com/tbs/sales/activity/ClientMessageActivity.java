package com.tbs.sales.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.bean.CityBean;
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
import com.tbs.sales.utils.StringUtils;
import com.tbs.sales.utils.ToastUtils;
import com.tbs.sales.widget.AddClientItem;

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
 * Created by Mr.Wang on 2019/4/1 11:33.
 * 客户信息
 */
public class ClientMessageActivity extends BaseActivity {
    @BindView(R.id.relative_back)
    RelativeLayout relativeBack;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.tv_co_id)
    TextView tvCoId;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_right_city)
    TextView tvRightCity;
    @BindView(R.id.image_right_side_city)
    ImageView imageRightSideCity;
    @BindView(R.id.rl_city)
    RelativeLayout rlCity;
    @BindView(R.id.aci_compass_name)
    AddClientItem aciCompassName;
    @BindView(R.id.aci_address)
    AddClientItem aciAddress;
    @BindView(R.id.tv_business_scope)
    TextView tvBusinessScope;
    @BindView(R.id.image_right_business_scope)
    ImageView imageRightBusinessScope;
    @BindView(R.id.rl_business_scope)
    RelativeLayout rlBusinessScope;
    @BindView(R.id.tv_cooperation_platform)
    TextView tvCooperationPlatform;
    @BindView(R.id.image_right_cooperation_platform)
    ImageView imageRightCooperationPlatform;
    @BindView(R.id.rl_cooperation_platform)
    RelativeLayout rlCooperationPlatform;
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
    @BindView(R.id.aci_phone)
    AddClientItem aciPhone;
    @BindView(R.id.aci_tel_phone)
    AddClientItem aciTelPhone;
    @BindView(R.id.aci_wechat_num)
    AddClientItem aciWechatNum;
    @BindView(R.id.aci_email_address)
    AddClientItem aciEmailAddress;
    /**
     * 城市数据集
     */
    private List<CityBean> beanList;
    private Gson gson;
    private UserInfoDataBean listBean;
    /**
     * 业务范围
     */
    private List<KeyValueDataBean> strList1;
    private int position1;
    /**
     * 合作平台
     */
    private List<KeyValueDataBean> strList2;
    private int position2;
    /**
     * 性别
     */
    private List<KeyValueDataBean> strList3;
    private int position3;
    /**
     * 省份id
     */
    private int province;
    /**
     * 城市id
     */
    private String city_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_message);
        ButterKnife.bind(this);
        gson = new Gson();
        initIntent();
        initData();
        initCity(AppInfoUtils.getUserCity(this));
    }

    /**
     * 初始化数据
     */
    @SuppressLint("SetTextI18n")
    private void initData() {
        strList1 = KeyValueUtils.getYeWuFanWei();
        strList2 = KeyValueUtils.getHeZuoPingTai();
        strList3 = KeyValueUtils.getSex();
        //客户id
        tvCoId.setText(listBean.getCo_id() + "");
        //城市
        tvRightCity.setText(listBean.getCity_name());
        //公司名称
        aciCompassName.setText(listBean.getCo_name());
        //详细地址
        aciAddress.setText(listBean.getAddress());
        //业务范围
        if (listBean.getServ_range() == 1) {
            tvBusinessScope.setText("全市");
        } else if (listBean.getServ_range() == 2) {
            tvBusinessScope.setText("市区");
        }
        position1 = listBean.getServ_range();
        //合作平台
        if (listBean.getHas_serv_plat() == 1) {
            tvCooperationPlatform.setText("有");
        } else if (listBean.getHas_serv_plat() == 2) {
            tvCooperationPlatform.setText("无");
        }
        position2 = listBean.getHas_serv_plat();
        //联系人
        aciCoName.setText(listBean.getName());
        //性别
        if (listBean.getSex() == 1) {
            textSex.setText("男");
        } else if (listBean.getSex() == 2) {
            textSex.setText("女");
        }
        position3 = listBean.getSex();
        //座机
        aciPhone.setText(listBean.getTel());
        /**
         * 设置座机格式，加横线
         */
        aciPhone.setPhoneType();
        //手机
        aciTelPhone.setText(listBean.getPhone());
        //微信
        aciWechatNum.setText(listBean.getWeixin());
        //邮箱
        aciEmailAddress.setText(listBean.getEmail());
    }

    /**
     * 初始化intent
     */
    private void initIntent() {
        listBean = (UserInfoDataBean) getIntent().getSerializableExtra(UserInfoDataBean.class.getName());
        province = listBean.getProvince();
        city_id = listBean.getCity() + "";
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
                                AppInfoUtils.setUserCity(ClientMessageActivity.this, jsonObject.optString("data"));
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

    @OnClick({R.id.relative_back, R.id.tv_sure, R.id.rl_city, R.id.rl_business_scope, R.id.rl_cooperation_platform, R.id.rl_sex})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_back://返回
                if (editTextChange()) {//判断内容有无改动
                    DialogUtils.getInstances().compatDialog(this, "保存", "不保存", "是否保存本次修改?", "", new DialogUtils.onSureListener() {
                        @Override
                        public void onSure() {
                            if (judgeIsNull()) {//判断必填字段是否有空值
                                httpRequest();
                            }
                        }
                    }, new DialogUtils.onCancleListener() {
                        @Override
                        public void onCancle() {
                            finish();
                        }
                    });
                } else {
                    finish();
                }
                break;
            case R.id.tv_sure:
                if (judgeIsNull()) {//判断必填字段是否有空值
                    DialogUtils.getInstances().compatDialog(this, "确定", "取消", "确定保存?", "", new DialogUtils.onSureListener() {
                        @Override
                        public void onSure() {
                            httpRequest();
                        }
                    }, new DialogUtils.onCancleListener() {
                        @Override
                        public void onCancle() {
                        }
                    });
                }
                break;
            case R.id.rl_city://城市列表
                DialogUtils.getInstances().showCityList(ClientMessageActivity.this, beanList, onCityResultListener,DialogUtils.NO_MARGIN);
                break;
            case R.id.rl_business_scope://业务范围
                DialogUtils.getInstances().showBottomSelect(this, strList1, new DialogUtils.OnBottomItemSelectListener() {
                    @Override
                    public void onItemSelect(int position) {
                        position1 = position + 1;
                        tvBusinessScope.setText(strList1.get(position).getName());
                    }
                });
                break;
            case R.id.rl_cooperation_platform://合作平台
                DialogUtils.getInstances().showBottomSelect(this, strList2, new DialogUtils.OnBottomItemSelectListener() {
                    @Override
                    public void onItemSelect(int position) {
                        position2 = position + 1;
                        tvCooperationPlatform.setText(strList2.get(position).getName());
                    }
                });
                break;
            case R.id.rl_sex://性别
                DialogUtils.getInstances().showBottomSelect(this, strList3, new DialogUtils.OnBottomItemSelectListener() {
                    @Override
                    public void onItemSelect(int position) {
                        position3 = position + 1;
                        textSex.setText(strList3.get(position).getName());
                    }
                });
                break;
        }
    }

    /**
     * 判断必填字段是否为空
     */
    private boolean judgeIsNull() {
        if (TextUtils.isEmpty(tvRightCity.getText())) {
            ToastUtils.toastShort(this, "请完善城市");
            return false;
        }
        if (TextUtils.isEmpty(aciCompassName.getText())) {
            ToastUtils.toastShort(this, "请完善公司名称");
            return false;
        }
        if (TextUtils.isEmpty(aciAddress.getText())) {
            ToastUtils.toastShort(this, "请完善详细地址");
            return false;
        }
        if (TextUtils.isEmpty(aciCoName.getText())) {
            ToastUtils.toastShort(this, "请完善联系人");
            return false;
        }
        if (TextUtils.isEmpty(textSex.getText().toString().trim())) {
            ToastUtils.toastShort(this, "请完善性别");
            return false;
        }
        return true;
    }

    /**
     * 网络请求
     */
    private void httpRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppInfoUtils.getToekn(this));
        params.put("inlet", "private");
        params.put("co_id", tvCoId.getText().toString());
        params.put("city", city_id);
        params.put("province", province);
        params.put("co_name", aciCompassName.getText());
        params.put("address", aciAddress.getText());
        params.put("serv_range", position1);
        params.put("has_serv_plat", position2);
        params.put("name", aciCoName.getText());
        params.put("sex", position3);
        params.put("tel", aciPhone.getText());
        params.put("phone", aciTelPhone.getText());
        params.put("weixin", aciWechatNum.getText());
        params.put("email", aciEmailAddress.getText());
        OkHttpUtils.post(Constant.SALE_EDITCOM, params, new Callback() {
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
                                ToastUtils.toastShort(ClientMessageActivity.this, jsonObject.optString("message"));
                                EventBusUtil.sendEvent(new Event(EC.EventCode.UPDATE_CLIENT_DETAIL));//更新客户详情
                                EventBusUtil.sendEvent(new Event(EC.EventCode.UPDATE_HOME_DATA));   //更新首页数据
                                EventBusUtil.sendEvent(new Event(EC.EventCode.UPDATE_MINE_CLIENT));   //我的客户
                                finish();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.toastShort(ClientMessageActivity.this, jsonObject.optString("message"));
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
            province = cityData.getProvince_id();
            city_id = cityData.getId();
            tvRightCity.setText(cityData.getName());
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (editTextChange()) {

                DialogUtils.getInstances().compatDialog(this, "保存", "不保存", "是否保存本次修改?", "", new DialogUtils.onSureListener() {
                    @Override
                    public void onSure() {
                        if (judgeIsNull()) {
                            httpRequest();
                        }
                    }
                }, new DialogUtils.onCancleListener() {
                    @Override
                    public void onCancle() {
                        finish();
                    }
                });
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 判断输入框内容是否有更改
     *
     * @return
     */
    private boolean editTextChange() {
        if (!tvRightCity.getText().equals(listBean.getCity_name()) || !aciCompassName.getText().equals(listBean.getCo_name())
                || !aciAddress.getText().equals(listBean.getAddress()) || position1 != listBean.getServ_range() || position2 != listBean.getHas_serv_plat()
                || !aciCoName.getText().equals(listBean.getName()) || position3 != listBean.getSex() || !aciPhone.getText().equals(listBean.getTel()) ||
                !aciTelPhone.getText().equals(listBean.getPhone()) || !aciWechatNum.getText().equals(listBean.getWeixin()) || !aciEmailAddress.getText().equals(listBean.getEmail())) {
            return true;
        } else {
            return false;
        }
    }
}
