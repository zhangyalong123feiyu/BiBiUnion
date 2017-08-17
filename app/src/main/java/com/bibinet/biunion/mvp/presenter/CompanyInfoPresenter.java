package com.bibinet.biunion.mvp.presenter;

import android.util.Log;

import com.bibinet.biunion.mvp.model.CompanyInfoModel;
import com.bibinet.biunion.mvp.view.CompanyInfoView;
import com.bibinet.biunion.project.bean.CompanyUpImageBean;
import com.bibinet.biunion.project.bean.LoginResultBean;
import com.bibinet.biunion.project.bean.UpLoadDataResultBean;
import com.bibinet.biunion.project.builder.MyCallBack;
import com.google.gson.Gson;

import java.io.File;

/**
 * Created by bibinet on 2017-6-21.
 */

public class CompanyInfoPresenter extends BasePresenter{
    private CompanyInfoModel companyInfoModel;
    private CompanyInfoView companyInfoView;

    public CompanyInfoPresenter(CompanyInfoView companyInfoView) {
        super(companyInfoView);
        this.companyInfoView = companyInfoView;
        this.companyInfoModel=new CompanyInfoModel();
    }
    public void upLoadData(String enterpriseCode,String enterpriseName,String USCCode,String businessLicenseName,String businessLicenseCardNo,String industry,String region,String addr,String contactName,String contactCellphone,int originalFileInfoId,int thumbnailFileInfoId, String userId){
        Log.e("s", "-="+industry);
        companyInfoModel.upLoadData(enterpriseCode,enterpriseName,USCCode,businessLicenseName,businessLicenseCardNo,industry,region,addr,contactName,contactCellphone,originalFileInfoId,thumbnailFileInfoId, userId, new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("s", "-="+s);
                Gson gson = new Gson();
                LoginResultBean updataInfo = gson.fromJson(s, LoginResultBean.class);
                companyInfoView.onUpLoadDataSucess(updataInfo, s);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                companyInfoView.onUpLoadDataFailed(throwable.getMessage());
            }
        });
    }

    //上传图片
    public void upLoadPic(File file){
        waitDialog.open();
        companyInfoModel.upLoadCompanyImage(file, new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                CompanyUpImageBean upLoadResultInfo = gson.fromJson(s, CompanyUpImageBean.class);
                companyInfoView.onUpCompanyPicViewSucess(upLoadResultInfo);
                waitDialog.close();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                waitDialog.close();
                companyInfoView.onUpCompanyPicViewFailed(throwable.getMessage());
            }
        });
    }
}
