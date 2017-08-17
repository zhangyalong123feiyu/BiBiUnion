package com.bibinet.biunion.mvp.model;

import android.os.Environment;

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

public class PrivateInfoShowModel {
    private String cacheName="ProjectInfoShowCache";
//    private File cacheFile=new File(Environment.getExternalStorageDirectory(),"cachefile");
    public void loadPrivateMoreProjectInfo(int pageNum,int customerId,MyCacheCallBack myCacheCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_PIS)+"appMessageSubscription/selectOne.json");
        requestParams.addBodyParameter("pageNum",String.valueOf(pageNum));
        requestParams.addBodyParameter("customerId",String.valueOf(customerId));
        requestParams.setConnectTimeout(1000 * 120);
        requestParams.setReadTimeout(1000 * 120);
        x.http().post(requestParams,myCacheCallBack);
    }
}
