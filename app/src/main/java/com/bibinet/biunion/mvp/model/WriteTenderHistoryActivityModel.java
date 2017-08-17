package com.bibinet.biunion.mvp.model;

import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.builder.MyCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.bibinet.biunion.mvp.UrlManager.TYPE_PIS;
import static com.bibinet.biunion.mvp.UrlManager.getBaseUrl;

/**
 * Created by bibinet on 2017-6-26.
 */

public class WriteTenderHistoryActivityModel {
    public void writeTenderHistory(String cellPhone,String customerId,String pageNum ,MyCallBack myCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_PIS)+"appProxyTender/selectPage.json");
        requestParams.addBodyParameter("customerId",customerId);
        requestParams.addBodyParameter("pageNum",pageNum );
        x.http().post(requestParams,myCallBack);
    }

    public void delectHistory(String objectId, MyCallBack myCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_PIS)+"appProxyTender/delete.json");
        requestParams.addBodyParameter("objectId", objectId);
        x.http().post(requestParams,myCallBack);
    }
}
