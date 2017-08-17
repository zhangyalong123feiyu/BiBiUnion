package com.bibinet.biunion.mvp.model;

import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.builder.MyCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.bibinet.biunion.mvp.UrlManager.TYPE_IIP;
import static com.bibinet.biunion.mvp.UrlManager.getBaseUrl;

/**
 * Created by bibinet on 2017-6-30.
 */

public class ExpertsAnswerHistoryModel {
    public void getExpertsAnswerData(String userId,int pageNum,MyCallBack myCacheCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_IIP)+"appQuestion/selectPage.json");
        requestParams.addBodyParameter("creator",userId);
        requestParams.addBodyParameter("pageNum",String.valueOf(pageNum));
        x.http().post(requestParams,myCacheCallBack);
    }
    public void deleteAnserData(String objectId,MyCallBack myCacheCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_IIP)+"appQuestion/delete.json");
        requestParams.addBodyParameter("objectId",objectId);
        x.http().post(requestParams,myCacheCallBack);
    }
}
