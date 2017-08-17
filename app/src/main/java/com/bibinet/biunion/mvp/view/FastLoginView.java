package com.bibinet.biunion.mvp.view;

import com.bibinet.biunion.project.bean.FastLoginResultBean;
import com.bibinet.biunion.project.bean.FastLoginVerifCodeBean;
import com.bibinet.biunion.project.bean.LoginResultBean;

/**
 * Created by bibinet on 2017-6-22.
 */

public interface FastLoginView {
    void onGetVerfCodeSucess(FastLoginVerifCodeBean verifCodeInfo);
    void onGetVerfCodeFailed(String s);
    void onLoginSucess(LoginResultBean fastLoginInfo, String json);
    void OnLoginFailed(String s);
}
