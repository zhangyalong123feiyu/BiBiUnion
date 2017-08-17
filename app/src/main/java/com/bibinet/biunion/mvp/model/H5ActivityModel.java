package com.bibinet.biunion.mvp.model;

import android.util.Log;

import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.builder.MyCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.bibinet.biunion.mvp.UrlManager.TYPE_PIS;
import static com.bibinet.biunion.mvp.UrlManager.getBaseUrl;

/**
 * Created by bibinet on 2017-6-24.
 */

public class H5ActivityModel {
    public void collctionData(int userId,String projectCode,int type,MyCallBack myCacheCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_PIS)+"appCollection/collect.json");
        requestParams.addBodyParameter("userId",String.valueOf(userId));
        requestParams.addBodyParameter("relatedCode",projectCode);
        requestParams.addBodyParameter("type",String.valueOf(type));
        x.http().post(requestParams,myCacheCallBack);
    }
    public void cancelFoucs(int userId,String relatedCode,MyCallBack myCallBack){
        Log.e("-=" , userId+"-"+relatedCode+"-");
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_PIS)+"appCollection/cancellCollect.json");
        requestParams.addBodyParameter("userId",String.valueOf(userId));
        requestParams.addBodyParameter("relatedCode",relatedCode);
        x.http().post(requestParams,myCallBack);
    }
}
