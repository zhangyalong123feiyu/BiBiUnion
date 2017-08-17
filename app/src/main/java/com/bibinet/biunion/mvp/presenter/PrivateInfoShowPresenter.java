package com.bibinet.biunion.mvp.presenter;

import android.util.Log;

import com.bibinet.biunion.mvp.model.MoreProjectModel;
import com.bibinet.biunion.mvp.model.PrivateInfoShowModel;
import com.bibinet.biunion.mvp.view.MoreProjectView;
import com.bibinet.biunion.mvp.view.PrivateInfoShowView;
import com.bibinet.biunion.project.bean.ProjectInfoBean;
import com.bibinet.biunion.project.bean.ProjectPrivateInfoBean;
import com.bibinet.biunion.project.builder.MyCacheCallBack;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by bibinet on 2017-6-14.
 */

public class PrivateInfoShowPresenter {
    private PrivateInfoShowModel privateInfoShowModel;
    private PrivateInfoShowView privateInfoShowView;

    public PrivateInfoShowPresenter(PrivateInfoShowView privateInfoShowView) {
        this.privateInfoShowView = privateInfoShowView;
        this.privateInfoShowModel=new PrivateInfoShowModel();
    }
    public void loadPrivateMoreProjectInfo(int pageNum, int customerId){
        privateInfoShowModel.loadPrivateMoreProjectInfo(pageNum,customerId,new MyCacheCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                ProjectPrivateInfoBean projectInfo = gson.fromJson(s, ProjectPrivateInfoBean.class);
                List<ProjectPrivateInfoBean.ItemsBean> projectinfo_list = projectInfo.getItem();
                privateInfoShowView.loadMoreSuccess(projectinfo_list);
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                privateInfoShowView.loadMoreFail(throwable.getMessage());
            }
        });
    }
}
