package com.bibinet.biunion.mvp.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.bibinet.biunion.mvp.model.FastLoginModel;
import com.bibinet.biunion.mvp.view.FastLoginView;
import com.bibinet.biunion.project.bean.FastLoginResultBean;
import com.bibinet.biunion.project.bean.FastLoginVerifCodeBean;
import com.bibinet.biunion.project.bean.LoginResultBean;
import com.bibinet.biunion.project.bean.VerifCodeBean;
import com.bibinet.biunion.project.builder.MyCallBack;
import com.google.gson.Gson;

/**
 * Created by bibinet on 2017-6-22.
 */

public class FastLoginPresenter extends BasePresenter{
    private FastLoginModel fastLoginModel;
    private FastLoginView fastLoginView;

    public FastLoginPresenter(FastLoginView fastLoginView) {
        super(fastLoginView);
        this.fastLoginView = fastLoginView;
        this.fastLoginModel = new FastLoginModel();
    }

    public void getVerfiCode(String phone) {
        waitDialog.open();
        fastLoginModel.getVerfiCode(phone, new MyCallBack() {
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson = new Gson();
                FastLoginVerifCodeBean verifCodeInfo = gson.fromJson(s, FastLoginVerifCodeBean.class);
                fastLoginView.onGetVerfCodeSucess(verifCodeInfo);
                waitDialog.close();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                fastLoginView.onGetVerfCodeFailed(throwable.getMessage());
                waitDialog.close();
            }
        });
    }

    public void fastLogin(String phone, String verifCode) {
        waitDialog.open();
        fastLoginModel.FastLogin(phone, verifCode, new MyCallBack() {
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson = new Gson();
                LoginResultBean fastLoginInfo = gson.fromJson(s, LoginResultBean.class);
                fastLoginView.onLoginSucess(fastLoginInfo, s);
                waitDialog.close();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                fastLoginView.OnLoginFailed(throwable.getMessage());
                waitDialog.close();
            }
        });
    }
}
