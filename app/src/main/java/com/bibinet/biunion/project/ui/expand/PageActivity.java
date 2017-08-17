package com.bibinet.biunion.project.ui.expand;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.MoreProjectPresenter;
import com.bibinet.biunion.mvp.view.MoreProjectView;
import com.bibinet.biunion.mvp.view.PageView;
import com.bibinet.biunion.project.adapter.MoreItemAdapter;
import com.bibinet.biunion.project.adapter.MoreProjectAdapter;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.bean.ProjectInfoBean;
import com.bibinet.biunion.project.ui.dialog.WaitDialog;
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

public abstract class PageActivity<T> extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, PageView<T> {
    private View errorV;
    private FrameLayout mainV;

    private View notDataV;

    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private List<T> dataList;

    private PageActivityAdapter adapter;

    private boolean isLoadMore = false;

    private int lastvisibleitem = 1;
    private int pageNumb = 1;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        mainV = (FrameLayout) findViewById(R.id.act_page_main);
        errorV = findViewById(R.id.act_page_error);
        waitDialog = new WaitDialog(this);

        View view = LayoutInflater.from(this).inflate(getLayoutId(), null, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(getSwipeRefreshLayoutId());
        recyclerView = (RecyclerView) view.findViewById(getRecyclerViewId());
        notDataV = view.findViewById(getNotDataViewId());
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(listener);
        swipeRefreshLayout.setOnRefreshListener(this);
        mainV.addView(view);

        ButterKnife.bind(this);
        onChildCreate(savedInstanceState);

        dataList = new ArrayList<T>();
        adapter = getPageAdapter(dataList);


        adapter.changeMoreStatus(PageActivityAdapter.LOADING_INIT);

        initRefresh();
    }

    protected void initRefresh(){
        adapter.changeMoreStatus(PageActivityAdapter.LOADING_MORE);
        pageNumb = 1;
        initLoad(pageNumb);
        waitDialog.open();
        isLoadMore = false;
    }

    protected abstract int getNotDataViewId();


    protected abstract PageActivityAdapter getPageAdapter(List<T> dataList);


    protected abstract void initLoad(int pageNumb);

    protected abstract void onChildCreate(Bundle savedInstanceState);

    protected abstract int getRecyclerViewId();

    protected abstract int getSwipeRefreshLayoutId();

    protected abstract int getLayoutId();

    private RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (adapter != null) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastvisibleitem + 1 == adapter.getItemCount()) {
                    if(adapter.getCurrentState() == PageActivityAdapter.LOAD_ACCESS){
                        pageNumb++;
                        nextPageLoad(pageNumb);
                        isLoadMore = true;
                        adapter.changeMoreStatus(PageActivityAdapter.LOADING_MORE);
                    }
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastvisibleitem = linearLayoutManager.findLastVisibleItemPosition();
        }
    };

    protected abstract void nextPageLoad(int pageNumb);

    //刷新
    @Override
    public void onRefresh() {
        adapter.changeMoreStatus(PageActivityAdapter.LOADING_INIT);
        pageNumb = 1;
        refreshLoad(pageNumb);
        isLoadMore = false;
        swipeRefreshLayout.setRefreshing(true);
    }

    private WaitDialog waitDialog;

    @Override
    public void loadMoreSuccess(List<T> newDataList) {
        waitDialog.close();
        errorV.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        if (isLoadMore) {
            dataList.addAll(newDataList);
            if (newDataList.size() <= 0) {
                Toast.makeText(this, "没有更多数据", Toast.LENGTH_SHORT).show();
                adapter.changeMoreStatus(PageActivityAdapter.LOAD_NODATA);
            } else {
                adapter.changeMoreStatus(PageActivityAdapter.LOAD_ACCESS);
            }
            adapter.notifyDataSetChanged();
        } else {
            dataList.clear();
            dataList.addAll(newDataList);
            adapter = getPageAdapter(dataList);
            recyclerView.setAdapter(adapter);
            if(dataList.size()<=0){
                notDataV.setVisibility(View.VISIBLE);
            }else{
                notDataV.setVisibility(View.GONE);
            }
            int size = dataList.size();
            if(size<8){
                adapter.changeMoreStatus(PageActivityAdapter.LOAD_NODATA);
            }else{
                adapter.changeMoreStatus(PageActivityAdapter.LOAD_ACCESS);
            }
        }
    }

    @Override
    public void loadMoreFail(String s) {
        waitDialog.close();
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
        //判断列表数量 为0展示错误图
        if(dataList.size()<=0){
            errorV.setVisibility(View.VISIBLE);
        }else{
            errorV.setVisibility(View.GONE);
        }
    }

    protected abstract void refreshLoad(int pageNumb);

    protected List<T> getDataList(){
        return dataList;
    }

    protected PageActivityAdapter getAdapter(){
        return adapter;
    }
}
