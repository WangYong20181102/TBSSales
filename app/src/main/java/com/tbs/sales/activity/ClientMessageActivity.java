package com.tbs.sales.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.bean.CityBean;
import com.tbs.sales.bean.HomeDataBean;
import com.tbs.sales.bean.KeyValueDataBean;
import com.tbs.sales.bean.UserInfoDataBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.DialogUtils;
import com.tbs.sales.utils.KeyValueUtils;
import com.tbs.sales.utils.OkHttpUtils;
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
    private List<CityBean> beanList;
    private Gson gson;
    private String city_id;
    private int sex;
    private UserInfoDataBean listBean;
    private List<KeyValueDataBean> strList1;
    private List<KeyValueDataBean> strList2;
    private List<KeyValueDataBean> strList3;

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
        //合作平台
        if (listBean.getHas_serv_plat() == 1) {
            tvCooperationPlatform.setText("有");
        } else if (listBean.getHas_serv_plat() == 2) {
            tvCooperationPlatform.setText("无");
        }
        //联系人
        aciCoName.setText(listBean.getName());
        //性别
        if (listBean.getSex() == 1) {
            textSex.setText("男");
        } else if (listBean.getSex() == 2) {
            textSex.setText("女");
        }
        //座机
        aciPhone.setText(listBean.getTel());
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
            case R.id.relative_back:
                DialogUtils.getInstances().compatDialog(this, "保存", "不保存", "是否保存本次修改?", "", new DialogUtils.onSureListener() {
                    @Override
                    public void onSure() {

                    }
                }, new DialogUtils.onCancleListener() {
                    @Override
                    public void onCancle() {
                        finish();
                    }
                });
                break;
            case R.id.tv_sure:
                DialogUtils.getInstances().compatDialog(this, "确定", "取消", "确定保存?", "", new DialogUtils.onSureListener() {
                    @Override
                    public void onSure() {

                    }
                }, new DialogUtils.onCancleListener() {
                    @Override
                    public void onCancle() {
                    }
                });
                break;
            case R.id.rl_city:
                DialogUtils.getInstances().showCityList(ClientMessageActivity.this, beanList, onCityResultListener);
                break;
            case R.id.rl_business_scope:
                DialogUtils.getInstances().showBottomSelect(this, strList1, new DialogUtils.OnBottomItemSelectListener() {
                    @Override
                    public void onItemSelect(int position) {
                        tvBusinessScope.setText(strList1.get(position).getName());
                    }
                });
                break;
            case R.id.rl_cooperation_platform:
                DialogUtils.getInstances().showBottomSelect(this, strList2, new DialogUtils.OnBottomItemSelectListener() {
                    @Override
                    public void onItemSelect(int position) {
                        tvCooperationPlatform.setText(strList2.get(position).getName());
                    }
                });
                break;
            case R.id.rl_sex:
                DialogUtils.getInstances().showBottomSelect(this, strList3, new DialogUtils.OnBottomItemSelectListener() {
                    @Override
                    public void onItemSelect(int position) {
                        textSex.setText(strList3.get(position).getName());
                    }
                });
                break;
        }
    }

    //城市
    DialogUtils.OnCityResultListener onCityResultListener = new DialogUtils.OnCityResultListener() {
        @Override
        public void onCityResult(CityBean cityData) {
            city_id = cityData.getId();
            tvRightCity.setText(cityData.getName());
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            DialogUtils.getInstances().compatDialog(this, "保存", "不保存", "是否保存本次修改?", "", new DialogUtils.onSureListener() {
                @Override
                public void onSure() {

                }
            }, new DialogUtils.onCancleListener() {
                @Override
                public void onCancle() {
                    finish();
                }
            });
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
