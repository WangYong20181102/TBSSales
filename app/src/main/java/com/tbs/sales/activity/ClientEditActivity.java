package com.tbs.sales.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.tbs.sales.R;
import com.tbs.sales.adapter.ClientTypeAdapter;
import com.tbs.sales.bean.KeyValueDataBean;
import com.tbs.sales.bean.UserInfoDataBean;
import com.tbs.sales.utils.DialogUtils;
import com.tbs.sales.utils.KeyValueUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr.Wang on 2019/4/1 14:51.
 * 客户详情(客户编辑)
 */
public class ClientEditActivity extends BaseActivity {
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.tv_client_type)
    TextView tvClientType;
    @BindView(R.id.grid_view_client_type)
    GridView gridViewClientType;
    @BindView(R.id.linear_client_type)
    LinearLayout linearClientType;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_date_time)
    TextView tvDateTime;
    @BindView(R.id.image_date_time)
    ImageView imageDateTime;
    @BindView(R.id.linear_date_time)
    LinearLayout linearDateTime;
    @BindView(R.id.linear_next)
    LinearLayout linearNext;
    @BindView(R.id.tv_follow_miaoshu)
    TextView tvFollowMiaoshu;
    @BindView(R.id.et_follow_miaoshu)
    EditText etFollowMiaoshu;
    @BindView(R.id.linear_follow_miaoshu)
    LinearLayout linearFollowMiaoshu;
    @BindView(R.id.tv_give_up_type)
    TextView tvGiveUpType;
    @BindView(R.id.tv_right_give_up_type)
    TextView tvRightGiveUpType;
    @BindView(R.id.image_give_up_type)
    ImageView imageGiveUpType;
    @BindView(R.id.rl_give_up_type)
    RelativeLayout rlGiveUpType;
    /**
     * 客户类型("新客户","潜在","暂无意向",无效，到期会员)
     */
    private List<KeyValueDataBean> listClientType;
    private ClientTypeAdapter adapterClientType;
    private int selectPosition;
    private int type;

    /**
     * 新客户、潜在客户(客户标签)
     */
    private List<KeyValueDataBean> keyValueDataBeanList1;
    /**
     * 暂无意向(客户标签)
     */
    private List<KeyValueDataBean> keyValueDataBeanList2;
    /**
     * 无效(无效原因)
     */
    private List<KeyValueDataBean> keyValueDataBeanList3;

    private UserInfoDataBean listBean;
    private TimePickerView pvTime;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_edit);
        ButterKnife.bind(this);
        initIntent();
        initData();
        initTimePicker();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        keyValueDataBeanList1 = KeyValueUtils.getDetailPageClientType();
        keyValueDataBeanList2 = KeyValueUtils.getDetailNoYiXiang();
        keyValueDataBeanList3 = KeyValueUtils.getWuXiao();
        //0-新客户、1-潜在（初步接触）、2-持续跟进（归到1）、3-意向（线下跟进）、4-成交、5-无效、6-已签单客户（归到5）、7-暂无意向、8-待签约
        switch (listBean.getCo_type()) {
            case 0:
            case 1:
            case 2:
                listClientType = KeyValueUtils.getClientType1();
                selectPosition = 1;
                rlGiveUpType.setVisibility(View.VISIBLE);
                tvGiveUpType.setText("客户标签");
                break;
            case 7:
                listClientType = KeyValueUtils.getClientType1();
                selectPosition = 2;
                rlGiveUpType.setVisibility(View.VISIBLE);
                tvGiveUpType.setText("客户标签");
                break;
            case 3:
                listClientType = KeyValueUtils.getClientType2();
                selectPosition = 1;
                rlGiveUpType.setVisibility(View.GONE);
                break;
            case 8:
                listClientType = KeyValueUtils.getClientType2();
                selectPosition = 0;
                rlGiveUpType.setVisibility(View.GONE);
                break;
            case 4:
                listClientType = KeyValueUtils.getClientType3();
                selectPosition = 0;
                rlGiveUpType.setVisibility(View.GONE);
                break;
            case 5:
            case 6:
                listClientType = KeyValueUtils.getClientType1();
                selectPosition = 0;
                rlGiveUpType.setVisibility(View.VISIBLE);
                tvGiveUpType.setText("无效原因");
                break;
            default:
                listClientType = KeyValueUtils.getClientType1();
                selectPosition = 1;
                rlGiveUpType.setVisibility(View.GONE);
                break;
        }
        adapterClientType = new ClientTypeAdapter(this, listClientType, selectPosition);
        gridViewClientType.setAdapter(adapterClientType);
        gridViewClientType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterClientType.setSelectPosition(position);
                adapterClientType.notifyDataSetChanged();
                tvRightGiveUpType.setText("请选择");
                type = listClientType.get(position).getId();
                //0-新客户、1-潜在（初步接触）、2-持续跟进（归到1）、3-意向（线下跟进）、4-成交、5-无效、6-已签单客户（归到5）、7-暂无意向、8-待签约
                switch (listClientType.get(position).getId()) {
                    case 0:
                    case 1:
                    case 2:
                    case 7:
                        rlGiveUpType.setVisibility(View.VISIBLE);
                        tvGiveUpType.setText("客户标签");
                        break;
                    case 5:
                    case 6:
                        rlGiveUpType.setVisibility(View.VISIBLE);
                        tvGiveUpType.setText("无效原因");
                        break;
                    default:
                        rlGiveUpType.setVisibility(View.GONE);
                        break;
                }
            }
        });

    }

    /**
     * 初始化intent
     */
    private void initIntent() {
        listBean = (UserInfoDataBean) getIntent().getSerializableExtra(UserInfoDataBean.class.getName());
        tvDateTime.setText(listBean.getFollow_time());
    }

    @OnClick({R.id.tv_cancle, R.id.tv_sure, R.id.linear_date_time, R.id.rl_give_up_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle://取消
                finish();
                break;
            case R.id.tv_sure:
                break;
            case R.id.linear_date_time://日期选择
                pvTime.show();
                break;
            case R.id.rl_give_up_type://客户标签
                //0-新客户、1-潜在（初步接触）、2-持续跟进（归到1）、3-意向（线下跟进）、4-成交、5-无效、6-已签单客户（归到5）、7-暂无意向、8-待签约
                switch (type) {
                    case 0:
                    case 1:
                    case 2:
                        DialogUtils.getInstances().showBottomSelect(this, keyValueDataBeanList1, new DialogUtils.OnBottomItemSelectListener() {
                            @Override
                            public void onItemSelect(int position) {
                                tvRightGiveUpType.setText(keyValueDataBeanList1.get(position).getName());
                            }
                        });
                        break;
                    case 5:
                    case 6:
                        DialogUtils.getInstances().showBottomSelect(this, keyValueDataBeanList3, new DialogUtils.OnBottomItemSelectListener() {
                            @Override
                            public void onItemSelect(int position) {
                                tvRightGiveUpType.setText(keyValueDataBeanList3.get(position).getName());
                            }
                        });
                        break;
                    case 7:
                        DialogUtils.getInstances().showBottomSelect(this, keyValueDataBeanList2, new DialogUtils.OnBottomItemSelectListener() {
                            @Override
                            public void onItemSelect(int position) {
                                tvRightGiveUpType.setText(keyValueDataBeanList2.get(position).getName());
                            }
                        });
                        break;
                }
                break;
        }
    }

    private void initTimePicker() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvDateTime.setText(dateFormat.format(date));
            }
        }).setType(new boolean[]{true, true, true, true, true, true})  //年、月、日、时、分、秒，true显示、false不显示
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
            }
        }
    }
}
