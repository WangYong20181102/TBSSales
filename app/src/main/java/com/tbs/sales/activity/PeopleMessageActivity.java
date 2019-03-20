package com.tbs.sales.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tbs.sales.R;
import com.tbs.sales.bean.Event;
import com.tbs.sales.bean.ImageUpload;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.DateTimeUtils;
import com.tbs.sales.utils.EC;
import com.tbs.sales.utils.EventBusUtil;
import com.tbs.sales.utils.FileUtil;
import com.tbs.sales.utils.GetImagePath;
import com.tbs.sales.utils.GlideUtils;
import com.tbs.sales.utils.ImageUploadUtils;
import com.tbs.sales.utils.LogUtils;
import com.tbs.sales.utils.OkHttpUtils;
import com.tbs.sales.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    private File mCameraFile;//拍照路径
    private final int CAMERA_REQUEST_CODE = 1;
    private final int RESULT_REQUEST_CODE = 2;
    private final int SELECT_PIC_KITKAT = 3;
    private final int IMAGE_REQUEST_CODE = 4;
    private String TAG = "PeopleMessageActivity";
    private Uri uritempFile;
    private String userIcon = "";   //用户头像
    private ImageUpload mImageUpload;
    private Gson gson;
    private String name = "";//姓名
    private String phone = "";//手机号

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_message);
        ButterKnife.bind(this);
        gson = new Gson();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        name = AppInfoUtils.getUserNickname(this);
        phone = AppInfoUtils.getCellPhone(this);
        tvUsername.setText(name);
        tvPhoneNumber.setText(phone);
        if (AppInfoUtils.getUserSex(this) == 1) {
            tvGender.setText("男");
        } else {
            tvGender.setText("女");
        }
    }

    @OnClick({R.id.relative_back, R.id.image_head, R.id.relative_change_head, R.id.relative_username, R.id.relative_gender, R.id.relative_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relative_back:    //返回
                finish();
                break;
            case R.id.image_head:
            case R.id.relative_change_head://更换头像
                changeIcon();
                break;
            case R.id.relative_username:    //姓名
                startActivity(new Intent(PeopleMessageActivity.this, EditNameActivity.class));

                break;
            case R.id.relative_gender:  //性别
                changeGender();
                break;
            case R.id.relative_phone:  //手机
                startActivity(new Intent(PeopleMessageActivity.this, EditPhoneActivity.class));
                break;
        }
    }

    @Subscribe
    @Override
    public boolean isRegisterEventBus() {
        return true;
    }

    @Subscribe
    @Override
    public void receiveEvent(Event event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case EC.EventCode.CHANGE_USERNAME://更换姓名
                name = event.getData().toString().trim();
                initHttpRequest(event.getData().toString().trim(), "real_name");
                break;
            case EC.EventCode.CHANGE_PHONE://更换手机号
                phone = event.getData().toString().trim();
                initHttpRequest(event.getData().toString().trim(), "phone");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        userIcon = AppInfoUtils.getUserIcon(this);
        if (TextUtils.isEmpty(userIcon)) {
            imageHead.setImageResource(R.mipmap.img_moren);
        } else {
            GlideUtils.glideLoader(this, userIcon, imageHead);
        }

    }

    /**
     * 性别更改
     */
    private void changeGender() {
        View view = View.inflate(this, R.layout.pop_change_gender, null);
        RelativeLayout relativeLayout = view.findViewById(R.id.relative_bg);//父布局
        //男
        TextView textMan = view.findViewById(R.id.text_man);
        //女
        TextView textWoman = view.findViewById(R.id.text_woman);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        textMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initHttpRequest("1", "sex");
            }
        });
        textWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initHttpRequest("2", "sex");
            }
        });
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    /**
     * @param value
     */
    private void initHttpRequest(final String value, final String field) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppInfoUtils.getToekn(this));
        params.put("field", field);
        params.put("value", value);
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
                                tvUsername.setText(name);   //更改用户姓名
                                tvPhoneNumber.setText(phone);//更改手机号
                                if (field.equals("sex")){   //性别
                                    if (value.equals("1")) {
                                        tvGender.setText("男");
                                    } else {
                                        tvGender.setText("女");
                                    }
                                }
                                if (field.equals("icon") || field.equals("real_name")) {    //更换头像、姓名通知客户界面更新数据
                                    EventBusUtil.sendEvent(new Event(EC.EventCode.UPDATE_CLIENT_DATA));
                                }
                                ToastUtils.toastShort(PeopleMessageActivity.this, jsonObject.optString("message"));
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.toastShort(PeopleMessageActivity.this, jsonObject.optString("message"));
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //更换用户的头像
    private void changeIcon() {
        //点击出现弹窗
        View popview = View.inflate(this, R.layout.pop_change_icon, null);
        //拍照获取
        TextView pop_change_icon_take_photo = popview.findViewById(R.id.pop_change_icon_take_photo);
        //从相册中获取
        TextView pop_change_icon_get_photo = popview.findViewById(R.id.pop_change_icon_get_photo);
        //取消
        TextView pop_change_icon_dissmiss = popview.findViewById(R.id.pop_change_icon_dissmiss);
        //整个可点击层
        RelativeLayout pop_change_icon_rl = popview.findViewById(R.id.pop_change_icon_rl);
        //按键层
        LinearLayout pop_change_icon_11 = popview.findViewById(R.id.pop_change_icon_11);
        pop_change_icon_11.setBackgroundColor(Color.parseColor("#ffffff"));
        final PopupWindow popupWindow = new PopupWindow(popview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        //取消事件
        pop_change_icon_dissmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //从相册中获取
        pop_change_icon_get_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 从相册中获取
                ImageUploadUtils.getInstances(PeopleMessageActivity.this).checkPhotoPermission();
                popupWindow.dismiss();
            }
        });
        //拍照获取
        pop_change_icon_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照获取
                mCameraFile = ImageUploadUtils.getInstances(PeopleMessageActivity.this).checkCameraPermission();
                popupWindow.dismiss();
            }
        });
        //点击整个层级
        pop_change_icon_dissmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //点击整个非按钮区域
        pop_change_icon_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(popview, Gravity.CENTER, 0, 0);
    }
//权限获取处理

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 10086) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //获取权限成功
                mCameraFile = ImageUploadUtils.getInstances(PeopleMessageActivity.this).takeCamerGetPhoto();
            } else {
                //获取权限失败
                Toast.makeText(PeopleMessageActivity.this, "你取消了相机授权~", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ImageUploadUtils.getInstances(PeopleMessageActivity.this).getPhotoFormPictrue();
            } else {
                Toast.makeText(this, "权限获取失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "获取的返回码======================" + resultCode);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (resultCode == Activity.RESULT_CANCELED) {
                    return;
                }
                if (DateTimeUtils.hasSDCard()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri inputUri = FileProvider.getUriForFile(PeopleMessageActivity.this, "com.tbs.sales.fileprovider", mCameraFile);//通过FileProvider创建一个content类型的Uri
                        uritempFile = ImageUploadUtils.getInstances(PeopleMessageActivity.this).startPhotoZoom(inputUri);
                    } else {
                        Uri inputUri = Uri.fromFile(mCameraFile);
                        uritempFile = ImageUploadUtils.getInstances(PeopleMessageActivity.this).startPhotoZoom(inputUri);
                    }
                } else {
                    Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                }
                break;
            case RESULT_REQUEST_CODE:
                if (resultCode == Activity.RESULT_CANCELED) {
                    return;
                }
                //设置数据
                if (data != null) {
                    File file = FileUtil.getFileByUri(uritempFile, this);
                    //上传图片
                    ImageUploadUtils.getInstances(this).HttpUpLoadImage(file, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            LogUtils.logE("链接失败=====" + e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String json = new String(response.body().string());
                            try {
                                final JSONObject jsonObject = new JSONObject(json);
                                String status = jsonObject.optString("status");
                                if (status.equals("200")) {
                                    mImageUpload = gson.fromJson(json, ImageUpload.class);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            LogUtils.logE("上传图片链接成功======");
                                            GlideUtils.glideLoader(PeopleMessageActivity.this, uritempFile, R.drawable.iamge_loading, R.drawable.iamge_loading, imageHead, 0);
                                            initHttpRequest(mImageUpload.getData().getUrl(), "icon");
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;
            case IMAGE_REQUEST_CODE://版本<7.0  图库后返回
                if (resultCode == Activity.RESULT_CANCELED) {
                    return;
                }
                uritempFile = ImageUploadUtils.getInstances(PeopleMessageActivity.this).startPhotoZoom(data.getData());
                break;
            case SELECT_PIC_KITKAT://版本>= 7.0
                if (resultCode == Activity.RESULT_CANCELED) {
                    return;
                }
                File imgUri = new File(GetImagePath.getPath(this, data.getData()));
                Uri dataUri = FileProvider.getUriForFile(this, "com.tbs.sales.fileprovider", imgUri);
                uritempFile = ImageUploadUtils.getInstances(PeopleMessageActivity.this).startPhotoZoom(dataUri);
                break;
        }
    }
}
