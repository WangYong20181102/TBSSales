package com.tbs.sales.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.widget.ClientItemWidget;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr.Wang on 2019/2/20 14:12.
 * 客户
 */
public class ClientActivity extends BaseActivity {
    @BindView(R.id.image_benyuepaihang)
    ImageView imageBenyuepaihang;
    @BindView(R.id.tv_list)
    TextView tvList;
    @BindView(R.id.image_right_side)
    ImageView imageRightSide;
    @BindView(R.id.ciw_my_client)
    ClientItemWidget ciwMyClient;
    @BindView(R.id.ciw_public_client)
    ClientItemWidget ciwPublicClient;
    @BindView(R.id.ciw_invalid_customer)
    ClientItemWidget ciwInvalidCustomer;
    @BindView(R.id.ciw_follow_up_record)
    ClientItemWidget ciwFollowUpRecord;
    @BindView(R.id.ciw_customer_follow_up)
    ClientItemWidget ciwCustomerFollowUp;
    @BindView(R.id.ciw_outbound_statistics)
    ClientItemWidget ciwOutboundStatistics;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ciw_my_client, R.id.ciw_public_client, R.id.ciw_invalid_customer, R.id.ciw_follow_up_record, R.id.ciw_customer_follow_up, R.id.ciw_outbound_statistics})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ciw_my_client:    //我的客户
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_CUSTOMER_MY);
                break;
            case R.id.ciw_public_client://公共客户
                break;
            case R.id.ciw_invalid_customer://无效客户
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_CUSTOMER_INVALID);
                break;
            case R.id.ciw_follow_up_record://跟进记录
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_CUSTOMER_FOLLOW);
                break;
            case R.id.ciw_customer_follow_up://客户跟进
                break;
            case R.id.ciw_outbound_statistics://外呼统计
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
