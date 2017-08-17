package com.bibinet.biunion.mvp.presenter;

import android.util.Log;

import com.bibinet.biunion.mvp.model.ExpertsAnswerHistoryModel;
import com.bibinet.biunion.mvp.view.ExpertsAnswerHistoryView;
import com.bibinet.biunion.project.bean.ExpertsAskAnswerResultBean;
import com.bibinet.biunion.project.builder.MyCallBack;
import com.google.gson.Gson;

/**
 * Created by bibinet on 2017-6-30.
 */

public class ExpertsAnswerHistoryPresenter {
    private ExpertsAnswerHistoryView expertsAnswerHistoryView;
    private ExpertsAnswerHistoryModel expertsAnswerHistoryModel;

    public ExpertsAnswerHistoryPresenter(ExpertsAnswerHistoryView expertsAnswerHistoryView) {
        this.expertsAnswerHistoryView = expertsAnswerHistoryView;
        this.expertsAnswerHistoryModel=new ExpertsAnswerHistoryModel();
    }
    public void getExpertsAnswerHistoryData(String userId, int pageNum){
        expertsAnswerHistoryModel.getExpertsAnswerData(userId,pageNum,new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("--", "-="+s);
                Gson gson= new Gson();
                ExpertsAskAnswerResultBean resultInfo = gson.fromJson(s, ExpertsAskAnswerResultBean.class);
                expertsAnswerHistoryView.loadMoreSuccess(resultInfo.getItems());
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                expertsAnswerHistoryView.loadMoreFail(throwable.getMessage());
            }
        });
    }
    public void deletAnserData(String objectId){
        expertsAnswerHistoryModel.deleteAnserData(objectId,new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                expertsAnswerHistoryView.onDeleteSucess();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                expertsAnswerHistoryView.onDeleteFailed(throwable.getMessage());
            }
        });
    }
}
