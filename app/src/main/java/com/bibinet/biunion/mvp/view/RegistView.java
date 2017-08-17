package com.bibinet.biunion.mvp.view;

import com.bibinet.biunion.project.bean.BaseBean;
import com.bibinet.biunion.project.bean.VerifCodeBean;

/**
 * Created by bibinet on 2017-6-16.
 */

public interface RegistView  {
    void onVerifGetSucess(VerifCodeBean verifInfo);
    void onVerifGetFailed();
    void onRegistSucess(BaseBean baseBean);
    void onRegistFailed(String s);
}
