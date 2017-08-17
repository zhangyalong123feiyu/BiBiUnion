package com.bibinet.biunion.mvp.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.bibinet.biunion.mvp.model.RegistModel;
import com.bibinet.biunion.mvp.view.RegistView;
import com.bibinet.biunion.project.bean.BaseBean;
import com.bibinet.biunion.project.bean.VerifCodeBean;
import com.bibinet.biunion.project.builder.MyCallBack;
import com.google.gson.Gson;

/**
 * Created by bibinet on 2017-6-16.
 */

public class RegistPresenter extends BasePresenter{
    private RegistModel registModel;
    private RegistView registView;

    public RegistPresenter(RegistView registView) {
        super(registView);
        this.registView = registView;
        this.registModel = new RegistModel();
    }

    public void getVerifCode(String phone, String type) {
        waitDialog.open();
        registModel.getVerifCode(phone, type, new MyCallBack() {
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson = new Gson();
                VerifCodeBean verifInfo = gson.fromJson(s, VerifCodeBean.class);
                registView.onVerifGetSucess(verifInfo);
                waitDialog.close();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                registView.onVerifGetFailed();
                waitDialog.close();
            }
        });
    }

    public void doRegist(String companyName, String userName, String phone, String verfiCode) {
        waitDialog.open();
        registModel.compeletRegist(companyName, userName, phone, verfiCode, new MyCallBack() {
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("--", "register"+s);
                Gson gson = new Gson();
                BaseBean baseBean = gson.fromJson(s, BaseBean.class);
                registView.onRegistSucess(baseBean);
                waitDialog.close();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                registView.onRegistFailed(throwable.getMessage());
                waitDialog.close();
            }
        });

    }
}
