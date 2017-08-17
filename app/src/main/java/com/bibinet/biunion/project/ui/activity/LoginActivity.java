package com.bibinet.biunion.project.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.LoginPresenter;
import com.bibinet.biunion.mvp.view.LoginView;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.LoginResultBean;
import com.bibinet.biunion.project.ui.custom.WaitView;
import com.bibinet.biunion.project.ui.dialog.WaitDialog;
import com.bibinet.biunion.project.utils.DialogUtils;
import com.bibinet.biunion.project.utils.PhoneNumberUtils;
import com.bibinet.biunion.project.utils.SharedPresUtils;
import com.bibinet.biunion.project.utils.SoftKeyboardUtils;
import com.bibinet.biunion.project.utils.ViewCheckUtils;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-7.
 */

public class LoginActivity extends BaseActivity implements LoginView {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_imageleft)
    ImageView titleImageleft;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.inputPhoneNumber)
    EditText inputPhoneNumber;
    @BindView(R.id.inputPassword)
    EditText inputPassword;
    @BindView(R.id.act_login_show_password)
    ImageView seePasswrod;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.fastLogin)
    Button fastLogin;

    private WaitDialog waitDialog;

    @OnClick(R.id.act_login_register)
    void register() {
        //注册成功返回结果
        startActivityForResult(new Intent(this, RegistActivity.class), RegistActivity.requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        ViewCheckUtils.inputConfirm(btnLogin, inputPhoneNumber, inputPassword);
        inputPhoneNumber.requestFocus();
        waitDialog = new WaitDialog(this);
    }

    private void initView() {
        title.setText("登录");
    }

    @OnClick({R.id.title_imageleft, R.id.btn_login, R.id.fastLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_imageleft:
                SoftKeyboardUtils.hintKbTwo(this);
                finish();
                break;
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.fastLogin:
                startActivityForResult(new Intent(LoginActivity.this, FastLoginActivity.class), FastLoginActivity.requestCode);
                break;
        }
    }

    //展示密码
    @OnClick(R.id.act_login_show_password)
    void showPassword() {
        int selectIndex = inputPassword.getSelectionEnd();
        if (seePasswrod.isSelected()) {
            seePasswrod.setSelected(false);
            inputPassword.setInputType(129);
        } else {
            seePasswrod.setSelected(true);
            inputPassword.setInputType(145);
        }
        inputPassword.setSelection(selectIndex);
    }

    private void doLogin() {
        LoginPresenter presenter = new LoginPresenter(this);
        String phoneNumber = inputPhoneNumber.getText().toString().trim();
        String phonePassword = inputPassword.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber) && TextUtils.isEmpty(phonePassword)) {
            Toast.makeText(this, "手机号或者密码为空", Toast.LENGTH_SHORT).show();
        } else if (!PhoneNumberUtils.isMobileNumber(phoneNumber)) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
        } else {
            presenter.getLoginInfo(phoneNumber, phonePassword);
        }
    }

    @Override
    public void onLoadSucess(String loginString) {
        Gson gson = new Gson();
        LoginResultBean loginResultInfo = gson.fromJson(loginString, LoginResultBean.class);
        String reslutCode = loginResultInfo.getResCode();
        switch (Integer.parseInt(reslutCode)) {
            case 0000:
                Constants.loginresultInfo = loginResultInfo;
                SharedPresUtils sharedPresUtils = SharedPresUtils.getsSharedPresUtils(this);
                sharedPresUtils.putString("loginResultData", loginString);
                finish();
                break;
            default:
                Toast.makeText(this, loginResultInfo.getResMessage(), Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onLoadFaield(String msg) {
        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RegistActivity.requestCode && resultCode == RegistActivity.resultCode){
            //带回注册成功的手机号
            String phone = data.getStringExtra("phone");
            inputPhoneNumber.setText(phone);
            inputPassword.requestFocus();
            inputPassword.setText("");
        }
        if(requestCode == FastLoginActivity.requestCode && resultCode == FastLoginActivity.resultCode){
            //快速注册成功回调
            finish();
        }
    }
}
