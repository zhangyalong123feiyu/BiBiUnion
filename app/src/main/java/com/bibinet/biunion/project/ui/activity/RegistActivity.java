package com.bibinet.biunion.project.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.RegistPresenter;
import com.bibinet.biunion.mvp.view.RegistView;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.bean.BaseBean;
import com.bibinet.biunion.project.bean.VerifCodeBean;
import com.bibinet.biunion.project.utils.DialogUtils;
import com.bibinet.biunion.project.utils.PhoneNumberUtils;
import com.bibinet.biunion.project.utils.TimeUtils;
import com.bibinet.biunion.project.utils.ViewCheckUtils;
import com.bibinet.biunion.project.utils.cityselectutil.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-15.
 */

public class RegistActivity extends BaseActivity implements RegistView, TimeUtils.OnTimeUtilsListener {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_imageright)
    ImageView titleImageright;
    @BindView(R.id.title_imageleft)
    ImageView titleImageleft;
    @BindView(R.id.inputCompanyName)
    EditText inputCompanyName;
    @BindView(R.id.inputUserName)
    EditText inputUserName;
    @BindView(R.id.inputPhone)
    EditText inputPhone;
    @BindView(R.id.inputVerifCode)
    EditText inputVerifCode;
    @BindView(R.id.verifCodeBtn)
    Button verifCodeBtn;
    @BindView(R.id.completeRegist)
    Button completeRegist;
    private RegistPresenter registPresenter;

    public final static int resultCode = 1100;
    public final static int requestCode = 1101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        initView();
        //检测
        ViewCheckUtils.inputConfirm(completeRegist, inputCompanyName, inputUserName, inputPhone, inputVerifCode);

        timeUtils = new TimeUtils();

        long historyTime = SharedPreferencesUtils.getInstence().getCheckCodeAgainSendTime(this);
        long currentTime = System.currentTimeMillis();
        if(currentTime<historyTime){
            long time = historyTime - currentTime;
            timeUtils.startCountDownTime(time, 1000, this);
            verifCodeBtn.setEnabled(false);
        }else{
            verifCodeBtn.setEnabled(true);
        }
    }

    private void initView() {
        titleImageleft.setVisibility(View.VISIBLE);
        registPresenter=new RegistPresenter(this);
        title.setText("注册");
    }

    @OnClick({R.id.title_imageleft, R.id.verifCodeBtn, R.id.completeRegist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_imageleft:
                finish();
                break;
            case R.id.verifCodeBtn:
                String phoneNumb = inputPhone.getText().toString().trim();
                if (PhoneNumberUtils.isMobileNumber(phoneNumb)) {
                    registPresenter.getVerifCode(phoneNumb,"1");
                }else {
                    Toast.makeText(this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.completeRegist:
                phoneNumb=inputPhone.getText().toString().trim();
                String companyName = inputCompanyName.getText().toString().trim();
                String userName = inputUserName.getText().toString().trim();
                String verifCode = inputVerifCode.getText().toString().trim();
                if (TextUtils.isEmpty(companyName)) {
                    Toast.makeText(this,"公司名为空",Toast.LENGTH_SHORT).show();
                		}else if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(this,"用户名为空",Toast.LENGTH_SHORT).show();
                				}else if (TextUtils.isEmpty(verifCode)) {
                    Toast.makeText(this,"验证码为空",Toast.LENGTH_SHORT).show();
                						}else {
                    registPresenter.doRegist(companyName,userName,phoneNumb,verifCode);
                }
                break;
        }
    }



    private TimeUtils timeUtils;

    private final long AGAINSENDTIME = 60 * 1000;

    @Override
    public void onVerifGetSucess(VerifCodeBean verifInfo) {
        if (verifInfo.getModelAndView().getModel().getResCode().equals("0000")) {
            Toast.makeText(this,"验证码获取成功",Toast.LENGTH_SHORT).show();

            //保存一段时间后的时间点
            long time = System.currentTimeMillis() + AGAINSENDTIME;
            SharedPreferencesUtils.getInstence().setCheckCodeAgainSendTime(this, time);
            //启动倒计时
            timeUtils.startCountDownTime(AGAINSENDTIME, 1000, this);

        }else{
            //失败了可以直接重发 不用等
            Toast.makeText(this, verifInfo.getResMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onVerifGetFailed() {
        Toast.makeText(this,"网络异常",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegistSucess(BaseBean baseBean) {
        if(baseBean.getResCode().equals("0000")){
            Toast.makeText(this, "注册成功！您的密码稍后会为您发送至当前手机号，请注意查收", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.putExtra("phone", inputPhone.getText().toString());
            setResult(resultCode, intent);
            finish();
//            startActivity(new Intent(this,LoginActivity.class));
        }else{
            Toast.makeText(this, baseBean.getResMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //间隔倒计时
    @Override
    public void onTick(long millisUntilFinished) {
        verifCodeBtn.setText("（"+(millisUntilFinished/1000)+"）秒后可重发");
        verifCodeBtn.setEnabled(false);
    }
    //完成
    @Override
    public void onFinish() {
        verifCodeBtn.setText("获取验证码");
        verifCodeBtn.setEnabled(true);
    }

    @Override
    public void onRegistFailed(String s) {
        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
    }
}
