package com.tbs.sales.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tbs.sales.R;
import com.tbs.sales.bean.Event;
import com.tbs.sales.constant.Constant;
import com.tbs.sales.utils.AppInfoUtils;
import com.tbs.sales.utils.EC;
import com.tbs.sales.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Mr.Wang on 2019/2/27 13:29.
 * 简报
 */
public class HomeBriefingFragment extends BaseFragment implements View.OnTouchListener {
    @BindView(R.id.web_view)
    WebView webView;
    Unbinder unbinder;
//    private boolean b = true;
    private DisplayMetrics dm;
    private WindowManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_briefing, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public boolean isRegisterEventBus() {
        return true;
    }

    @Subscribe
    @Override
    public void receiveEvent(Event event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case EC.EventCode.UPDATE_HOME_DATA:
//                b = true;
                webView.loadUrl(Constant.WXDISTRIBUTE_CUSTOMER_COUNT);
                break;
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        dm = new DisplayMetrics();
        manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);

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
        String appCachePath = getActivity().getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setOnTouchListener(this);

//        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(Constant.WXDISTRIBUTE_CUSTOMER_COUNT);
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
        String val = AppInfoUtils.getToekn(getActivity());
        String key2 = "user_id";
        String val2 = AppInfoUtils.getId(getActivity());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("window.localStorage.setItem('" + key + "','" + val + "');", null);
            webView.evaluateJavascript("window.localStorage.setItem('" + key2 + "','" + val2 + "');", null);
        } else {
            webView.loadUrl("javascript:localStorage.setItem('" + key + "','" + val + "');");
            webView.loadUrl("javascript:localStorage.setItem('" + key2 + "','" + val2 + "');");
        }
//        if (b) {
//            webView.reload();
//            b = false;
//        }
    }
//    private WebChromeClient webChromeClient = new WebChromeClient() {
//        @Override
//        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//            if (!TextUtils.isEmpty(AppInfoUtils.getId(getActivity()))){
//                ToastUtils.toastShort(getActivity(),message);
//            }
//            return super.onJsAlert(view, url, message, result);
//        }
//
//        @Override
//        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
//            if (!TextUtils.isEmpty(AppInfoUtils.getId(getActivity()))){
//                ToastUtils.toastShort(getActivity(),message);
//            }
//            return super.onJsPrompt(view, url, message, defaultValue, result);
//        }
//    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 后退
     */
    public void onKeyBack() {
        webView.goBack();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                int point = (int) event.getX();

                if (point > 0 && point < 50 || point > dm.widthPixels - 50 && point < dm.widthPixels) {

                    webView.requestDisallowInterceptTouchEvent(false);

                } else {

                    webView.requestDisallowInterceptTouchEvent(true);

                }

                break;

        }

        return false;

    }
}
