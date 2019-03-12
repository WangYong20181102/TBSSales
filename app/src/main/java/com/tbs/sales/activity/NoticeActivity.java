package com.tbs.sales.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.bean.MyMessageBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr.Wang on 2019/3/1 14:23.
 * 通知
 */
public class NoticeActivity extends BaseActivity {
    @BindView(R.id.linear_back)
    LinearLayout linearBack;
    @BindView(R.id.text_tittle)
    TextView textTittle;
    @BindView(R.id.text_data)
    TextView textData;
    @BindView(R.id.text_content)
    TextView textContent;
    private MyMessageBean messageBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        messageBean = (MyMessageBean) getIntent().getSerializableExtra(MyMessageBean.class.getName());
        textTittle.setText(messageBean.getTitle());
        textData.setText(messageBean.getCreate_time());
        textContent.setText(messageBean.getContent());
    }

    @OnClick(R.id.linear_back)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.linear_back:  //返回
                finish();
                break;
        }
    }
}
