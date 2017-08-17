package com.bibinet.biunion.project.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.MoreProjectPresenter;
import com.bibinet.biunion.mvp.presenter.PrivateInfoShowPresenter;
import com.bibinet.biunion.mvp.view.MoreProjectView;
import com.bibinet.biunion.mvp.view.PrivateInfoShowView;
import com.bibinet.biunion.project.adapter.MoreItemAdapter;
import com.bibinet.biunion.project.adapter.MoreProjectAdapter;
import com.bibinet.biunion.project.adapter.PrivateInfoShowAdapter;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.ProjectInfoBean;
import com.bibinet.biunion.project.bean.ProjectPrivateInfoBean;
import com.bibinet.biunion.project.ui.expand.PageActivity;
import com.bibinet.biunion.project.ui.expand.PageActivityAdapter;
import com.bibinet.biunion.project.utils.ConvertUtils;
import com.bibinet.biunion.project.utils.PublicPopWindowUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-7.
 */

public class PrivateInfoShowActivity extends PageActivity<ProjectPrivateInfoBean.ItemsBean> implements PrivateInfoShowView {

    private PrivateInfoShowPresenter privateInfoShowPresenter;

    @Override
    protected int getNotDataViewId() {
        return R.id.act_private_info_show_not_data;
    }

    @Override
    protected PageActivityAdapter getPageAdapter(List<ProjectPrivateInfoBean.ItemsBean> dataList) {
        return new PrivateInfoShowAdapter(this, dataList);
    }

    @Override
    protected void initLoad(int pageNumb) {
        loadData(pageNumb);
    }

    @Override
    protected void onChildCreate(Bundle savedInstanceState) {
        initView();
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.act_private_info_show_recycler;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.act_private_info_show_swipe_refresh;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_private_info_show;
    }

    @Override
    protected void nextPageLoad(int pageNumb) {
        loadData(pageNumb);
    }

    @Override
    protected void refreshLoad(int pageNumb) {
        loadData(pageNumb);
    }

    private void initView() {
        privateInfoShowPresenter = new PrivateInfoShowPresenter(this);
    }

    //取数据
    private void loadData(int pageNumb) {
        int cId = Integer.valueOf(Constants.loginresultInfo.getUser().getUserId());
        privateInfoShowPresenter.loadPrivateMoreProjectInfo(pageNumb, cId);
    }

    @OnClick({R.id.backImage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImage:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //详情页回调关注结果
        if(requestCode == H5Activity1.requestCode && resultCode == H5Activity1.resultCode){
            Log.e("-=", "x-x-xx22--");
            try{
                int pos = data.getIntExtra("pos", -1);
                boolean isCollect = data.getBooleanExtra("isCollect", false);
                Log.e("-=", "x-x-x"+pos+"--"+isCollect);
                getDataList().get(pos).setProjectIsCollect(isCollect);
                getAdapter().notifyDataSetChanged();
            }catch (Exception e){

            }
        }
    }
}
