package com.tbs.sales.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbs.sales.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr.Wang on 2019/2/21 14:32.
 */
public class PeopleMessageActivity extends BaseActivity {
    @BindView(R.id.relative_back)
    RelativeLayout relativeBack;
    @BindView(R.id.image_head)
    ImageView imageHead;
    @BindView(R.id.image_head_right_arrow)
    ImageView imageHeadRightArrow;
    @BindView(R.id.relative_change_head)
    RelativeLayout relativeChangeHead;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.image_name_right_arrow)
    ImageView imageNameRightArrow;
    @BindView(R.id.relative_username)
    RelativeLayout relativeUsername;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.image_gender_right_arrow)
    ImageView imageGenderRightArrow;
    @BindView(R.id.relative_gender)
    RelativeLayout relativeGender;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.relative_phone)
    RelativeLayout relativePhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_message);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.relative_back, R.id.image_head, R.id.relative_change_head, R.id.relative_username, R.id.relative_gender,R.id.relative_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_back:    //返回
                finish();
                break;
            case R.id.image_head:
                break;
            case R.id.relative_change_head:
                break;
            case R.id.relative_username:    //姓名
                startActivity(new Intent(PeopleMessageActivity.this,EditNameActivity.class));

                break;
            case R.id.relative_gender:  //性别
                break;
            case R.id.relative_phone:  //手机
                startActivity(new Intent(PeopleMessageActivity.this,EditPhoneActivity.class));
                break;
        }
    }
}
