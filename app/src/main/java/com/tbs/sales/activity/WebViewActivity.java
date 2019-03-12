package com.tbs.sales.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.utils.AppInfoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViewEvent();
    }

    private void initViewEvent() {
        mIntent = getIntent();
        newWebviewBannerRl.setBackgroundColor(Color.parseColor("#ffffff"));
        mLoadingUrl = mIntent.getStringExtra("mLoadingUrl");
        if (mLoadingUrl.contains("?")) {
            mLoadingUrl = mLoadingUrl + "&token=" + AppInfoUtils.getToekn(this);
        } else {
            mLoadingUrl = mLoadingUrl + "?token=" + AppInfoUtils.getToekn(this);
        }
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

        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
        webView.loadUrl(mLoadingUrl);
    }

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };
    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            newWebviewTitle.setText(title);
        }
    };

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
                finish();
                break;
            case R.id.ll_share:
                break;
        }
    }
}
