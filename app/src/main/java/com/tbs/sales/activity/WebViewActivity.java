package com.tbs.sales.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tbs.sales.R;
import com.tbs.sales.utils.ActionSheetDialog;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.DateTimeUtils;
import com.tbs.sales.utils.GetPathFromUri4kitkat;
import com.tbs.sales.utils.ImageUploadUtils;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v4.content.FileProvider.getUriForFile;

/**
 * Created by Mr.Wang on 2019/2/20 16:15.
 */
public class WebViewActivity extends BaseActivity {
    @BindView(R.id.new_webview_back)
    LinearLayout newWebviewBack;
    @BindView(R.id.new_webview_title)
    TextView newWebviewTitle;
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    @BindView(R.id.new_webview_banner_rl)
    RelativeLayout newWebviewBannerRl;
    @BindView(R.id.web_view)
    WebView webView;
    private Intent mIntent;
    private String mLoadingUrl = "";//加载数据的URL
    private static final String TAG = WebViewActivity.class.getSimpleName();
    private static final int REQUEST_CODE_TAKE_PICETURE = 11;
    private static final int REQUEST_CODE_PICK_PHOTO = 12;
    private static final int REQUEST_CODE_PERMISSION = 13;
    public static ValueCallback mFilePathCallback;
    private File picturefile;
    private static final String MCAMERAFILE_NAME = DateTimeUtils.getNowTime() + "mcamerafile_name.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        initWebView();
    }

    private void initWebView() {
        mIntent = getIntent();
        newWebviewBannerRl.setBackgroundColor(Color.parseColor("#ffffff"));
        mLoadingUrl = mIntent.getStringExtra("mLoadingUrl");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSavePassword(true);
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);

        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(mLoadingUrl);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            writeData();
        }
    };


    private void writeData() {
        String key = "token";
        String val = AppInfoUtils.getToekn(this);
        String key2 = "user_id";
        String val2 = AppInfoUtils.getId(this);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("window.localStorage.setItem('" + key + "','" + val + "');", null);
            webView.evaluateJavascript("window.localStorage.setItem('" + key2 + "','" + val2 + "');", null);
        } else {
            webView.loadUrl("javascript:localStorage.setItem('" + key + "','" + val + "');");
            webView.loadUrl("javascript:localStorage.setItem('" + key2 + "','" + val2 + "');");
        }
    }

    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            newWebviewTitle.setText(title);
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {//5.0+
            Log.e(TAG, "--------调用onShowFileChooser");
            showDialog();
            mFilePathCallback = filePathCallback;
            return true;
        }

        //openFileChooser 方法是隐藏方法
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {// android 系统版本>4.1.1
            Log.e(TAG, "--------调用openFileChooser");
            showDialog();
            mFilePathCallback = uploadMsg;
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {//android 系统版本<3.0
            showDialog();
            mFilePathCallback = uploadMsg;
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {//android 系统版本3.0+
            showDialog();
            mFilePathCallback = uploadMsg;
        }
    };

    private void showDialog() {
        ActionSheetDialog dialog = new ActionSheetDialog(WebViewActivity.this).builder().addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                checkCameraPermission();
            }
        }).addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                checkPhotoPermission();
            }
        }).setCancelable(false).setCanceledOnTouchOutside(false);

        dialog.show();
        //设置点击“取消”按钮监听，目的取消mFilePathCallback回调，可以重复调起弹窗
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelFilePathCallback();
            }
        });
    }
    /**
     * 检查相机权限
     */
    public void checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            //6.0以上的系统进行权限获取
            if (ContextCompat.checkSelfPermission(WebViewActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(WebViewActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_PERMISSION);
            } else {
                takeForPicture();
            }
        } else {
            takeForPicture();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE_PERMISSION == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //获取权限成功
                takeForPicture();
            } else {
                //获取权限失败
                Toast.makeText(WebViewActivity.this, "拍照权限被禁用，请在权限管理修改", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takeForPhoto();
            } else {
                Toast.makeText(this, "权限获取失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 调用相册
     */
    private void takeForPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_PHOTO);

    }
    /**
     * 检查相册权限
     */
    public void checkPhotoPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(WebViewActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //权限还没有授予，需要在这里写申请权限的代码
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            } else {
                //权限已经被授予，在这里直接写要执行的相应方法即可
                takeForPhoto();
            }
        } else {
            takeForPhoto();
        }
    }

    /**
     * 调用相机
     */
    private void takeForPicture() {
        picturefile = new File(Environment.getExternalStorageDirectory(), MCAMERAFILE_NAME);//图片位置
        if (!picturefile.exists()) {
            try {
                picturefile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.e(TAG, "拍照所存路径: ===" + picturefile.getAbsolutePath());

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (DateTimeUtils.hasSDCard()) {
            if (Build.VERSION.SDK_INT > 23) {//7.0及以上
                Uri uriForFile = FileProvider.getUriForFile(WebViewActivity.this, "com.tbs.sales.fileprovider", picturefile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {//7.0以下
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picturefile));
            }
        }
        startActivityForResult(intent, REQUEST_CODE_TAKE_PICETURE);

    }

    private void cancelFilePathCallback() {
        if (mFilePathCallback != null) {
            mFilePathCallback.onReceiveValue(null);
            mFilePathCallback = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "获取的返回码======================" + resultCode);
        switch (requestCode) {
            case REQUEST_CODE_TAKE_PICETURE:
                if (DateTimeUtils.hasSDCard()) {
                    takePictureResult(resultCode);
                }else {
                    Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                }
                break;

            case REQUEST_CODE_PICK_PHOTO:
                takePhotoResult(resultCode, data);

                break;
        }
    }

    private void takePhotoResult(int resultCode, Intent data) {
        if (mFilePathCallback != null) {
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (result != null) {
                String path = GetPathFromUri4kitkat.getPath(this, result);
                Uri uri = Uri.fromFile(new File(path));
                if (Build.VERSION.SDK_INT > 18) {
                    mFilePathCallback.onReceiveValue(new Uri[]{uri});
                } else {
                    mFilePathCallback.onReceiveValue(uri);
                }

            } else {
                mFilePathCallback.onReceiveValue(null);
                mFilePathCallback = null;
            }
        }
    }

    private void takePictureResult(int resultCode) {
        if (mFilePathCallback != null) {
            if (resultCode == RESULT_OK) {
                Uri uri = Uri.fromFile(picturefile);
                if (Build.VERSION.SDK_INT > 18) {
                    mFilePathCallback.onReceiveValue(new Uri[]{uri});
                } else {
                    mFilePathCallback.onReceiveValue(uri);
                }
            } else {
                //点击了file按钮，必须有一个返回值，否则会卡死
                mFilePathCallback.onReceiveValue(null);
                mFilePathCallback = null;
            }
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    @OnClick({R.id.new_webview_back, R.id.ll_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.new_webview_back: //返回
                if (webView != null) {
                    ((ViewGroup) webView.getParent()).removeView(webView);
                    webView.destroy();
                    webView = null;
                }
                finish();
                break;
            case R.id.ll_share:
                break;
        }
    }

}
