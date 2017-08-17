package com.bibinet.biunion.mvp.model;

import android.os.Environment;

import com.bibinet.biunion.project.application.Configs;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.builder.MyCacheCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import static com.bibinet.biunion.mvp.UrlManager.TYPE_PIS;
import static com.bibinet.biunion.mvp.UrlManager.getBaseUrl;

/**
 * Created by bibinet on 2017-6-14.
 */

public class MoreProjectModel {
    //判断是否有id 有就传
    private RequestParams checkId(RequestParams requestParams){
        if(Constants.loginresultInfo!=null && Constants.loginresultInfo.getUser()!=null && Constants.loginresultInfo.getUser().getUserId() != null && !Constants.loginresultInfo.getUser().getUserId().equals("")){
            String id = Constants.loginresultInfo.getUser().getUserId();
            requestParams.addBodyParameter("userId", id);
            return requestParams;
        }
        return requestParams;
    }
    private String cacheName= Configs.cachePathMain + "/ProjectInfoCache.json";
    public void LoadHomeDataMoreProjectInfo(int pageNum,int _type,int dateRange,String trad,int provinceId,MyCacheCallBack myCacheCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_PIS)+"generalProjects/selectPage.json");
        requestParams.addBodyParameter("pageNum",String.valueOf(pageNum));
        requestParams.addBodyParameter("_type",String.valueOf(_type));
        requestParams.addBodyParameter("dateRange",String.valueOf(dateRange));
        requestParams.addBodyParameter("tradeFirst",trad);
        requestParams.addBodyParameter("provinceId",String.valueOf(provinceId));
        requestParams = checkId(requestParams);
//        requestParams.setCacheMaxAge(60*60*24*15);
//        requestParams.setCacheDirName(cacheName);
//        requestParams.setCacheSize(1024*1024*30);
//        requestParams.setMaxRetryCount(2);
        x.http().post(requestParams,myCacheCallBack);
    }

    public void LoadHomeDataMoreTenderInfo(int pageNum,int _type,int dateRange,String trad,int provinceId,MyCacheCallBack myCacheCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_PIS)+"biddingInfos/selectPage.json");
        requestParams.addBodyParameter("pageNum",String.valueOf(pageNum));
        requestParams.addBodyParameter("_type",String.valueOf(_type));
        requestParams.addBodyParameter("dateRange",String.valueOf(dateRange));
        requestParams.addBodyParameter("tradeFirst",trad);
        requestParams.addBodyParameter("provinceId",String.valueOf(provinceId));
        requestParams = checkId(requestParams);
//        requestParams.setCacheMaxAge(60*60*24*15);
//        requestParams.setCacheDirName(cacheName);
//        requestParams.setCacheSize(1024*1024*30);
//        requestParams.setMaxRetryCount(2);
        x.http().post(requestParams,myCacheCallBack);
    }
    public void LoadHomeDataMoreBuyInfo(int pageNum,int _type,int dateRange,String trad,int provinceId,MyCacheCallBack myCacheCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_PIS)+"purchaseInfos/selectPage.json");
        requestParams.addBodyParameter("pageNum",String.valueOf(pageNum));
        requestParams.addBodyParameter("_type",String.valueOf(_type));
        requestParams.addBodyParameter("dateRange",String.valueOf(dateRange));
        requestParams.addBodyParameter("tradeFirst",trad);
        requestParams.addBodyParameter("provinceId",String.valueOf(provinceId));
        requestParams = checkId(requestParams);
//        requestParams.setCacheMaxAge(60*60*24*15);
//        requestParams.setCacheDirName(cacheName);
//        requestParams.setCacheSize(1024*1024*30);
//        requestParams.setMaxRetryCount(2);
        x.http().post(requestParams,myCacheCallBack);
    }
    public void LoadHomeDataMorePpProjectInfo(int pageNum,int _type,int dateRange,String trad,int provinceId,MyCacheCallBack myCacheCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_PIS)+"pppProjects/selectPage.json");
        requestParams.addBodyParameter("pageNum",String.valueOf(pageNum));
        requestParams.addBodyParameter("dateRange",String.valueOf(dateRange));
        requestParams.addBodyParameter("tradeFirst",trad);
        requestParams.addBodyParameter("provinceId",String.valueOf(provinceId));
        requestParams = checkId(requestParams);
//        requestParams.setCacheMaxAge(60*60*24*15);
//        requestParams.setCacheDirName(cacheName);
//        requestParams.setCacheSize(1024*1024*30);
//        requestParams.setMaxRetryCount(2);
        x.http().post(requestParams,myCacheCallBack);
    }
    public void LoadHomeDataMoreApplayProjectInfo(int pageNum,int _type,int dateRange,String trad,int provinceId,MyCacheCallBack myCacheCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_PIS)+"portalUsers/selectPage.json");
        requestParams.addBodyParameter("pageNum",String.valueOf(pageNum));
        requestParams.addBodyParameter("dateRange",String.valueOf(dateRange));
        requestParams.addBodyParameter("tradeFirst",trad);
        requestParams.addBodyParameter("provinceId",String.valueOf(provinceId));
        requestParams = checkId(requestParams);
//        requestParams.setCacheMaxAge(60*60*24*15);
//        requestParams.setCacheDirName(cacheName);
//        requestParams.setCacheSize(1024*1024*30);
//        requestParams.setMaxRetryCount(2);
        x.http().post(requestParams,myCacheCallBack);
    }
}
