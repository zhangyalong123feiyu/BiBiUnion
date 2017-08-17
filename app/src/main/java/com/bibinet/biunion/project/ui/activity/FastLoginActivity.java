package com.bibinet.biunion.project.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.FastLoginPresenter;
import com.bibinet.biunion.mvp.view.FastLoginView;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.FastLoginResultBean;
import com.bibinet.biunion.project.bean.FastLoginVerifCodeBean;
import com.bibinet.biunion.project.bean.LoginResultBean;
import com.bibinet.biunion.project.ui.dialog.WaitDialog;
import com.bibinet.biunion.project.utils.DialogUtils;
import com.bibinet.biunion.project.utils.PhoneNumberUtils;
import com.bibinet.biunion.project.utils.PublicPopWindowUtils;
import com.bibinet.biunion.project.utils.SharedPresUtils;
import com.bibinet.biunion.project.utils.TimeUtils;
import com.bibinet.biunion.project.utils.ViewCheckUtils;
import com.bibinet.biunion.project.utils.cityselectutil.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-7.
 */

public class FastLoginActivity extends BaseActivity implements FastLoginView, TimeUtils.OnTimeUtilsListener {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_imageright)
    ImageView titleImageright;
    @BindView(R.id.title_imageleft)
    ImageView titleImageleft;
    @BindView(R.id.inputPhoneNumber)
    EditText inputPhoneNumber;
    @BindView(R.id.getVerifCode)
    TextView getVerifCode;
    @BindView(R.id.adverService)
    LinearLayout adverService;
    @BindView(R.id.inputVerifCode)
    EditText inputVerifCode;
    @BindView(R.id.act_fast_login_confirm)
    Button btnLogin;
    private FastLoginPresenter fastLoginPresenter;

    private TimeUtils timeUtils;

    public static final int requestCode = 2000;
    public static final int resultCode = 2002;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastlogin);
        ButterKnife.bind(this);
        initView();
        ViewCheckUtils.inputConfirm(btnLogin, inputPhoneNumber, inputVerifCode);
        timeUtils = new TimeUtils();

        long historyTime = SharedPreferencesUtils.getInstence().getCheckCodeAgainSendTime(this);
        long currentTime = System.currentTimeMillis();
        if (currentTime < historyTime) {
            long time = historyTime - currentTime;
            timeUtils.startCountDownTime(time, 1000, this);
            getVerifCode.setEnabled(false);
        } else {
            getVerifCode.setEnabled(true);
        }
    }

    private void initView() {
        fastLoginPresenter = new FastLoginPresenter(this);
        title.setText("快捷登录");
        titleImageleft.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.title_imageleft, R.id.getVerifCode, R.id.act_fast_login_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_imageleft:
                finish();
                break;
            case R.id.getVerifCode:
                String phone = inputPhoneNumber.getText().toString().trim();
                if (PhoneNumberUtils.isMobileNumber(phone)) {
                    fastLoginPresenter.getVerfiCode(phone);
                } else {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.act_fast_login_confirm:
                String phoneNumb = inputPhoneNumber.getText().toString().trim();
                String VerifCode = inputVerifCode.getText().toString().trim();
                if (TextUtils.isEmpty(VerifCode)) {
                    Toast.makeText(this, "验证码为空", Toast.LENGTH_SHORT).show();
                } else if (!PhoneNumberUtils.isMobileNumber(phoneNumb)) {
                    Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                } else {
                    fastLoginPresenter.fastLogin(phoneNumb, VerifCode);
                }
                break;
        }
    }

    private final long AGAINSENDTIME = 60 * 1000;

    @Override
    public void onGetVerfCodeSucess(FastLoginVerifCodeBean verifCodeInfo) {
        if (verifCodeInfo.getResCode().equals("0000")) {
            Toast.makeText(this, "验证码获取成功", Toast.LENGTH_SHORT).show();

            //保存一段时间后的时间点
            long time = System.currentTimeMillis() + AGAINSENDTIME;
            SharedPreferencesUtils.getInstence().setCheckCodeAgainSendTime(this, time);
            //启动倒计时
            timeUtils.startCountDownTime(AGAINSENDTIME, 1000, this);

        }else{
            Toast.makeText(this, verifCodeInfo.getResMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onGetVerfCodeFailed(String s) {
        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSucess(LoginResultBean bean, String loginString) {
        if(bean.getResCode().equals("0000")){
            Constants.loginresultInfo = bean;
            SharedPresUtils sharedPresUtils = SharedPresUtils.getsSharedPresUtils(this);
            sharedPresUtils.putString("loginResultData", loginString);
            setResult(resultCode);
            finish();
        }else{
            Toast.makeText(this, bean.getResMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //间隔倒计时
    @Override
    public void onTick(long millisUntilFinished) {
        getVerifCode.setText("（" + (millisUntilFinished / 1000) + "）秒后可重发");
        getVerifCode.setEnabled(false);
    }

    //完成
    @Override
    public void onFinish() {
        getVerifCode.setText("获取验证码");
        getVerifCode.setEnabled(true);
    }

    @Override
    public void OnLoginFailed(String s) {
        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
    }


}
