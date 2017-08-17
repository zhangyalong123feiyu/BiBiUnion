package com.bibinet.biunion.mvp.presenter;

import com.bibinet.biunion.mvp.model.TenderHelpModel;
import com.bibinet.biunion.mvp.view.TenderHelpView;
import com.bibinet.biunion.project.bean.BaseBean;
import com.bibinet.biunion.project.builder.MyCallBack;
import com.google.gson.Gson;

/**
 * Created by bibinet on 2017-6-23.
 */

public class TenderHelpPresenter extends BasePresenter{
    private TenderHelpModel tenderHelpModel;
    private TenderHelpView tenderHelpView;

    public TenderHelpPresenter(TenderHelpView tenderHelpView) {
        super(tenderHelpView);
        this.tenderHelpView = tenderHelpView;
        this.tenderHelpModel=new TenderHelpModel();
    }
    public void upLoadData(String contact, String cellPhone, String content,String customerId){
        waitDialog.open();
        tenderHelpModel.upLoadData(contact,cellPhone,content,customerId,new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson = new Gson();
                BaseBean bean = gson.fromJson(s, BaseBean.class);
                tenderHelpView.onUpLoadDataSucess(bean);
                waitDialog.close();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                tenderHelpView.onUpLoadDataFailed(throwable.getMessage());
                waitDialog.close();
            }
        });
    }
}
