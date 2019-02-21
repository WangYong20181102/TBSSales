package com.tbs.sales.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tbs.sales.R;
import com.tbs.sales.constant.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr.Wang on 2019/2/20 14:13.
 * 应用
 */
public class ApplicationActivity extends BaseActivity {
    @BindView(R.id.image_test)
    ImageView imageTest;
    @BindView(R.id.ll_pending_order)
    LinearLayout llPendingOrder;
    @BindView(R.id.ll_already_divided)
    LinearLayout llAlreadyDivided;
    @BindView(R.id.ll_withdrawn)
    LinearLayout llWithdrawn;
    @BindView(R.id.ll_contract_filing)
    LinearLayout llContractFiling;
    @BindView(R.id.ll_collection_report)
    LinearLayout llCollectionReport;
    @BindView(R.id.ll_approval)
    LinearLayout llApproval;
    @BindView(R.id.ll_calculator)
    LinearLayout llCalculator;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.image_test, R.id.ll_pending_order, R.id.ll_already_divided, R.id.ll_withdrawn, R.id.ll_contract_filing, R.id.ll_collection_report, R.id.ll_approval, R.id.ll_calculator})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_test:   //图片
                break;
            case R.id.ll_pending_order: //待分单
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_WAIT);
                break;
            case R.id.ll_already_divided: //已分单
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_ALREADY);
                break;
            case R.id.ll_withdrawn: //已撤单
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_REVOKE);
                break;
            case R.id.ll_contract_filing: //合同报备
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_CONTRACT);
                break;
            case R.id.ll_collection_report: //收款报备
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_RECEIPT);
                break;
            case R.id.ll_approval: //待审批
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_APPROVE);
                break;
            case R.id.ll_calculator: //计算器
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", Constant.WXDISTRIBUTE_COMPUTE);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
