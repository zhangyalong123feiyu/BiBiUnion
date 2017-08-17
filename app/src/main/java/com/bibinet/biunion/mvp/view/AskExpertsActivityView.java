package com.bibinet.biunion.mvp.view;

import com.bibinet.biunion.project.bean.AskExpertsBean;
import com.bibinet.biunion.project.bean.ExpertsDataBean;

import java.util.List;

/**
 * Created by bibinet on 2017-6-28.
 */

public interface AskExpertsActivityView {
    void onPostQuestionDataSucess(AskExpertsBean projectInfo);
    void onPostQuestionDataFailed(String msg);
    void onGetExpertsDataSucess(List<ExpertsDataBean.ItemsBean> itemsBeanList);
    void onGetExpertsDataFailed(String msg);
}
