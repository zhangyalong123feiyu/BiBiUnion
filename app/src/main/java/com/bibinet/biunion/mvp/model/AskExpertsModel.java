package com.bibinet.biunion.mvp.model;

import android.util.Log;

import com.bibinet.biunion.mvp.UrlManager;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.builder.MyCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.bibinet.biunion.mvp.UrlManager.*;

/**
 * Created by bibinet on 2017-6-28.
 */

public class AskExpertsModel {
    public void postQuestionData(String userId,String CompanyId,int type,String expertsCode,String question,String questionContent,MyCallBack myCacheCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_IIP)+"appQuestion/saveQuestion.json");
        requestParams.addBodyParameter("userId",userId);
        requestParams.addBodyParameter("enterpriseId",CompanyId);
        requestParams.addBodyParameter("type",String.valueOf(type));
        requestParams.addBodyParameter("expertCodeStr",expertsCode);
        requestParams.addBodyParameter("title",question);
        requestParams.addBodyParameter("content",questionContent);
        x.http().post(requestParams,myCacheCallBack);
    }
    public void upLoadPayResult(String questionCode,int sucessCode,MyCallBack myCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_IIP)+"appQuestion/savePay.json");
        requestParams.addBodyParameter("questionCode",questionCode);
        requestParams.addBodyParameter("success",String.valueOf(sucessCode));
        Log.i("TAG","sucesscode----------------"+sucessCode);
        x.http().post(requestParams,myCallBack);
    }
    public void getExpertsData(MyCallBack myCallBack){
        RequestParams requestParams=new RequestParams(getBaseUrl(TYPE_IIP)+"appQuestion/expertListPage.json");
        x.http().post(requestParams,myCallBack);
    }
}
