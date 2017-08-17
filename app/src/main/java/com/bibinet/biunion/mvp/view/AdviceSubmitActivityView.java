package com.bibinet.biunion.mvp.view;

import com.bibinet.biunion.project.bean.BaseBean;

/**
 * Created by bibinet on 2017-6-26.
 */

public interface AdviceSubmitActivityView {
    void onSubmitSucess(BaseBean bean);
    void onSubmitFailed(String s);
}
