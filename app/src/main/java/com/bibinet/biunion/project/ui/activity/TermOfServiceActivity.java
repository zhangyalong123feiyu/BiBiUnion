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

public class TermOfServiceActivity extends WebViewActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_imageright)
    ImageView titleImageright;
    @BindView(R.id.title_imageleft)
    ImageView titleImageleft;




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
        return R.layout.activity_term_of_service;
    }

    private void initView() {
        title.setText("服务条款");
        titleImageleft.setVisibility(View.VISIBLE);
        Intent intent=getIntent();
        String type = intent.getStringExtra("Type");

        getWebView().loadUrl(getBaseUrl(TYPE_PIS) + "appPage/serviceAgreement.jsp");
    }

    @OnClick(R.id.title_imageleft)
    void exit(){
        finish();
    }
}
