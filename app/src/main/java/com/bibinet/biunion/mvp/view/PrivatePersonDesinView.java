package com.bibinet.biunion.mvp.view;

import com.bibinet.biunion.project.bean.BaseBean;
import com.bibinet.biunion.project.bean.PrivatePersonDesinBean;

/**
 * Created by bibinet on 2017-6-27.
 */

public interface PrivatePersonDesinView  {
    void onDesinSucess(BaseBean bean);
    void onDesinFailed(String s);

    void onGetDesinSucess(PrivatePersonDesinBean bean);
    void onGetDesinFailed(String s);
}
