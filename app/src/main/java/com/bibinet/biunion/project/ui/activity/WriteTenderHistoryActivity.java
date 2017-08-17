package com.bibinet.biunion.project.ui.activity;

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
import com.bibinet.biunion.mvp.presenter.WriteTenderHistoryActivityPresenter;
import com.bibinet.biunion.mvp.view.WriteTenderHistoryActivityView;
import com.bibinet.biunion.project.adapter.SocailFooterAdapter;
import com.bibinet.biunion.project.adapter.TenderHistoryAdapter;
import com.bibinet.biunion.project.adapter.WriteTenderBookHistoryAdapter;
import com.bibinet.biunion.project.adapter.WriteTenderHistoryAdapter;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.DeleteHistoryBean;
import com.bibinet.biunion.project.bean.WriteTenderBookHistoryBean;
import com.bibinet.biunion.project.ui.expand.PageActivity;
import com.bibinet.biunion.project.ui.expand.PageActivityAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-14.
 */

public class WriteTenderHistoryActivity extends PageActivity<WriteTenderBookHistoryBean.ItemBean> implements WriteTenderHistoryActivityView{
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_imageright)
    ImageView titleImageright;
    @BindView(R.id.title_imageleft)
    ImageView titleImageleft;

    private WriteTenderHistoryActivityPresenter presenter;


    @Override
    protected int getNotDataViewId() {
        return R.id.act_write_tender_history_not_data;
    }


    @Override
    protected PageActivityAdapter getPageAdapter(List<WriteTenderBookHistoryBean.ItemBean> dataList) {
        return new WriteTenderHistoryAdapter(this, dataList, presenter);
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
        return R.id.act_write_tender_history_recycler;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.act_write_tender_history_swipe_refresh;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_tender_history;
    }

    @Override
    protected void nextPageLoad(int pageNumb) {
        loadData(pageNumb);
    }

    private void initView() {
        title.setText("历史记录");
        titleImageleft.setVisibility(View.VISIBLE);
        presenter=new WriteTenderHistoryActivityPresenter(this);
    }

    private void loadData(int pageNumb) {
        presenter.getWriteHistoryData(Constants.loginresultInfo.getUser().getEnterprise().getContactCellphone(),Constants.loginresultInfo.getUser().getUserId(),String.valueOf(pageNumb));
    }

    @Override
    protected void refreshLoad(int pageNumb) {
        loadData(pageNumb);
    }

    @OnClick(R.id.title_imageleft)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onDeleteResultSuccess(DeleteHistoryBean bean) {
        if(bean.isDelResult()){
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
            initRefresh();
        }else{
            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteResultFailed(String s) {
        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
    }

}
