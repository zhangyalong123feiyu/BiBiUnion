package com.bibinet.biunion.mvp.model;

import android.util.Log;

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

public class FoucsActivityModel {
    public void getFoucsData(int userid,int index,MyCallBack myCacheCallBack){
        Log.e("x-x-", userid+"--"+index);
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_PIS)+"appCollection/selectPage.json");
        requestParams.addBodyParameter("userId",String.valueOf(userid));
        requestParams.addBodyParameter("pageNum",String.valueOf(index));
        x.http().post(requestParams,myCacheCallBack);
    }
}
