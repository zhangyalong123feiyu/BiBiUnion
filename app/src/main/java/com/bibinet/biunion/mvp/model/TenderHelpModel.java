package com.bibinet.biunion.mvp.model;

import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.builder.MyCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.bibinet.biunion.mvp.UrlManager.TYPE_IIP;
import static com.bibinet.biunion.mvp.UrlManager.TYPE_PIS;
import static com.bibinet.biunion.mvp.UrlManager.getBaseUrl;

/**
 * Created by bibinet on 2017-6-23.
 */

public class TenderHelpModel {
    public void upLoadData(String contact, String cellPhone, String content,String customerId, MyCallBack myCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_PIS)+"appAssistance/apply.json");
        requestParams.addBodyParameter("contact",contact);
        requestParams.addBodyParameter("cellPhone",cellPhone);
        requestParams.addBodyParameter("content",content);
        requestParams.addBodyParameter("customerId",customerId);
        x.http().post(requestParams,myCallBack);
    }
}
