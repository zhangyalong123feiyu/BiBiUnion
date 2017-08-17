package com.bibinet.biunion.mvp.presenter;

import android.util.Log;

import com.bibinet.biunion.mvp.model.WriteTenderBookModel;
import com.bibinet.biunion.mvp.view.WriteTenderBookView;
import com.bibinet.biunion.project.bean.BaseBean;
import com.bibinet.biunion.project.builder.MyCallBack;
import com.google.gson.Gson;

/**
 * Created by bibinet on 2017-6-22.
 */

public class WriteTenderBookPresenter extends BasePresenter{
    private WriteTenderBookView writeTenderBookView;
    private WriteTenderBookModel writeTenderBookModel;

    public WriteTenderBookPresenter(WriteTenderBookView writeTenderBookView) {
        super(writeTenderBookView);
        this.writeTenderBookView = writeTenderBookView;
        this.writeTenderBookModel = new WriteTenderBookModel();
    }
    public void saveWriteTenderBook(int tenderSelection,int projectType,int tenderMode,int tenderType,String contact,String cellPhone,String email,String customerId){
        waitDialog.open();
        writeTenderBookModel.upLoadTenderBookData(tenderSelection,projectType,tenderMode,tenderType,contact,cellPhone,email,customerId,new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);

                Gson gson = new Gson();
                BaseBean bean =  gson.fromJson(s, BaseBean.class);
                waitDialog.close();
                writeTenderBookView.saveTenderBookSucess(bean);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                waitDialog.close();
                writeTenderBookView.saveTenderBookFailed(throwable.getMessage());
            }
        });
    }

}
