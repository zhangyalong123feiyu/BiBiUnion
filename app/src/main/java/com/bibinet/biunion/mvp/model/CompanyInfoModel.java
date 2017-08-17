package com.bibinet.biunion.mvp.model;

import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.builder.MyCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import static com.bibinet.biunion.mvp.UrlManager.TYPE_IIP;
import static com.bibinet.biunion.mvp.UrlManager.TYPE_PIS;
import static com.bibinet.biunion.mvp.UrlManager.getBaseUrl;

/**
 * Created by bibinet on 2017-6-21.
 */

public class CompanyInfoModel {
    public void upLoadData(String enterpriseCode, String enterpriseName, String USCCode, String businessLicenseName, String businessLicenseCardNo, String industry, String region, String addr, String contactName, String contactCellphone, int originalFileInfoId, int thumbnailFileInfoId, String userId, MyCallBack myCallBack) {
        RequestParams requestParams = new RequestParams(getBaseUrl(TYPE_IIP) + "user/enterprise.json");
        requestParams.addBodyParameter("enterpriseCode", enterpriseCode);
        requestParams.addBodyParameter("enterpriseName", enterpriseName);
        requestParams.addBodyParameter("USCCode", USCCode);
        requestParams.addBodyParameter("businessLicenseName", businessLicenseName);
        requestParams.addBodyParameter("businessLicenseCardNo", businessLicenseCardNo);
        requestParams.addBodyParameter("industry", industry);
        requestParams.addBodyParameter("region", region);
        requestParams.addBodyParameter("addr", addr);
        requestParams.addBodyParameter("operateType", "1");
        requestParams.addBodyParameter("contactName", contactName);
        requestParams.addBodyParameter("contactCellphone", contactCellphone);
        if (originalFileInfoId == 0 && thumbnailFileInfoId == 0) {
            requestParams.addBodyParameter("originalFileInfoId", "");
            requestParams.addBodyParameter("thumbnailFileInfoId", "");
        } else {
            requestParams.addBodyParameter("originalFileInfoId", String.valueOf(originalFileInfoId));
            requestParams.addBodyParameter("thumbnailFileInfoId", String.valueOf(thumbnailFileInfoId));
        }
        requestParams.addBodyParameter("userId", userId);
        x.http().post(requestParams, myCallBack);
    }

    public void upLoadCompanyImage(File file, MyCallBack myCallBack) {
        RequestParams requestParams = new RequestParams(getBaseUrl(TYPE_IIP) + "file/uploadCertificateFile.json");
        requestParams.addBodyParameter("type", "1");
        requestParams.addBodyParameter("file", file);
        x.http().post(requestParams, myCallBack);
    }
}
