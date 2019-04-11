package com.tbs.sales.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.tbs.sales.R;
import com.tbs.sales.bean.CityBean;
import com.tbs.sales.bean.Event;
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
import com.tbs.sales.widget.AddClientItem1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
public class ClientMessageQueryActivity extends BaseActivity {
    @BindView(R.id.relative_back)
    RelativeLayout relativeBack;
    @BindView(R.id.aci_co_id)
    AddClientItem1 aciCoId;
    @BindView(R.id.aci_city)
    AddClientItem1 aciCity;
    @BindView(R.id.aci_compass_name)
    AddClientItem1 aciCompassName;
    @BindView(R.id.aci_address)
    AddClientItem1 aciAddress;
    @BindView(R.id.aci_yewu)
    AddClientItem1 aciYewu;
    @BindView(R.id.aci_pingtai)
    AddClientItem1 aciPingtai;
    @BindView(R.id.aci_co_name)
    AddClientItem1 aciCoName;
    @BindView(R.id.aci_sex)
    AddClientItem1 aciSex;
    @BindView(R.id.aci_phone)
    AddClientItem1 aciPhone;
    @BindView(R.id.aci_tel_phone)
    AddClientItem1 aciTelPhone;
    @BindView(R.id.aci_wechat_num)
    AddClientItem1 aciWechatNum;
    @BindView(R.id.aci_email_address)
    AddClientItem1 aciEmailAddress;
    private UserInfoDataBean listBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_message_query);
        ButterKnife.bind(this);
        initIntent();
        initData();
    }

    /**
     * 初始化数据
     */
    @SuppressLint("SetTextI18n")
    private void initData() {
        //客户id
        aciCoId.setText(listBean.getCo_id() + "");
        //城市
        aciCity.setText(listBean.getCity_name());
        //公司名称
        aciCompassName.setText(listBean.getCo_name());
        //详细地址
        aciAddress.setText(listBean.getAddress());
        //业务范围
        if (listBean.getServ_range() == 1) {
            aciYewu.setText("全市");
        } else if (listBean.getServ_range() == 2) {
            aciYewu.setText("市区");
        }
        //合作平台
        if (listBean.getHas_serv_plat() == 1) {
            aciPingtai.setText("有");
        } else if (listBean.getHas_serv_plat() == 2) {
            aciPingtai.setText("无");
        }
        //联系人
        aciCoName.setText(listBean.getName());
        //性别
        if (listBean.getSex() == 1) {
            aciSex.setText("男");
        } else if (listBean.getSex() == 2) {
            aciSex.setText("女");
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

    @OnClick({R.id.relative_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_back://返回
                finish();
                break;
        }
    }
}
