package com.bibinet.biunion.project.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.ui.expand.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bibinet.biunion.mvp.UrlManager.TYPE_PIS;
import static com.bibinet.biunion.mvp.UrlManager.getBaseUrl;

/**
 * Created by bibinet on 2017-6-15.
 */

public class PlatFormActivity extends WebViewActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_imageright)
    ImageView titleImageright;
    @BindView(R.id.title_imageleft)
    ImageView titleImageleft;


    WebView webview;



    @Override
    protected void onChildCreate(Bundle savedInstanceState) {
        initView();
    }

    @Override
    protected int getWebViewId() {
        return R.id.webview;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_platform;
    }

    private void initView() {
        titleImageleft.setVisibility(View.VISIBLE);
        webview = getWebView();
        WebSettings seting = webview.getSettings();
        seting.setJavaScriptEnabled(true);//设置webview支持javascript脚本
        webview.setVerticalScrollbarOverlay(true); //指定的垂直滚动条有叠加样式
        seting.setUseWideViewPort(true);//设定支持viewport
        seting.setLoadWithOverviewMode(true);
        seting.setBuiltInZoomControls(true);
        seting.setSupportZoom(true);//设定支持缩放
        Intent intent = getIntent();
        String type = intent.getStringExtra("Type");
        switch (Integer.parseInt(type)) {
            case 1:
                webview.loadUrl(getBaseUrl(TYPE_PIS) + "appPage/detail.jsp");
                title.setText("比比招标采购网");
                break;
            case 2:
                webview.loadUrl(getBaseUrl(TYPE_PIS) + "appPage/detail1.jsp");
                title.setText("企业招标采购数字平台");
                break;
            case 3:
                webview.loadUrl(getBaseUrl(TYPE_PIS) + "appPage/detail2.jsp");
                title.setText("比比招投标金融服务平台");
                break;
            case 4:
                webview.loadUrl(getBaseUrl(TYPE_PIS) + "appPage/detail3.jsp");
                title.setText("比比服务中心");
                break;

            default:
                break;
        }

    }

    @OnClick(R.id.title_imageleft)
    void exit() {
        finish();
    }

    //设置返回键动作（防止按返回键直接退出程序)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO 自动生成的方法存根
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webview.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                webview.goBack();
                return true;
            } else {//当webview处于第一页面时,直接退出程序
                //    System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.title_imageleft)
    public void onViewClicked() {
        finish();
    }
}
