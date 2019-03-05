package com.tbs.sales.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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

import com.tbs.sales.R;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.DateTimeUtils;
import com.tbs.sales.utils.FileUtil;
import com.tbs.sales.utils.GetImagePath;
import com.tbs.sales.utils.GlideUtils;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
    private File mGalleryFile;//相册路径
    private File mCameraFile;//拍照路径
    private static final String MGALLERYFILE_NAME = DateTimeUtils.getNowTime() + "mgalleryfile_name.jpg";
    private static final String MCROPFILE_NAME = DateTimeUtils.getNowTime() + "mcropfile_name.jpg";
    private static final String MCAMERAFILE_NAME = DateTimeUtils.getNowTime() + "mcamerafile_name.jpg";
    private final int CAMERA_REQUEST_CODE = 1;
    private final int RESULT_REQUEST_CODE = 2;
    private final int SELECT_PIC_KITKAT = 3;
    private final int IMAGE_REQUEST_CODE = 4;
    private String TAG = "PeopleMessageActivity";
    private Uri uritempFile;
    private File mCropFile;//切图路径

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_message);
        ButterKnife.bind(this);
        initViewEvent();
    }

    private void initViewEvent() {
        mGalleryFile = new File(Environment.getExternalStorageDirectory(), MGALLERYFILE_NAME);
        mCameraFile = new File(Environment.getExternalStorageDirectory(), MCAMERAFILE_NAME);
        mCropFile = new File(Environment.getExternalStorageDirectory(), MCROPFILE_NAME);


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

    /**
     * 性别更改
     */
    private void changeGender() {
        View view = View.inflate(this,R.layout.pop_change_gender,null);
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
                tvGender.setText("男");
            }
        });
        textWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                tvGender.setText("女");
            }
        });
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
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
                writerReadSDcard();
                popupWindow.dismiss();
            }
        });
        //拍照获取
        pop_change_icon_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断当前系统是否高于或等于6.0
                //拍照获取
                if (Build.VERSION.SDK_INT >= 23) {
                    //6.0以上的系统进行权限获取
                    if (ContextCompat.checkSelfPermission(PeopleMessageActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(PeopleMessageActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                10086);
                    } else {
                        takeCamerGetPhoto();
                    }
                } else {
                    takeCamerGetPhoto();
                }
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
                takeCamerGetPhoto();
            } else {
                //获取权限失败
                Toast.makeText(PeopleMessageActivity.this, "你取消了相机授权~", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == 101){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhotoFormPictrue();
            } else {
                Toast.makeText(this, "权限获取失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //相册中获取照片
    private void getPhotoFormPictrue() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果大于等于7.0使用FileProvider
            Uri uriForFile = FileProvider.getUriForFile(PeopleMessageActivity.this, "com.tbs.sales.fileprovider", mGalleryFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, SELECT_PIC_KITKAT);
        } else {
            startActivityForResult(intent, IMAGE_REQUEST_CODE);
        }
    }

    //拍照获取照片
    private void takeCamerGetPhoto() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (DateTimeUtils.hasSDCard()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0及以上
                Uri uriForFile = FileProvider.getUriForFile(PeopleMessageActivity.this, "com.tbs.sales.fileprovider", mCameraFile);
                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
                intentFromCapture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intentFromCapture.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCameraFile));
            }
        }
        startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
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
//                Log.e(TAG, "拍照完进入裁剪功能==============");
                if (resultCode == Activity.RESULT_CANCELED) {
                    return;
                }
                if (DateTimeUtils.hasSDCard()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri inputUri = FileProvider.getUriForFile(PeopleMessageActivity.this, "com.tbs.sales.fileprovider", mCameraFile);//通过FileProvider创建一个content类型的Uri
                        startPhotoZoom(inputUri);//设置输入类型
                    } else {
                        Uri inputUri = Uri.fromFile(mCameraFile);
                        startPhotoZoom(inputUri);
                    }
                } else {
                    Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                }
                break;
            case RESULT_REQUEST_CODE:
//                Log.e(TAG, "裁剪成功===============");
                if (resultCode == Activity.RESULT_CANCELED) {
                    return;
                }
                //设置数据
                if (data != null) {
                    GlideUtils.glideLoader(this, uritempFile, R.drawable.iamge_loading, R.drawable.iamge_loading, imageHead, 0);
                    File file = FileUtil.getFileByUri(uritempFile, this);
//                    Log.e(TAG, "获取转换的File大小===========" + file.length());
//                    Log.e(TAG, "获取转换的File路径===========" + file.getPath());
//                    Log.e(TAG, "获取转换的File名字===========" + file.getName());
                    //上传文件
//                    HttpUpLoadImage(file);
                }
                break;
            case IMAGE_REQUEST_CODE://版本<7.0  图库后返回
                if (resultCode == Activity.RESULT_CANCELED) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            case SELECT_PIC_KITKAT://版本>= 7.0
                if (resultCode == Activity.RESULT_CANCELED) {
                    return;
                }
                File imgUri = new File(GetImagePath.getPath(this, data.getData()));
                Uri dataUri = FileProvider.getUriForFile(this, "com.tbs.sales.fileprovider", imgUri);
                startPhotoZoom(dataUri);
                break;

        }
    }

    //图片上传
    private void HttpUpLoadImage(File mImageFile) {
        OkHttpClient client = new OkHttpClient();
        MediaType IMG_TYPE = MediaType.parse("image/*");
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("filedata", mImageFile.getName(), RequestBody.create(IMG_TYPE, mImageFile));
        builder.addFormDataPart("token", AppInfoUtils.getToekn(this));
        builder.addFormDataPart("app_type", "1");
        MultipartBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(Constant.UPLOAD_DYNAMIC_IMAGE)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "链接失败=====" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = new String(response.body().string());
                Log.e(TAG, "上传图片链接成功======" + json);
//                mImageUpload = mGson.fromJson(json, _ImageUpload.class);
//                presonerInfo.setIcon(mImageUpload.getData().getUrl());
//                HttpChangeUserMsg(mImageUpload.getData().getUrl(),
//                        "", "", "", "");
            }
        });
    }

    //裁剪
    public void startPhotoZoom(Uri inputUri) {
        if (inputUri == null) {
            Log.e(TAG, "The uri is not exist.");
            return;
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        //sdk>=24
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Uri outPutUri = Uri.fromFile(mCropFile);
            intent.setDataAndType(inputUri, "image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
            intent.putExtra("noFaceDetection", false);//去除默认的人脸识别，否则和剪裁匡重叠
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        } else {
            Uri outPutUri = Uri.fromFile(mCropFile);
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                String url = GetImagePath.getPath(this, inputUri);//这个方法是处理4.4以上图片返回的Uri对象不同的处理方法
                intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
            } else {
                intent.setDataAndType(inputUri, "image/*");
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
        }

        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 720);
        intent.putExtra("outputY", 720);
//        intent.putExtra("return-data", true);
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + DateTimeUtils.getNowTime() + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 图片格式
        startActivityForResult(intent, RESULT_REQUEST_CODE);//这里就将裁剪后的图片的Uri返回了
    }

    /**
     * 动态获取6.0版本以上手机存储权限
     */
    public void writerReadSDcard() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //权限还没有授予，需要在这里写申请权限的代码
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            } else {
                //权限已经被授予，在这里直接写要执行的相应方法即可
                getPhotoFormPictrue();
            }
        } else {
            getPhotoFormPictrue();
        }
    }

}
