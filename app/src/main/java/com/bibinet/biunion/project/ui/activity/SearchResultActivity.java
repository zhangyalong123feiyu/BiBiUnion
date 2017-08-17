package com.bibinet.biunion.project.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.HotWordsPresenter;
import com.bibinet.biunion.mvp.presenter.SearchActivityPresenter;
import com.bibinet.biunion.mvp.view.HotWordsView;
import com.bibinet.biunion.mvp.view.SearchActivityView;
import com.bibinet.biunion.project.adapter.SearchActivityAdapter;
import com.bibinet.biunion.project.adapter.SocailFooterAdapter;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.bean.SearchResultBean;
import com.bibinet.biunion.project.ui.dialog.WaitDialog;
import com.bibinet.biunion.project.ui.expand.PageActivity;
import com.bibinet.biunion.project.ui.expand.PageActivityAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-5.
 */

public class SearchResultActivity extends PageActivity<SearchResultBean.ItemsBean> implements SearchActivityView {

    private SearchActivityPresenter presenter;
    private String key;

    @OnClick(R.id.act_search_exit)
    void exit() {
        finish();
    }

    @Override
    protected int getNotDataViewId() {
        return R.id.act_search_not_data;
    }

    @Override
    protected PageActivityAdapter getPageAdapter(List<SearchResultBean.ItemsBean> dataList) {
        return new SearchActivityAdapter(this, dataList);
    }

    @Override
    protected void initLoad(int pageNumb) {
        loadData(pageNumb);
    }

    @Override
    protected void onChildCreate(Bundle savedInstanceState) {
        key = getIntent().getStringExtra("key");
        initView();
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.act_search_recycler;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.act_search_swipe_refresh;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_result;
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
        presenter = new SearchActivityPresenter(this);
    }
    private void loadData(int pageNum) {
        presenter.getSearchData(pageNum, key);//搜索presenter
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
                Log.e("-=", "x-x-x"+pos+"--"+isCollect+"-"+getDataList().get(pos).isProjectIsCollect());
                getAdapter().notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
