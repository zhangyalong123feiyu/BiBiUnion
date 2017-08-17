package com.bibinet.biunion.mvp.model;

import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.builder.MyCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.bibinet.biunion.mvp.UrlManager.TYPE_IIP;
import static com.bibinet.biunion.mvp.UrlManager.TYPE_PIS;
import static com.bibinet.biunion.mvp.UrlManager.getBaseUrl;

/**
 * Created by bibinet on 2017-6-16.
 */

public class RegistModel {
    public void getVerifCode(String phone,String type,MyCallBack myCacheCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_IIP)+"user/sendSMS.json");
        requestParams.addBodyParameter("cellPhone",phone);
        requestParams.addBodyParameter("type",type);
        x.http().post(requestParams,myCacheCallBack);
    }
    public void compeletRegist(String companyName,String userName,String phone,String verfiCode,MyCallBack myCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_IIP)+"user/appRegist.json");
        requestParams.addBodyParameter("enterpriseName",companyName);
        requestParams.addBodyParameter("userName",userName);
        requestParams.addBodyParameter("cellPhone",phone);
        requestParams.addBodyParameter("checkCode",verfiCode);
        x.http().post(requestParams,myCallBack);
    }
}
