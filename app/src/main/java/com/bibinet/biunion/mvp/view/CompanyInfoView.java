package com.bibinet.biunion.mvp.view;

import com.bibinet.biunion.project.bean.CompanyUpImageBean;
import com.bibinet.biunion.project.bean.LoginResultBean;
import com.bibinet.biunion.project.bean.UpLoadDataResultBean;

/**
 * Created by bibinet on 2017-6-21.
 */

public interface CompanyInfoView {
    void onUpLoadDataSucess(LoginResultBean updataInfo, String s);
    void onUpLoadDataFailed(String s);
    void onUpCompanyPicViewSucess(CompanyUpImageBean upImageInfo);
    void onUpCompanyPicViewFailed(String s);
}
