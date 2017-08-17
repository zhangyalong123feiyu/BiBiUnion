package com.bibinet.biunion.mvp.presenter;

import android.util.Log;

import com.bibinet.biunion.mvp.model.UpLoadUserPhotoModel;
import com.bibinet.biunion.mvp.view.UpLoadUserPhotoView;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.LoginResultBean;
import com.bibinet.biunion.project.bean.PrivatePersonDesinBean;
import com.bibinet.biunion.project.builder.MyCallBack;
import com.google.gson.Gson;

/**
 * Created by bibinet on 2017-6-30.
 */

public class UpLoadUserPhotoPresenter{
    private UpLoadUserPhotoView upLoadUserPhotoView;
    private UpLoadUserPhotoModel upLoadUserPhotoModel;

    public UpLoadUserPhotoPresenter(UpLoadUserPhotoView upLoadUserPhotoView) {
        this.upLoadUserPhotoView = upLoadUserPhotoView;
        this.upLoadUserPhotoModel=new UpLoadUserPhotoModel();
    }
    public void upLoadUserPhoto(String userId,String enterpriseId,String logofile){
        Log.e("upd", "userId="+userId+"-enterpriseId="+enterpriseId+"-logofile="+logofile);
        upLoadUserPhotoModel.upLoadUserPhoto(userId,enterpriseId,logofile,new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("-=", "upLoadUserPhoto"+s);
                Gson gson=new Gson();
                try{
                    LoginResultBean loginInfo = gson.fromJson(s, LoginResultBean.class);
                    upLoadUserPhotoView.onUpLoadPhotoSucess(loginInfo, s);
                }catch (Exception e){
                    Log.e("error", "eror="+e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                upLoadUserPhotoView.onUpLoadPhotoFailed(throwable.getMessage());
            }
        });
    }
}
