package com.bibinet.biunion.mvp.view;

import com.bibinet.biunion.project.bean.LoginResultBean;
import com.bibinet.biunion.project.bean.PrivatePersonDesinBean;

/**
 * Created by bibinet on 2017-6-30.
 */

public interface UpLoadUserPhotoView {
    void onUpLoadPhotoSucess(LoginResultBean loginInfo, String s);
    void onUpLoadPhotoFailed(String s);
}
