package com.bibinet.biunion.mvp.view;

import com.bibinet.biunion.project.bean.ExpertsAskAnswerResultBean;

import java.util.List;

/**
 * Created by bibinet on 2017-6-30.
 */

public interface ExpertsAnswerHistoryView extends PageView<ExpertsAskAnswerResultBean.ItemsBean>{
//    void onGetExpertsHistoryDataSucess(List<ExpertsAskAnswerResultBean.ItemsBean> reslutInfo);
//    void onGetExpertsHistoryFailed();
    void onDeleteSucess();
    void onDeleteFailed(String msg);
}
