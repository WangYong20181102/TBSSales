package com.tbs.sales.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.tbs.sales.constant.Constant;

import java.io.File;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Mr.Wang on 2019/3/8 13:33.
 */
public class ImageUploadUtils {
    private static Activity activity;
    private static Context context;
    private static File mGalleryFile;//相册路径
    private static File mCameraFile;//拍照路径
    private static final String MGALLERYFILE_NAME = DateTimeUtils.getNowTime() + "mgalleryfile_name.jpg";
    private static final String MCROPFILE_NAME = DateTimeUtils.getNowTime() + "mcropfile_name.jpg";
    private static final String MCAMERAFILE_NAME = DateTimeUtils.getNowTime() + "mcamerafile_name.jpg";
    private final int CAMERA_REQUEST_CODE = 1;
    private final int RESULT_REQUEST_CODE = 2;
    private final int SELECT_PIC_KITKAT = 3;
    private final int IMAGE_REQUEST_CODE = 4;
    private String TAG = "PeopleMessageActivity";
    private Uri uritempFile;
    private static File mCropFile;//切图路径


    private static ImageUploadUtils imageUploadUtils = null;

    public static ImageUploadUtils getInstances(Context context) {
        activity = (Activity) context;
        ImageUploadUtils.context = context;
        if (imageUploadUtils == null) {
            imageUploadUtils = new ImageUploadUtils();
        }
        initViewEvent();
        return imageUploadUtils;
    }

    private static void initViewEvent() {
        mGalleryFile = new File(Environment.getExternalStorageDirectory(), MGALLERYFILE_NAME);
        mCameraFile = new File(Environment.getExternalStorageDirectory(), MCAMERAFILE_NAME);
        mCropFile = new File(Environment.getExternalStorageDirectory(), MCROPFILE_NAME);


    }

    /**
     * 检查相册权限
     */
    public void checkPhotoPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //权限还没有授予，需要在这里写申请权限的代码
                activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            } else {
                //权限已经被授予，在这里直接写要执行的相应方法即可
                getPhotoFormPictrue();
            }
        } else {
            getPhotoFormPictrue();
        }
    }

    /**
     * 检查相机权限
     */
    public File checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            //6.0以上的系统进行权限获取
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CAMERA},
                        10086);
            } else {
                takeCamerGetPhoto();
            }
        } else {
            takeCamerGetPhoto();
        }
        return mCameraFile;
    }

    //相册中获取照片
    public void getPhotoFormPictrue() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果大于等于7.0使用FileProvider
            Uri uriForFile = FileProvider.getUriForFile(context, "com.tbs.sales.fileprovider", mGalleryFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivityForResult(intent, SELECT_PIC_KITKAT);
        } else {
            activity.startActivityForResult(intent, IMAGE_REQUEST_CODE);
        }
    }

    //拍照获取照片
    public File takeCamerGetPhoto() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (DateTimeUtils.hasSDCard()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0及以上
                Uri uriForFile = FileProvider.getUriForFile(context, "com.tbs.sales.fileprovider", mCameraFile);
                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
                intentFromCapture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intentFromCapture.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCameraFile));
            }
        }
        activity.startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
        return mCameraFile;
    }

    //图片上传
    public void HttpUpLoadImage(File mImageFile, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        MediaType IMG_TYPE = MediaType.parse("image/*");
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("filedata", mImageFile.getName(), RequestBody.create(IMG_TYPE, mImageFile));
        builder.addFormDataPart("token", Utils.getDateToken());
        builder.addFormDataPart("app_type", "1");
        MultipartBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(Constant.UPLOAD_DYNAMIC_IMAGE)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //裁剪
    public Uri startPhotoZoom(Uri inputUri) {
        if (inputUri == null) {
            Log.e(TAG, "The uri is not exist.");
            return null;
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
                String url = GetImagePath.getPath(context, inputUri);//这个方法是处理4.4以上图片返回的Uri对象不同的处理方法
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
        activity.startActivityForResult(intent, RESULT_REQUEST_CODE);//这里就将裁剪后的图片的Uri返回了
        return uritempFile;
    }
}
