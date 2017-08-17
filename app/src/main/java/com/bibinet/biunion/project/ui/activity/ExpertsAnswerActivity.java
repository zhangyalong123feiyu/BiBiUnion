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
import com.bibinet.biunion.mvp.presenter.ExpertsAnswerHistoryPresenter;
import com.bibinet.biunion.mvp.presenter.WriteTenderHistoryActivityPresenter;
import com.bibinet.biunion.mvp.view.ExpertsAnswerHistoryView;
import com.bibinet.biunion.project.adapter.ExpertsHistoryAdapter;
import com.bibinet.biunion.project.adapter.SocailFooterAdapter;
import com.bibinet.biunion.project.adapter.WriteTenderHistoryAdapter;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.ExpertsAskAnswerResultBean;
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
 * Created by bibinet on 2017-6-19.
 */

public class ExpertsAnswerActivity extends PageActivity<ExpertsAskAnswerResultBean.ItemsBean> implements ExpertsAnswerHistoryView,ExpertsHistoryAdapter.onDeleteDataListioner {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_imageright)
    ImageView titleImageright;
    @BindView(R.id.title_imageleft)
    ImageView titleImageleft;

    private ExpertsAnswerHistoryPresenter presenter;
    private int pageNum=1;
    private int lastvisibleitem;
    private ExpertsHistoryAdapter adapter;
    private List<ExpertsAskAnswerResultBean.ItemsBean> dataListX=new ArrayList<>();
    private int deletePostion=-1;
    @Override
    protected int getNotDataViewId() {
        return R.id.act_experts_answer_not_data;
    }

    @Override
    protected PageActivityAdapter getPageAdapter(List<ExpertsAskAnswerResultBean.ItemsBean> dataList) {
        dataListX=dataList;
        adapter=new ExpertsHistoryAdapter(this, dataListX);
        adapter.setOnDeletListioner(this);
        return adapter;
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
        return R.id.act_experts_answer_recycler;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.act_experts_answer_swipe_refresh;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_experts_answer;
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
        title.setText("专家问答");
        titleImageleft.setVisibility(View.VISIBLE);
        titleImageright.setVisibility(View.VISIBLE);
        titleImageright.setImageResource(R.mipmap.tiwen);
        presenter=new ExpertsAnswerHistoryPresenter(this);
    }

    private void loadData(int pageNum) {
        presenter.getExpertsAnswerHistoryData(Constants.loginresultInfo.getUser().getUserId(),pageNum);
    }
    @OnClick({R.id.title_imageright, R.id.title_imageleft})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_imageright:
                startActivityForResult(new Intent(this,AskExpertsActivtiyx.class), AskExpertsActivtiyx.requestCode);
                break;
            case R.id.title_imageleft:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AskExpertsActivtiy.requestCode && resultCode == AskExpertsActivtiy.resultCode){
            initRefresh();
        }
    }


    @Override
    public void onDelete(int postion) {
        deletePostion=postion;
        presenter.deletAnserData(String.valueOf(dataListX.get(postion).getObjectId()));
    }

    @Override
    public void onDeleteSucess() {
        dataListX.remove(deletePostion);
        adapter.notifyDataSetChanged();
        Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteFailed(String msg) {
        Toast.makeText(this,"删除失败",Toast.LENGTH_SHORT).show();
    }
}
