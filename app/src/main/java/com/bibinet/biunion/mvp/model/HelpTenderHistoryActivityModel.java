package com.bibinet.biunion.mvp.model;

import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.builder.MyCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.bibinet.biunion.mvp.UrlManager.TYPE_PIS;
import static com.bibinet.biunion.mvp.UrlManager.getBaseUrl;

/**
 * Created by bibinet on 2017-6-29.
 */

public class HelpTenderHistoryActivityModel {
    public void helpTenderHistory(String customerId,int pageNumb,MyCallBack myCacheCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_PIS)+"appAssistance/selectPage.json");
        requestParams.addBodyParameter("customerId",customerId);
        requestParams.addBodyParameter("pageNum",String.valueOf(pageNumb));
        x.http().post(requestParams,myCacheCallBack);
    }

    public void delectHistory(String objectId, MyCallBack myCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_PIS)+"appAssistance/delete.json");
        requestParams.addBodyParameter("objectId", objectId);
        x.http().post(requestParams,myCallBack);
    }
}
