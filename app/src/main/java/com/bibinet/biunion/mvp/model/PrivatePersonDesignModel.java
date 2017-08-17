package com.bibinet.biunion.mvp.model;

import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.builder.MyCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.bibinet.biunion.mvp.UrlManager.TYPE_PIS;
import static com.bibinet.biunion.mvp.UrlManager.getBaseUrl;

/**
 * Created by bibinet on 2017-6-27.
 */

public class PrivatePersonDesignModel {
    public void psotDesinInfo(int customerId,String infoType,String projectType,String regionCode,MyCallBack myCacheCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_PIS)+"appMessageSubscription/saveMessage.json");
        requestParams.addBodyParameter("customerId",String.valueOf(customerId));
        requestParams.addBodyParameter("infoType",infoType);
        requestParams.addBodyParameter("projectType",projectType);
        requestParams.addBodyParameter("regionCode",String.valueOf(regionCode));
        x.http().post(requestParams,myCacheCallBack);
    }

    public void getPostDesinInfo(int customerId, MyCallBack myCacheCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_PIS)+"appMessageSubscription/selectOneConverter.json");
        requestParams.addBodyParameter("customerId",String.valueOf(customerId));
        x.http().post(requestParams,myCacheCallBack);
    }
}
