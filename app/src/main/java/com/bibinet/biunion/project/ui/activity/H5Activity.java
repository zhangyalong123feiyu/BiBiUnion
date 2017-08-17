package com.bibinet.biunion.project.ui.activity;

import android.content.Intent;
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
import com.bibinet.biunion.project.bean.ProjectInfoBean;
import com.bibinet.biunion.project.ui.expand.WebViewActivity;
import com.easemob.helpdeskdemo.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bibinet.biunion.mvp.UrlManager.TYPE_PIS;
import static com.bibinet.biunion.mvp.UrlManager.getBaseUrl;

/**
 * Created by bibinet on 2017-6-10.
 */

public class H5Activity extends WebViewActivity implements H5ActivityView {
    public final static int requestCode = 200;
    public final static int resultCode = 201;
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
    private String selcType;
    private String detaiType;
    private boolean isCollect;
    private int pos;

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
        return R.layout.activity_h5;
    }

    private void initView() {
        h5ActivityPresenter = new H5ActivityPresenter(this);
        Intent intent = getIntent();
        String detailUrl = intent.getStringExtra("detailUrl");
        selcType = intent.getStringExtra("selectType");
        detaiType = intent.getStringExtra("detailType");
        projectCode = intent.getStringExtra("projectCode");
        isCollect = intent.getBooleanExtra("isCollect", false);
        pos = intent.getIntExtra("pos", -1);

        webview = getWebView();
        switch (Integer.parseInt(selcType)) {
            case 5:
                switch (Integer.parseInt(detaiType)) {
                    case 1:
                        title.setText("拟在建项目详情");
                        break;
                    case 2:
                        title.setText("业主委托项目详情");
                        break;
                    default:
                        break;
                }
                break;
            case 6:
                switch (Integer.parseInt(detaiType)) {
                    case 1:
                        title.setText("招标公告详情");
                        break;
                    case 2:
                        title.setText("中标候选人公示详情");
                        break;
                    case 3:
                        title.setText("中标公告详情");
                        break;
                    default:
                        break;
                }
                break;
            case 7:
                switch (Integer.parseInt(detaiType)) {
                    case 1:
                        title.setText("政府采购详情");
                        break;
                    case 2:
                        title.setText("业主采购详情");
                        break;
                    default:
                        break;
                }
                break;
            case 8:
                title.setText("ppp项目详情");
                break;
            case 9:
                switch (Integer.parseInt(detaiType)) {
                    case 1:
                        title.setText("供应商详情");
                        break;
                    case 2:
                        title.setText("采购业主详情");
                        break;
                    case 3:
                        title.setText("招标机构详情");
                        break;
                    default:
                        break;
                }
                break;

            default:
                break;
        }
//        detailUrl = detailUrl.replace("http://192.168.10.18:19180/", getBaseUrl(TYPE_PIS));
        detailUrl = UrlManager.getBaseUrl(UrlManager.TYPE_PIS) + detailUrl;
        Log.e("0-0", detailUrl + "-=");
        webview.loadUrl(detailUrl);
        titleImageleft.setVisibility(View.VISIBLE);


        //初始化收藏按钮
        if(isCollect){
            titleImageRightFoucs.setSelected(true);
        }else{
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
                        h5ActivityPresenter.collctionData(Integer.parseInt(Constants.loginresultInfo.getUser().getUserId()), projectCode, getType());
                    }
                } else {
                    Toast.makeText(this, "您还没有登录呢!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private int getType() {
        switch (Integer.valueOf(selcType)){
            case 5:
                return 2;
            case 6:
                return 1;
            case 7:
                return 4;
            case 8:
                return 3;
        }
        return 1;
    }

    @Override
    public void onCollectoinSucess() {
        Toast.makeText(this, "关注成功", Toast.LENGTH_SHORT).show();
        isCollect = true;
    }

    @Override
    public void onCollectionFailed(String msg) {
        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
        Log.e("TAG", "shibaishoucang" + msg);
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

    private void goBack(){
        Intent intent = new Intent();
        intent.putExtra("pos", pos);
        intent.putExtra("isCollect", isCollect);
        setResult(resultCode, intent);
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
                goBack();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
