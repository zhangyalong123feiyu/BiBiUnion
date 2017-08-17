package com.bibinet.biunion.mvp.model;

import android.util.Log;

import com.bibinet.biunion.project.application.Configs;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.builder.MyCacheCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.bibinet.biunion.mvp.UrlManager.TYPE_IIP;
import static com.bibinet.biunion.mvp.UrlManager.TYPE_PIS;
import static com.bibinet.biunion.mvp.UrlManager.getBaseUrl;

/**
 * Created by bibinet on 2017-6-19.
 */

public class SearchActivityModel {
    private String cacheName= Configs.cachePathMain;

    //判断是否有id 有就传
    private RequestParams checkId(RequestParams requestParams){
        if(Constants.loginresultInfo!=null && Constants.loginresultInfo.getUser()!=null && Constants.loginresultInfo.getUser().getUserId() != null && !Constants.loginresultInfo.getUser().getUserId().equals("")){
            String id = Constants.loginresultInfo.getUser().getUserId();
            requestParams.addBodyParameter("userId", id);
            return requestParams;
        }
        return requestParams;
    }

    public void searProjectInfoModel(int pageNumb,String content,MyCacheCallBack myCacheCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_PIS)+"appData/selectPage.json");
        Log.e("x-x-x", pageNumb+"-"+content);
        requestParams.addBodyParameter("pageIndex",String.valueOf(pageNumb));
        requestParams.addBodyParameter("messageLike",content);
        requestParams = checkId(requestParams);
        requestParams.setCacheMaxAge(60*60*24*15);
        requestParams.setCacheDirName(cacheName);
        requestParams.setCacheSize(1024*1024*30);
        requestParams.setConnectTimeout(60 * 1000);
        x.http().post(requestParams,myCacheCallBack);
    }
}
