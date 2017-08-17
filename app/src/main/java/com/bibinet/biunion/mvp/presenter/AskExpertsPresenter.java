package com.bibinet.biunion.mvp.presenter;

import android.util.Log;

import com.bibinet.biunion.mvp.model.AskExpertsModel;
import com.bibinet.biunion.mvp.view.AskExpertsActivityView;
import com.bibinet.biunion.project.bean.AskExpertsBean;
import com.bibinet.biunion.project.bean.ExpertsDataBean;
import com.bibinet.biunion.project.bean.ProjectInfoBean;
import com.bibinet.biunion.project.builder.MyCallBack;
import com.google.gson.Gson;

/**
 * Created by bibinet on 2017-6-28.
 */

public class AskExpertsPresenter extends BasePresenter{
    private AskExpertsActivityView askExpertsPresenterView;
    private AskExpertsModel askExpertsModel;

    public AskExpertsPresenter(AskExpertsActivityView askExpertsPresenterView) {
        super(askExpertsPresenterView);
        this.askExpertsPresenterView = askExpertsPresenterView;
        this.askExpertsModel=new AskExpertsModel();
    }
    public void postAskExpertsData(String userId,String CompanyId,int type,String expertsCode,String question,String questionContent){
        waitDialog.open();
        askExpertsModel.postQuestionData(userId,CompanyId,type,expertsCode,question,questionContent,new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                AskExpertsBean askExpertsBean = gson.fromJson(s, AskExpertsBean.class);
                askExpertsPresenterView.onPostQuestionDataSucess(askExpertsBean);
                waitDialog.close();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                askExpertsPresenterView.onPostQuestionDataFailed(throwable.getMessage());
                waitDialog.close();
            }
        });
    }
    public void getExpertsData(){
        waitDialog.open();
        askExpertsModel.getExpertsData(new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                waitDialog.close();
                Gson gson=new Gson();
                ExpertsDataBean expertsDataInfo = gson.fromJson(s, ExpertsDataBean.class);
                askExpertsPresenterView.onGetExpertsDataSucess(expertsDataInfo.getItems());

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                waitDialog.close();
                askExpertsPresenterView.onGetExpertsDataFailed(throwable.getMessage());
            }
        });
    }
    public void upLoadPayResult(String questionCode,int sucessCode){
        askExpertsModel.upLoadPayResult(questionCode,sucessCode,new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
            }

            @Override
                public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
            }
        });
    }
}
