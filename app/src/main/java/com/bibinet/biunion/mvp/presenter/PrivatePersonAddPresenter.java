package com.bibinet.biunion.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.bibinet.biunion.mvp.model.PrivatePersonDesignModel;
import com.bibinet.biunion.mvp.view.PrivatePersonDesinView;
import com.bibinet.biunion.project.bean.BaseBean;
import com.bibinet.biunion.project.bean.PrivatePersonDesinBean;
import com.bibinet.biunion.project.builder.MyCallBack;
import com.google.gson.Gson;

/**
 * Created by bibinet on 2017-6-27.
 */

public class PrivatePersonAddPresenter extends BasePresenter{
    private PrivatePersonDesignModel privatePersonDesignModel;
    private PrivatePersonDesinView privatePersonDesinView;

    public PrivatePersonAddPresenter(Context context, PrivatePersonDesinView privatePersonDesinView) {
        super(context);
        this.privatePersonDesinView = privatePersonDesinView;
        this.privatePersonDesignModel=new PrivatePersonDesignModel();
    }
    public void onPostPrivatePersonData(int customerId,String infoType,String projectType,String regionCode){
        privatePersonDesignModel.psotDesinInfo(customerId,infoType,projectType,regionCode,new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson = new Gson();
                BaseBean bean = gson.fromJson(s, BaseBean.class);
                privatePersonDesinView.onDesinSucess(bean);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                privatePersonDesinView.onDesinFailed(throwable.getMessage());

            }
        });
    }

    public void getPersonDesinPresenter(int customerId){
        waitDialog.open();
        privatePersonDesignModel.getPostDesinInfo(customerId, new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("-=", "--------_+++--"+s);
                waitDialog.close();
                Gson gson = new Gson();
                PrivatePersonDesinBean bean = gson.fromJson(s, PrivatePersonDesinBean.class);
                privatePersonDesinView.onGetDesinSucess(bean);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                waitDialog.close();
                privatePersonDesinView.onGetDesinFailed(throwable.getMessage());
                Log.e("-=", "--------------"+throwable.getMessage());
            }
        });
    }
}
