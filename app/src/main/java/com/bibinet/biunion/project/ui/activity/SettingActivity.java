package com.bibinet.biunion.project.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Configs;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.utils.DataCleanManagerUtils;
import com.bibinet.biunion.project.utils.SharedPresUtils;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-5.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.advicetalk)
    LinearLayout advicetalk;
    @BindView(R.id.clearCache)
    LinearLayout clearCache;
    @BindView(R.id.loginOut)
    Button loginOut;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_imageright)
    ImageView titleImageright;
    @BindView(R.id.title_imageleft)
    ImageView titleImageleft;
    @BindView(R.id.cachesize)
    TextView cachesize;
    private String cachePath = Configs.cachePathMain;
    private File cacheFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
        if(checkLogin()){
            loginOut.setVisibility(View.VISIBLE);
        }else{
            loginOut.setVisibility(View.GONE);
        }
    }

    private boolean checkLogin(){
        if(Constants.loginresultInfo!=null && Constants.loginresultInfo.getUser()!=null && Constants.loginresultInfo.getUser().getUserId() != null && !Constants.loginresultInfo.getUser().getUserId().equals("")){
            return true;
        }
        return false;
    }

    private void initView() {
        title.setText("设置");
        titleImageleft.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cacheFile = new File(cachePath);
        try {
            String sizeInfo = DataCleanManagerUtils.getCacheSize(cacheFile);
            cachesize.setText(sizeInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({ R.id.advicetalk, R.id.clearCache, R.id.loginOut,R.id.title_imageleft})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.advicetalk:
                startActivity(new Intent(this,AdviceSubmitActivity.class));
                break;
            case R.id.clearCache:
                doClearCache();
                Toast.makeText(this, "清除成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.loginOut:
                doLoginOut();
                break;
            case R.id.title_imageleft:
                finish();
                break;
        }
    }

    private void doLoginOut() {
        Constants.loginresultInfo=null;
        SharedPresUtils sharedPresUtils=SharedPresUtils.getsSharedPresUtils(this);
        sharedPresUtils.putString("loginResultData",null);
        if (ChatClient.getInstance().isLoggedInBefore()) {
            //第一个参数为是否解绑推送的devicetoken
            ChatClient.getInstance().logout(false, new Callback() {
                @Override
                public void onSuccess() {
                    Log.e("-=", "onSuccess");
                }

                @Override
                public void onError(int i, String s) {
                    Log.e("-=", "onError");
                }
                @Override
                public void onProgress(int i, String s) {

                }
            });
        }
        finish();
    }

    private void doClearCache() {
        DataCleanManagerUtils.deleteFolderFile(cachePath, false);
        try {
            cachesize.setText(DataCleanManagerUtils.getCacheSize(cacheFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            Configs.createMkdirs();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
