package com.bibinet.biunion.mvp.model;

import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.builder.MyCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.bibinet.biunion.mvp.UrlManager.TYPE_IIP;
import static com.bibinet.biunion.mvp.UrlManager.TYPE_PIS;
import static com.bibinet.biunion.mvp.UrlManager.getBaseUrl;

/**
 * Created by bibinet on 2017-6-30.
 */

public class UpLoadUserPhotoModel {
    public void upLoadUserPhoto(String userId,String enterpriseId,String logofile,MyCallBack myCacheCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_IIP)+"user/updateLogoFile.json");
        requestParams.addBodyParameter("userId",userId);
        requestParams.addBodyParameter("enterpriseId",enterpriseId);
        requestParams.addBodyParameter("logofile",logofile);
        x.http().post(requestParams,myCacheCallBack);
    }
}
