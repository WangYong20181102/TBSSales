package com.tbs.sales;

import android.Manifest;
import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.tbs.sales.activity.ApplicationActivity;
import com.tbs.sales.activity.ClientActivity;
import com.tbs.sales.activity.HomePagerActivity;
import com.tbs.sales.activity.MineActivity;
import com.tbs.sales.activity.WebViewActivity;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends TabActivity {

    @BindView(R.id.image_home)
    ImageView imageHome;
    @BindView(R.id.text_home)
    TextView textHome;
    @BindView(R.id.relative_home)
    RelativeLayout relativeHome;
    @BindView(R.id.image_client)
    ImageView imageClient;
    @BindView(R.id.text_client)
    TextView textClient;
    @BindView(R.id.relative_client)
    RelativeLayout relativeClient;
    @BindView(R.id.image_application)
    ImageView imageApplication;
    @BindView(R.id.text_application)
    TextView textApplication;
    @BindView(R.id.relative_application)
    RelativeLayout relativeApplication;
    @BindView(R.id.image_mine)
    ImageView imageMine;
    @BindView(R.id.text_mine)
    TextView textMine;
    @BindView(R.id.relative_mine)
    RelativeLayout relativeMine;
    @BindView(R.id.relative_add)
    RelativeLayout relativeAdd;
    private static TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (!AppInfoUtils.getFirstLaunch(this)){
            AppInfoUtils.setFirstLaunch(this,true);
            //获取存储权限
            writerReadSDcard();
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        tabHost = this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent = new Intent().setClass(this, HomePagerActivity.class);
        //首页
        spec = tabHost.newTabSpec("ONE").setIndicator("首页").setContent(intent);
        tabHost.addTab(spec);
        //客户
        intent = new Intent().setClass(this, ClientActivity.class);
        spec = tabHost.newTabSpec("TWO").setIndicator("客户").setContent(intent);
        tabHost.addTab(spec);
        //应用
        intent = new Intent().setClass(this, ApplicationActivity.class);
        spec = tabHost.newTabSpec("FOUR").setIndicator("应用").setContent(intent);
        tabHost.addTab(spec);
        //我的
        intent = new Intent().setClass(this, MineActivity.class);
        spec = tabHost.newTabSpec("FIVE").setIndicator("我的").setContent(intent);
        tabHost.addTab(spec);

        setActivityPosition(0);

    }

    @OnClick({R.id.relative_home, R.id.relative_client, R.id.relative_application, R.id.relative_mine, R.id.relative_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_home:    //首页
                tabHost.setCurrentTabByTag("ONE");
                setActivityPosition(0);
                break;
            case R.id.relative_client:  //客户
                tabHost.setCurrentTabByTag("TWO");
                setActivityPosition(1);
                break;
            case R.id.relative_application: //应用
                tabHost.setCurrentTabByTag("FOUR");
                setActivityPosition(3);
                break;
            case R.id.relative_mine:    //我的
                tabHost.setCurrentTabByTag("FIVE");
                setActivityPosition(4);
                break;
            case R.id.relative_add: //添加
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_CUSTOMER_ADD);
                startActivity(intent);
                break;
        }
    }

    private void setActivityPosition(int activityPosition) {
        switch (activityPosition) {
            case 0:
                tabHost.setCurrentTab(activityPosition);
                tabHost.setCurrentTabByTag("ONE");
                imageHome.setBackgroundResource(R.mipmap.common_homepage_select);
                imageClient.setBackgroundResource(R.mipmap.common_client);
                imageApplication.setBackgroundResource(R.mipmap.common_application);
                imageMine.setBackgroundResource(R.mipmap.common_mine);

                textHome.setTextColor(Color.parseColor("#107BFD"));
                textClient.setTextColor(Color.parseColor("#000000"));
                textApplication.setTextColor(Color.parseColor("#000000"));
                textMine.setTextColor(Color.parseColor("#000000"));

                break;
            case 1:
                tabHost.setCurrentTab(activityPosition);
                tabHost.setCurrentTabByTag("TWO");
                imageHome.setBackgroundResource(R.mipmap.common_homepage);
                imageClient.setBackgroundResource(R.mipmap.common_client_select);
                imageApplication.setBackgroundResource(R.mipmap.common_application);
                imageMine.setBackgroundResource(R.mipmap.common_mine);

                textHome.setTextColor(Color.parseColor("#000000"));
                textClient.setTextColor(Color.parseColor("#107BFD"));
                textApplication.setTextColor(Color.parseColor("#000000"));
                textMine.setTextColor(Color.parseColor("#000000"));

                break;
            case 3:
                tabHost.setCurrentTab(activityPosition);
                tabHost.setCurrentTabByTag("FOUR");
                imageHome.setBackgroundResource(R.mipmap.common_homepage);
                imageClient.setBackgroundResource(R.mipmap.common_client);
                imageApplication.setBackgroundResource(R.mipmap.common_application_select);
                imageMine.setBackgroundResource(R.mipmap.common_mine);

                textHome.setTextColor(Color.parseColor("#000000"));
                textClient.setTextColor(Color.parseColor("#000000"));
                textApplication.setTextColor(Color.parseColor("#107BFD"));
                textMine.setTextColor(Color.parseColor("#000000"));

                break;
            case 4:
                tabHost.setCurrentTab(activityPosition);
                tabHost.setCurrentTabByTag("FIVE");
                imageHome.setBackgroundResource(R.mipmap.common_homepage);
                imageClient.setBackgroundResource(R.mipmap.common_client);
                imageApplication.setBackgroundResource(R.mipmap.common_application);
                imageMine.setBackgroundResource(R.mipmap.common_mine_select);

                textHome.setTextColor(Color.parseColor("#000000"));
                textClient.setTextColor(Color.parseColor("#000000"));
                textApplication.setTextColor(Color.parseColor("#000000"));
                textMine.setTextColor(Color.parseColor("#107BFD"));

                break;
        }
    }

    //    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            /**
//             * 退出应用
//             */
//            AppManager.getInstances().AppExit(MainActivity.this);
//        }
//        return false;
//    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

//            CustomDialog.Builder builder = new CustomDialog.Builder(this);
//            builder.setMessage("你确定退出吗？")
//                    .setPositiveButton("确定",
//                            new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                    //关闭时上传数据
//                                    Util.HttpPostUserUseInfo();
//                                    finish();
//                                    System.exit(0);//                                }
//                            })
//                    .setNegativeButton("再看看",
//                            new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialog,
//                                                    int id) {
//                                    dialog.cancel();
//                                }
//                            });
//            builder.create().show();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 动态获取6.0版本以上手机存储权限
     */
    public void writerReadSDcard() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //权限还没有授予，需要在这里写申请权限的代码
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            }
        }
    }
}
