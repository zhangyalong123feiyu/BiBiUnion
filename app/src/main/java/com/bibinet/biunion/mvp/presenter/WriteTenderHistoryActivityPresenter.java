package com.bibinet.biunion.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.bibinet.biunion.mvp.model.WriteTenderHistoryActivityModel;
import com.bibinet.biunion.mvp.view.WriteTenderHistoryActivityView;
import com.bibinet.biunion.project.bean.DeleteHistoryBean;
import com.bibinet.biunion.project.bean.WriteTenderBookHistoryBean;
import com.bibinet.biunion.project.builder.MyCallBack;
import com.bibinet.biunion.project.ui.dialog.WaitDialog;
import com.google.gson.Gson;

/**
 * Created by bibinet on 2017-6-26.
 */

public class WriteTenderHistoryActivityPresenter {
    private WriteTenderHistoryActivityModel writeTenderHistoryActivityModel;
    private WriteTenderHistoryActivityView writeTenderHistoryActivityView;

    private WaitDialog waitDialog;

    public WriteTenderHistoryActivityPresenter(WriteTenderHistoryActivityView writeTenderHistoryActivityView) {
        this.writeTenderHistoryActivityView = writeTenderHistoryActivityView;
        this.writeTenderHistoryActivityModel = new WriteTenderHistoryActivityModel();
        waitDialog = new WaitDialog((Context)writeTenderHistoryActivityView);
    }

    public void getWriteHistoryData(String cellPhone, String customerId,String pageNum){
        writeTenderHistoryActivityModel.writeTenderHistory(cellPhone,customerId,pageNum ,new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                WriteTenderBookHistoryBean writeTenderHistroyInfo = gson.fromJson(s, WriteTenderBookHistoryBean.class);
                writeTenderHistoryActivityView.loadMoreSuccess(writeTenderHistroyInfo.getItem());
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                String s = throwable.getMessage();
                writeTenderHistoryActivityView.loadMoreFail(s);
            }
        });
    }

    public void delectHistory(String objectId){
        waitDialog.open();
        writeTenderHistoryActivityModel.delectHistory(objectId, new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                DeleteHistoryBean bean = gson.fromJson(s, DeleteHistoryBean.class);
                writeTenderHistoryActivityView.onDeleteResultSuccess(bean);
                waitDialog.close();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                String s = throwable.getMessage();
                writeTenderHistoryActivityView.onDeleteResultFailed(s);
                waitDialog.close();
            }
        });
    }
}
