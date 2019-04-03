package com.tbs.sales.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tbs.sales.R;
import com.tbs.sales.bean.Event;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.EC;
import com.tbs.sales.utils.EventBusUtil;
import com.tbs.sales.utils.OkHttpUtils;
import com.tbs.sales.widget.ClearEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Mr.Wang on 2019/2/21 15:03.
 */
public class EditPhoneActivity extends BaseActivity {
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.et_phone_number)
    ClearEditText etPhoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phone);
        ButterKnife.bind(this);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        String phone = AppInfoUtils.getCellPhone(this);
        etPhoneNumber.setText(phone);
        etPhoneNumber.setSelection(phone.length());
    }

    @OnClick({R.id.tv_cancle, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:    //取消
                finish();
                break;
            case R.id.tv_sure:
                if (TextUtils.isEmpty(etPhoneNumber.getText().toString().trim())) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                } else {
                    EventBusUtil.sendEvent(new Event(EC.EventCode.CHANGE_PHONE, etPhoneNumber.getText().toString().trim()));
                    finish();
                }
                break;
        }
    }
}
