package com.bibinet.biunion.project.ui.expand;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.ui.config.WebViewClientConfig;
import com.bibinet.biunion.project.ui.custom.WaitView;
import com.bibinet.biunion.project.utils.WaitUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bibinet.biunion.mvp.UrlManager.TYPE_PIS;
import static com.bibinet.biunion.mvp.UrlManager.getBaseUrl;

/**
 * Created by bibinet on 2017-6-15.
 */

public abstract class WebViewActivity extends BaseActivity {

    private WaitView waitV;
    private View errorV;
    private FrameLayout mainV;

    private WebView webview;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mainV = (FrameLayout) findViewById(R.id.act_web_view_main);
        errorV = findViewById(R.id.act_about_us_error_view);
        waitV = (WaitView) findViewById(R.id.act_about_us_wait_view);

        View v = LayoutInflater.from(this).inflate(getLayoutId(), null, false);
        webview = (WebView) v.findViewById(getWebViewId());
        initWebView();
        mainV.addView(v);
        ButterKnife.bind(this);

        onChildCreate(savedInstanceState);
    }

    protected abstract void onChildCreate(Bundle savedInstanceState);

    protected abstract int getWebViewId();

    protected abstract int getLayoutId();

    private void initWebView() {
        webview.setWebViewClient(new WebViewClient() {
            //覆写shouldOverrideUrlLoading实现内部显示网页
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO 自动生成的方法存根
                view.loadUrl(url);
                return true;
            }
        });

        WebSettings seting = webview.getSettings();
        seting.setJavaScriptEnabled(true);//设置webview支持javascript脚本
        webview.setVerticalScrollbarOverlay(true); //指定的垂直滚动条有叠加样式
        seting.setUseWideViewPort(true);//设定支持viewport
        seting.setLoadWithOverviewMode(true);
//        seting.setBuiltInZoomControls(true);
        seting.setSupportZoom(true);//设定支持缩放
        webview.setWebViewClient(new WebViewClientConfig(this, webview, new WaitUtils(waitV), errorV));
    }

    //设置返回键动作（防止按返回键直接退出程序)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO 自动生成的方法存根
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webview.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                webview.goBack();
                return true;
            } else {
                //    System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    protected WebView getWebView(){
        return webview;
    }
}
