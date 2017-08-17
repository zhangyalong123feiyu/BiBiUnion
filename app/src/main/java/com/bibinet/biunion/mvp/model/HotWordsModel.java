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

public class HotWordsModel {
    public void getHotWords(MyCallBack myCacheCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_PIS)+"appKeywords/selectKeywords.json");
        x.http().post(requestParams,myCacheCallBack);
    }
}
