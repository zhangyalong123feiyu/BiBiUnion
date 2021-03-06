package com.bibinet.biunion.mvp.presenter;

import android.util.Log;

import com.bibinet.biunion.mvp.model.MoreProjectModel;
import com.bibinet.biunion.mvp.view.MoreProjectView;
import com.bibinet.biunion.project.bean.ProjectInfoBean;
import com.bibinet.biunion.project.builder.MyCacheCallBack;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

/**
 * Created by bibinet on 2017-6-14.
 */

public class MoreProjectPresenter{
    private MoreProjectModel moreProjectModel;
    private MoreProjectView moreProjectView;

    public MoreProjectPresenter(MoreProjectView fragmentHomeView) {
        this.moreProjectView = fragmentHomeView;
        this.moreProjectModel=new MoreProjectModel();
    }
    public void LoadHomeDataMoreProjcetInfo(int pageNum, int type, int dateRange, String trad, int provinceId){
        Log.e("req", "-==-="+pageNum+"--"+type+"-"+dateRange+"-"+dateRange+"-"+trad+"-"+provinceId);
        moreProjectModel.LoadHomeDataMoreProjectInfo(pageNum,type, dateRange, trad,provinceId, new MyCacheCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
                moreProjectView.loadMoreSuccess(projectinfo_list);
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                moreProjectView.loadMoreFail(throwable.getMessage());
            }
//            @Override
//            public boolean onCache(String s) {
//                Gson gson=new Gson();
//                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
//                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
//                moreProjectView.loadMoreSuccess(projectinfo_list);
//                return super.onCache(s);
//            }
        });
    }
    public void LoadHomeDataMoreTenderInfo(int pageNum, int type, int dateRange, String trad, int provinceId){
        moreProjectModel.LoadHomeDataMoreTenderInfo(pageNum,type, dateRange, trad,provinceId,new MyCacheCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
                moreProjectView.loadMoreSuccess(projectinfo_list);
                Log.i("TAG","fragementhomedata--------"+projectinfo_list.size());
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                moreProjectView.loadMoreFail(throwable.getMessage());
                Log.i("TAG","error--------"+throwable.getMessage());
            }

//            @Override
//            public boolean onCache(String s) {
//                Gson gson=new Gson();
//                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
//                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
//                moreProjectView.loadMoreSuccess(projectinfo_list);
//                return super.onCache(s);
//
//            }
        });
    }
    public void LoadHomeDataMoreBuyInfo(int pageNum, int type, int dateRange, String trad, int provinceId){
        moreProjectModel.LoadHomeDataMoreBuyInfo(pageNum,type, dateRange, trad,provinceId,new MyCacheCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
                moreProjectView.loadMoreSuccess(projectinfo_list);
                Log.i("TAG","fragementhomedata--------"+projectinfo_list.size());
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                moreProjectView.loadMoreFail(throwable.getMessage());
                Log.i("TAG","error--------"+throwable.getMessage());
            }

//            @Override
//            public boolean onCache(String s) {
//                Gson gson=new Gson();
//                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
//                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
//                moreProjectView.loadMoreSuccess(projectinfo_list);
//                return super.onCache(s);
//            }
        });
    }
    public void LoadHomeDataMorePProjectInfo(int pageNum, int type, int dateRange, String trad, int provinceId){
        moreProjectModel.LoadHomeDataMorePpProjectInfo(pageNum,type, dateRange, trad,provinceId,new MyCacheCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
                moreProjectView.loadMoreSuccess(projectinfo_list);
                Log.i("TAG","fragementhomedata--------"+projectinfo_list.size());
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                moreProjectView.loadMoreFail(throwable.getMessage());
                Log.i("TAG","error--------"+throwable.getMessage());
            }

//            @Override
//            public boolean onCache(String s) {
//                Gson gson=new Gson();
//                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
//                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
//                moreProjectView.loadMoreSuccess(projectinfo_list);
//                return super.onCache(s);
//            }
        });
    }
    public void LoadHomeDataMoreApplayProjectInfo(int pageNum, int type, int dateRange, String trad, int provinceId){
        moreProjectModel.LoadHomeDataMoreApplayProjectInfo(pageNum,type, dateRange, trad,provinceId,new MyCacheCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
                moreProjectView.loadMoreSuccess(projectinfo_list);
                Log.i("TAG","fragementhomedata--------"+projectinfo_list.size());
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                moreProjectView.loadMoreFail(throwable.getMessage());
                Log.i("TAG","error--------"+throwable.getMessage());
            }

//            @Override
//            public boolean onCache(String s) {
//                Gson gson=new Gson();
//                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
//                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
//                moreProjectView.loadMoreSuccess(projectinfo_list);
//                return super.onCache(s);
//            }
        });
    }
}
