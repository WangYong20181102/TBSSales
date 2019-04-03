package com.tbs.sales.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.adapter.ClientDetailsPagerAdapter;
import com.tbs.sales.bean.KeyValueDataBean;
import com.tbs.sales.bean.UserInfoDataBean;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.fragment.CirculationRecordFragment;
import com.tbs.sales.fragment.FollowUpRecordFragment;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.DialogUtils;
import com.tbs.sales.utils.KeyValueUtils;
import com.tbs.sales.utils.OkHttpUtils;

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

import static com.tbs.sales.utils.TabLayoutUtils.setIndicator;

/**
 * Created by Mr.Wang on 2019/3/28 17:00.
 */
public class ClientDetailsActivity extends BaseActivity {
    @BindView(R.id.relative_back)
    RelativeLayout relativeBack;
    @BindView(R.id.relative_client_message)
    RelativeLayout relativeClientMessage;
    @BindView(R.id.decorate_com_collapsing_toolbar)
    CollapsingToolbarLayout decorateComCollapsingToolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.linear_menu)
    LinearLayout linearMenu;
    @BindView(R.id.image_phone)
    ImageView imagePhone;
    @BindView(R.id.image_follow)
    ImageView imageFollow;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.image_sex)
    ImageView imageSex;
    @BindView(R.id.image_positioning)
    ImageView imagePositioning;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.view_line)
    View viewLine;
    private String[] titles = new String[]{"跟进记录", "流转记录"};
    private List<String> phoneList;
    private List<KeyValueDataBean> dataBeanList;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private ClientDetailsPagerAdapter adapter;
    private int type;
    private int co_id;//客户id
    private UserInfoDataBean dataBean;
    private Gson gson;
    private static final int REQUEST_CALL_PERMISSION = 10111; //拨号请求码
    private String phone = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_detail);
        ButterKnife.bind(this);
        gson = new Gson();
        initIntent();
        initData();
        initTab();
        initHttpRequest();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        phoneList = new ArrayList<>();
    }

    /**
     * 初始化网络请求
     */
    private void initHttpRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppInfoUtils.getToekn(this));
        params.put("co_id", co_id);
        OkHttpUtils.post(Constant.SALE_GETCOMINFO, params, new Callback() {
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
                        dataBean = gson.fromJson(jsonObject.optString("data"), UserInfoDataBean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showUserData(dataBean);
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
     * 展示用户数据
     *
     * @param dataBean
     */
    private void showUserData(UserInfoDataBean dataBean) {
        if (!TextUtils.isEmpty(dataBean.getCallphone().trim())) {   //优先展示callphone
            phoneList.add(dataBean.getPhone().trim());
        } else {
            phoneList.add(dataBean.getContacts().trim());
        }
        dataBeanList = KeyValueUtils.getPhone(phoneList);
        //公司
        tvCompany.setText(dataBean.getCo_name());
        //id
        tvId.setText("ID: " + dataBean.getCo_id());
        //类型（客户类型id,0-新客户、1-潜在（初步接触）、2-持续跟进（归到1）、3-意向（线下跟进）、4-成交、5-无效、6-已签单客户（归到5）、7-暂无意向、8-待签约）
        tvType.setText(dataBean.getCo_type_name());
        //姓名
        tvName.setText(dataBean.getName());
        //地址
        tvAddress.setText(dataBean.getAddress());
        //性别(1-男、2-女)
        switch (dataBean.getSex()) {
            case 1:
                imageSex.setImageResource(R.mipmap.boy);
                break;
            case 2:
                imageSex.setImageResource(R.mipmap.girl);
                break;
        }
        if (type == 1) {
            linearMenu.setVisibility(View.VISIBLE);
            imageFollow.setVisibility(View.VISIBLE);
            imagePhone.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);
        } else if (type == 2) {//综合查询过来
            imageFollow.setVisibility(View.GONE);
            imagePhone.setVisibility(View.GONE);
            viewLine.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(AppInfoUtils.getOrganAreaIds(this).trim())) {
                linearMenu.setVisibility(View.VISIBLE);
            } else {
                linearMenu.setVisibility(View.GONE);
            }
        } else if (type == 3) {//跟进记录界面进入
            if (dataBean.getRespond_uid() != Integer.parseInt(AppInfoUtils.getId(this))) {    //是我的客户
                linearMenu.setVisibility(View.VISIBLE);
                imageFollow.setVisibility(View.VISIBLE);
                imagePhone.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.VISIBLE);
            } else {
                imageFollow.setVisibility(View.GONE);
                imagePhone.setVisibility(View.GONE);
                viewLine.setVisibility(View.GONE);
                linearMenu.setVisibility(View.GONE);
            }
        }
    }


    private void initIntent() {
        type = getIntent().getIntExtra("type", 1);
        co_id = getIntent().getIntExtra("co_id", 0);
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        //更改下划线宽度
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 30, 30);
            }
        });


        fragmentArrayList.add(new FollowUpRecordFragment(co_id));
        fragmentArrayList.add(new CirculationRecordFragment(co_id));
        adapter = new ClientDetailsPagerAdapter(getSupportFragmentManager(), fragmentArrayList);
        viewPager.setAdapter(adapter);
        //将tab和Viewpager绑定
        tabLayout.setupWithViewPager(viewPager);
        //关联之后设置tab的名称在这之前要完成Viewpager的实例化不然会报空指针异常
        for (int i = 0; i < titles.length; i++) {
            tabLayout.getTabAt(i).setText(titles[i]);
        }

    }

    @OnClick({R.id.relative_back, R.id.linear_menu, R.id.image_phone, R.id.image_follow, R.id.relative_client_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_back:    //返回
                finish();
                break;
            case R.id.linear_menu:  //菜单
                DialogUtils.getInstances().showMenuDialog(this, dataBean, type);
                break;
            case R.id.image_phone:  //打电话
                DialogUtils.getInstances().showBottomSelect(this, dataBeanList, new DialogUtils.OnBottomItemSelectListener() {
                    @Override
                    public void onItemSelect(int position) {
                        phone = dataBeanList.get(position).getName();
                        call(phone);
                    }
                });
                break;
            case R.id.image_follow: //客户跟进
                Intent intent = new Intent(this, ClientEditActivity.class);
                intent.putExtra(UserInfoDataBean.class.getName(), dataBean);
                startActivity(intent);
                break;
            case R.id.relative_client_message://客户信息
                Intent intent1 = new Intent(this, ClientMessageActivity.class);
                intent1.putExtra(UserInfoDataBean.class.getName(), dataBean);
                startActivity(intent1);
                break;
        }
    }

    /**
     * 检查权限后的回调
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION: //拨打电话
                if (permissions.length != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {//失败
                    Toast.makeText(this, "请允许拨号权限后再试", Toast.LENGTH_SHORT).show();
                } else {//成功
                    call(phone);
                }
                break;
        }
    }

    /**
     * 拨打电话（直接拨打）
     *
     * @param telPhone 电话
     */
    public void call(String telPhone) {
        if (checkReadPermission(Manifest.permission.CALL_PHONE, REQUEST_CALL_PERMISSION)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(telPhone));
            startActivity(intent);
        }

    }

    /**
     * 判断是否有某项权限
     *
     * @param string_permission 权限
     * @param request_code      请求码
     * @return
     */
    public boolean checkReadPermission(String string_permission, int request_code) {
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(this, string_permission) == PackageManager.PERMISSION_GRANTED) {//已有权限
            flag = true;
        } else {//申请权限
            ActivityCompat.requestPermissions(this, new String[]{string_permission}, request_code);
        }
        return flag;
    }
}
