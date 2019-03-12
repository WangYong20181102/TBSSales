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
    EditText etPhoneNumber;
    @BindView(R.id.image_username_del)
    ImageView imageUsernameDel;

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
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    imageUsernameDel.setVisibility(View.VISIBLE);
                } else {
                    imageUsernameDel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.tv_cancle, R.id.tv_sure, R.id.image_username_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:    //取消
                finish();
                break;
            case R.id.tv_sure:
                if (TextUtils.isEmpty(etPhoneNumber.getText().toString().trim())) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                } else {
//                    initHttpRequest();
                    EventBusUtil.sendEvent(new Event(EC.EventCode.CHANGE_PHONE,etPhoneNumber.getText().toString().trim()));
                    finish();
                }
                break;
            case R.id.image_username_del:   //清除
                etPhoneNumber.setText("");
                break;
        }
    }

    /**
     * 更改用户手机号
     */
    private void initHttpRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppInfoUtils.getToekn(this));
        params.put("field", "phone");
        params.put("value", etPhoneNumber.getText().toString().trim());
        OkHttpUtils.post(Constant.USER_EDITACCOUNTALONE, params, new Callback() {
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
                                EventBusUtil.sendEvent(new Event(EC.EventCode.UPDATE_USERINFO));
                                Toast.makeText(EditPhoneActivity.this, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(EditPhoneActivity.this, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
