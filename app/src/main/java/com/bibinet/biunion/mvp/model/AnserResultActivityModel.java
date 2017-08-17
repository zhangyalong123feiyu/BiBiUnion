package com.bibinet.biunion.mvp.model;

import com.bibinet.biunion.project.builder.MyCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.bibinet.biunion.mvp.UrlManager.TYPE_IIP;
import static com.bibinet.biunion.mvp.UrlManager.getBaseUrl;

/**
 * Created by bibinet on 2017-7-27.
 */

public class AnserResultActivityModel {
    public void getPostRating(String objectId,MyCallBack myCacheCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_IIP)+"appQuestion/toDetail.json");
        requestParams.addBodyParameter("objectId",objectId);
        x.http().get(requestParams,myCacheCallBack);
    }
    public void postRating(String answerCode,int commentLevel,String commentContent,String userId,MyCallBack myCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_IIP)+"appQuestion/comment.json");
        requestParams.addBodyParameter("answerCode",answerCode);
        requestParams.addBodyParameter("commentLevel",String.valueOf(commentLevel));
        requestParams.addBodyParameter("commentContent",commentContent);
        requestParams.addBodyParameter("creator",userId);
        x.http().post(requestParams,myCallBack);
    }
}
