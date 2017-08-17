package com.bibinet.biunion.project.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.FoucsActivityPresenter;
import com.bibinet.biunion.mvp.view.FoucsActivityView;
import com.bibinet.biunion.project.adapter.FoucsMyAdapter;
import com.bibinet.biunion.project.adapter.SocailFooterAdapter;
import com.bibinet.biunion.project.adapter.TenderHistoryAdapter;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.FoucsedBean;
import com.bibinet.biunion.project.ui.custom.WaitView;
import com.bibinet.biunion.project.ui.dialog.WaitDialog;
import com.bibinet.biunion.project.ui.expand.PageActivity;
import com.bibinet.biunion.project.ui.expand.PageActivityAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-10.
 */

public class FoucsMyActivity extends PageActivity<FoucsedBean.ItemBean> implements FoucsActivityView {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_imageright)
    ImageView titleImageright;
    @BindView(R.id.title_imageleft)
    ImageView titleImageleft;

    private String userId;

    private FoucsActivityPresenter presenter;

    @Override
    protected int getNotDataViewId() {
        return R.id.act_foucs_not_data;
    }

    @Override
    protected PageActivityAdapter getPageAdapter(List<FoucsedBean.ItemBean> dataList) {
        return new FoucsMyAdapter(this, dataList);
    }

    @Override
    protected void initLoad(int pageNumb) {
        loadData(pageNumb);
    }

    @Override
    protected void onChildCreate(Bundle savedInstanceState) {
        initView();
        userId = Constants.loginresultInfo.getUser().getUserId();
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.act_foucs_recycler;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.act_foucs_swipe_refresh;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_foucs;
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
        title.setText("我的关注");
        titleImageleft.setVisibility(View.VISIBLE);
        presenter = new FoucsActivityPresenter(this);
    }

    @OnClick(R.id.title_imageleft)
    public void onViewClicked() {
        finish();
    }

    private void loadData(int pageNumb) {
        presenter.getFoucsData(Integer.parseInt(userId),pageNumb);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //详情页回调关注结果
        if(requestCode == H5Activity1.requestCode && resultCode == H5Activity1.resultCode){
            try{
                int pos = data.getIntExtra("pos", -1);
                boolean isCollect = data.getBooleanExtra("isCollect", false);
                if(!isCollect){
                    initRefresh();
                }
            }catch (Exception e){

            }
        }
    }
}
