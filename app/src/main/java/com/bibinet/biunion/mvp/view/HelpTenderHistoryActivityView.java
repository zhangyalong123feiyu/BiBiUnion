package com.bibinet.biunion.mvp.view;

import com.bibinet.biunion.project.bean.DeleteHistoryBean;
import com.bibinet.biunion.project.bean.HelpTenderHistoryReusltBean;

import java.util.List;

/**
 * Created by bibinet on 2017-6-29.
 */

public interface HelpTenderHistoryActivityView extends PageView<HelpTenderHistoryReusltBean.ItemBean>{
    void onDeleteResultSuccess(DeleteHistoryBean bean);
    void onDeleteResultFailed(String s);
}
