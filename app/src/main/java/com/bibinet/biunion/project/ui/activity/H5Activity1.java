package com.bibinet.biunion.project.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.UrlManager;
import com.bibinet.biunion.mvp.presenter.H5ActivityPresenter;
import com.bibinet.biunion.mvp.view.H5ActivityView;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.ui.expand.WebViewActivity;

import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bibinet.biunion.mvp.UrlManager.TYPE_PIS;
import static com.bibinet.biunion.mvp.UrlManager.getBaseUrl;

/**
 * Created by bibinet on 2017-6-10.
 */

public class H5Activity1 extends WebViewActivity implements H5ActivityView {
    public final static int requestCode = 203;
    public final static int resultCode = 204;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_imageright)
    ImageView titleImageright;
    @BindView(R.id.title_imageleft)
    ImageView titleImageleft;
    WebView webview;
    @BindView(R.id.title_imageRightFoucs)
    ImageView titleImageRightFoucs;
    private H5ActivityPresenter h5ActivityPresenter;
    private String projectCode;
    private boolean isCollect;
    private int pos;
    private String titleStr;
    private String collectType;

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
        return R.layout.activity_h5_1;
    }

    private void initView() {
        webview = getWebView();
        h5ActivityPresenter = new H5ActivityPresenter(this);
        Intent intent = getIntent();
        String detailUrl = intent.getStringExtra("detailUrl");
        projectCode = intent.getStringExtra("projectCode");
        isCollect = intent.getBooleanExtra("isCollect", false);
        pos = intent.getIntExtra("pos", -1);

        collectType = intent.getStringExtra("collectType");
        titleStr = intent.getStringExtra("detailName");

//        detailUrl = detailUrl.replace("http://192.168.10.18:19180/", getBaseUrl(TYPE_PIS));
        detailUrl = UrlManager.getBaseUrl(UrlManager.TYPE_PIS) + detailUrl;

        webview.loadUrl(detailUrl);
        titleImageleft.setVisibility(View.VISIBLE);
        title.setText(titleStr);

        //初始化收藏按钮
        if (isCollect) {
            titleImageRightFoucs.setSelected(true);
        } else {
            titleImageRightFoucs.setSelected(false);
        }
    }

    private boolean checkLogin() {
        if (Constants.loginresultInfo != null && Constants.loginresultInfo.getUser() != null && Constants.loginresultInfo.getUser().getUserId() != null && !Constants.loginresultInfo.getUser().getUserId().equals("")) {
            return true;
        }
        return false;
    }

    @OnClick({R.id.title_imageleft, R.id.title_imageright, R.id.title_imageRightFoucs})
    public void viewOnclick(View view) {
        switch (view.getId()) {
            case R.id.title_imageleft:
                goBack();
                break;
            case R.id.title_imageright:/*客服*/
                if (!checkLogin()) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                startActivity(new Intent(this, com.easemob.helpdeskdemo.ui.LoginActivity.class));
                break;
            case R.id.title_imageRightFoucs:/*关注*/
                Log.i("TAG", "点击关注");
                if (!checkLogin()) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                if (Constants.loginresultInfo != null) {
                    if (titleImageRightFoucs.isSelected()) {
                        titleImageRightFoucs.setSelected(false);
                        h5ActivityPresenter.cancelFoucs(Integer.parseInt(Constants.loginresultInfo.getUser().getUserId()), projectCode);
                    } else {
                        titleImageRightFoucs.setSelected(true);
                        if (!collectType.equals("-1")) {
                            try {
                                int type = Integer.valueOf(collectType);
                                h5ActivityPresenter.collctionData(Integer.parseInt(Constants.loginresultInfo.getUser().getUserId()), projectCode, type);
                            } catch (Exception e) {

                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "您还没有登录呢!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onCollectoinSucess() {
        Toast.makeText(this, "关注成功", Toast.LENGTH_SHORT).show();
        isCollect = true;
    }

    @Override
    public void onCollectionFailed(String msg) {
        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
        Log.i("TAG", "shibaishoucang" + msg);
    }

    private void goBack() {
        Intent intent = new Intent();
        intent.putExtra("pos", pos);
        intent.putExtra("isCollect", isCollect);
        setResult(resultCode, intent);
        finish();
    }

    @Override
    public void onCancelFoucsSucess() {
        Toast.makeText(this, "取消关注", Toast.LENGTH_SHORT).show();
        isCollect = false;
    }

    @Override
    public void onCancelFoucsFailed() {
        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
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
                goBack();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
