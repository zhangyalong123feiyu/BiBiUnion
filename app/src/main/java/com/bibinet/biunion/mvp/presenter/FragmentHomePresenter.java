package com.bibinet.biunion.mvp.presenter;

import android.util.Log;

import com.bibinet.biunion.mvp.model.FragmentHomeModel;
import com.bibinet.biunion.mvp.view.FragmentHomeView;
import com.bibinet.biunion.project.bean.BannerBean;
import com.bibinet.biunion.project.bean.ProjectInfoBean;
import com.bibinet.biunion.project.builder.MyCacheCallBack;
import com.bibinet.biunion.project.builder.MyCallBack;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by bibinet on 2017-6-1.
 */
public class FragmentHomePresenter {
    private FragmentHomeModel fragmentHomeModel;
    private FragmentHomeView fragmentHomeView;

    public FragmentHomePresenter(FragmentHomeView fragmentHomeView) {
        this.fragmentHomeView = fragmentHomeView;
        this.fragmentHomeModel=new FragmentHomeModel();
    }
    public void LoadHomeDataProjcetInfo(int pageNum, int type,int provinceId){
        fragmentHomeModel.LoadHomeDataProjectInfo(pageNum,type,provinceId,new MyCacheCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
                fragmentHomeView.loadMoreSuccess(projectinfo_list);
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                fragmentHomeView.loadMoreFail(throwable.getMessage());
            }
            @Override
            public boolean onCache(String s) {
                Gson gson=new Gson();
                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
                fragmentHomeView.loadMoreSuccess(projectinfo_list);
                return super.onCache(s);
            }
        });
    }
    public void LoadHomeDataTenderInfo(int pageNum, int type,int provinceId){
        fragmentHomeModel.LoadHomeDataTenderInfo(pageNum,type,provinceId,new MyCacheCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
                fragmentHomeView.loadMoreSuccess(projectinfo_list);
                Log.i("TAG","fragementhomedata--------"+projectinfo_list.size());
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                fragmentHomeView.loadMoreFail(throwable.getMessage());
            }

            @Override
            public boolean onCache(String s) {
                Gson gson=new Gson();
                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
                fragmentHomeView.loadMoreSuccess(projectinfo_list);
                return super.onCache(s);
            }
        });
    }
    public void LoadHomeDataBuyInfo(int pageNum, int type,int provinceId){
        fragmentHomeModel.LoadHomeDataBuyInfo(pageNum,type,provinceId,new MyCacheCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
                fragmentHomeView.loadMoreSuccess(projectinfo_list);
                Log.i("TAG","fragementhomedata--------"+projectinfo_list.size());
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                fragmentHomeView.loadMoreFail(throwable.getMessage());
            }

            @Override
            public boolean onCache(String s) {
                Gson gson=new Gson();
                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
                fragmentHomeView.loadMoreSuccess(projectinfo_list);
                return super.onCache(s);
            }
        });
    }
    public void LoadHomeDataPProjectInfo(int pageNum, int type,int provinceId){
        fragmentHomeModel.LoadHomeDataPpProjectInfo(pageNum,type,provinceId,new MyCacheCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
                fragmentHomeView.loadMoreSuccess(projectinfo_list);
                Log.i("TAG","fragementhomedata--------"+projectinfo_list.size());
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                fragmentHomeView.loadMoreFail(throwable.getMessage());
                Log.i("TAG","errorhomedata--------"+throwable.getMessage());
            }

            @Override
            public boolean onCache(String s) {
                Gson gson=new Gson();
                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
                fragmentHomeView.loadMoreSuccess(projectinfo_list);
                return super.onCache(s);
            }
        });
    }
    public void LoadHomeDataApplayProjectInfo(int pageNum, int type, int provinceId  ){
        fragmentHomeModel.LoadHomeDataApplayProjectInfo(pageNum,type,provinceId,new MyCacheCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
                fragmentHomeView.loadMoreSuccess(projectinfo_list);
                Log.i("TAG","fragementhomedata--------"+projectinfo_list.size());
            }
            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                fragmentHomeView.loadMoreFail(throwable.getMessage());
                Log.i("TAG","errorhomedata--------"+throwable.getMessage());
            }

            @Override
            public boolean onCache(String s) {
                Gson gson=new Gson();
                ProjectInfoBean projectInfo = gson.fromJson(s, ProjectInfoBean.class);
                List<ProjectInfoBean.ItemsBean> projectinfo_list = projectInfo.getItems();
                fragmentHomeView.loadMoreSuccess(projectinfo_list);
                return super.onCache(s);
            }
        });
    }
    public void getBannerData(){
        fragmentHomeModel.getBannerUrl(new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                BannerBean bannerInfo = gson.fromJson(s, BannerBean.class);
                fragmentHomeView.onLoadBannerSucess(bannerInfo.getItem());
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                fragmentHomeView.onLoadBannerFailed();
            }
        });
    }
}
