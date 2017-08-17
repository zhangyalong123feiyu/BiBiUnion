package com.bibinet.biunion.mvp.view;

import com.bibinet.biunion.project.bean.BaseBean;

/**
 * Created by bibinet on 2017-6-23.
 */

public interface TenderHelpView {
    void onUpLoadDataSucess(BaseBean bean);
    void onUpLoadDataFailed(String msg);
}
