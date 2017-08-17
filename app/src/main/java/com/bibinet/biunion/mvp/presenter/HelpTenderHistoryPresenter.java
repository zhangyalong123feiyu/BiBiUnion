package com.bibinet.biunion.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.bibinet.biunion.mvp.model.HelpTenderHistoryActivityModel;
import com.bibinet.biunion.mvp.view.HelpTenderHistoryActivityView;
import com.bibinet.biunion.project.bean.DeleteHistoryBean;
import com.bibinet.biunion.project.bean.HelpTenderHistoryReusltBean;
import com.bibinet.biunion.project.builder.MyCallBack;
import com.bibinet.biunion.project.ui.dialog.WaitDialog;
import com.google.gson.Gson;

/**
 * Created by bibinet on 2017-6-29.
 */

public class HelpTenderHistoryPresenter extends BasePresenter{
    private HelpTenderHistoryActivityModel helpTenderHistoryActivityModel;
    private HelpTenderHistoryActivityView helpTenderHistoryActivityView;

    public HelpTenderHistoryPresenter(HelpTenderHistoryActivityView helpTenderHistoryActivityView) {
        super(helpTenderHistoryActivityView);
        this.helpTenderHistoryActivityView = helpTenderHistoryActivityView;
        this.helpTenderHistoryActivityModel=new HelpTenderHistoryActivityModel();
    }
    public void getHelpHistoryData(String userId, int pageNumb){
        helpTenderHistoryActivityModel.helpTenderHistory(userId,pageNumb,new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                HelpTenderHistoryReusltBean helpHistoryInfo = gson.fromJson(s, HelpTenderHistoryReusltBean.class);
                helpTenderHistoryActivityView.loadMoreSuccess(helpHistoryInfo.getItem());
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                helpTenderHistoryActivityView.loadMoreFail(throwable.getMessage());
            }
        });
    }

    public void delectHistory(String objectId){
        waitDialog.open();
        helpTenderHistoryActivityModel.delectHistory(objectId, new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                DeleteHistoryBean bean = gson.fromJson(s, DeleteHistoryBean.class);
                helpTenderHistoryActivityView.onDeleteResultSuccess(bean);
                waitDialog.close();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                String s = throwable.getMessage();
                helpTenderHistoryActivityView.onDeleteResultFailed(s);
                waitDialog.close();
            }
        });
    }
}
