package com.bibinet.biunion.project.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.bibinet.biunion.project.ui.custom.HistoryTagShowView;
import com.bibinet.biunion.project.utils.SearchHistoryUtils;
import com.bibinet.biunion.project.utils.SoftKeyboardUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-5.
 */

public class SearchActivity extends BaseActivity implements HotWordsView {
    @BindView(R.id.doSearch)
    View doSearch;
    @BindView(R.id.searchEdit)
    EditText searchEdit;

    @BindView(R.id.hotOne)
    TextView hotOne;
    @BindView(R.id.hotTwo)
    TextView hotTwo;
    @BindView(R.id.hotThree)
    TextView hotThree;
    @BindView(R.id.hotFour)
    TextView hotFour;
    @BindView(R.id.hotFive)
    TextView hotFive;
    @BindView(R.id.hotSix)
    TextView hotSix;


    @BindView(R.id.act_search_history_tag)
    HistoryTagShowView historyTagShowView;
    @BindView(R.id.act_search_not_history)
    View notHistoryV;

    private List<String> searchDataList;

    private List<SearchResultBean.ItemsBean> projectList = new ArrayList<>();
    private SearchActivityAdapter adapter;
    private int lastvisibleitem;
    private int pageNum;
    private String content;
    private HotWordsPresenter hotpresenter;
    private TextView [] tvHot;

    @OnClick(R.id.act_search_exit)
    void exit() {
        SoftKeyboardUtils.hintKbTwo(this);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcher);
        ButterKnife.bind(this);
        searchDataList = new ArrayList<String>();
        initView();
        updateSearchData();
    }

    private void initView() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        hotpresenter = new HotWordsPresenter(this);
        hotpresenter.getHotWords();//热词presenter
    }

    private void loadData(boolean isLoadMore) {
//        if (isLoadMore) {
//            adapter.changeMoreStatus(SocailFooterAdapter.LOADING_MORE);
//            pageNum++;
//        } else {
//            pageNum = 1;
//            progressDialogUtils1.showProgressDialog(this);
//        }
//        presenter.getSearchData(pageNum, content, isLoadMore);//搜索presenter
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("key", content);
        startActivity(intent);

        //写入数据
        SearchHistoryUtils.writeData(this, content);
//        searchDataList.add(0, content);
        updateSearchData();
    }

    @OnClick(R.id.doSearch)
    public void onViewClicked() {
        content = searchEdit.getText().toString().trim();
        if (content.equals("")) {
            Toast.makeText(this, "没有输入关键词", Toast.LENGTH_SHORT).show();
            return;
        }
        //跳转新界面展示
        loadData(false);
    }

    @Override//加载热词
    public void onLoadHotWordsSucess(List<String> hotWords) {
        tvHot = new TextView[6];
        tvHot[0] = hotOne;
        tvHot[1] = hotTwo;
        tvHot[2] = hotThree;
        tvHot[3] = hotFour;
        tvHot[4] = hotFive;
        tvHot[5] = hotSix;

        for(int i=0 ; i<tvHot.length ; i++){
            String hot = hotWords.get(i);
            if(!hot.equals("")){
                tvHot[i].setText(hot);
                tvHot[i].setVisibility(View.VISIBLE);
            }else{
                tvHot[i].setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onLoadHotWordsFailed() {

    }

    @OnClick({R.id.hotOne, R.id.hotTwo, R.id.hotThree, R.id.hotFour, R.id.hotFive, R.id.hotSix})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hotOne:
                content = hotOne.getText().toString().trim();
                loadData(false);
                break;
            case R.id.hotTwo:
                content = hotTwo.getText().toString().trim();
                loadData(false);
                break;
            case R.id.hotThree:
                content = hotThree.getText().toString().trim();
                loadData(false);
                break;
            case R.id.hotFour:
                content = hotFour.getText().toString().trim();
                loadData(false);
                break;
            case R.id.hotFive:
                content = hotFive.getText().toString().trim();
                loadData(false);
                break;
            case R.id.hotSix:
                content = hotSix.getText().toString().trim();
                loadData(false);
                break;
        }
    }

    private void updateSearchData() {
        // TODO Auto-generated method stub
		List<String> d = SearchHistoryUtils.getData(this);
        //锟斤拷取锟斤拷签锟斤拷锟斤拷锟斤拷锟斤拷
		searchDataList.clear();
//		for(SearchHistoryData data : d){
//			commentSearchMainDataList.add(data.getMain());
//		}
        searchDataList.addAll(d);
        //锟叫讹拷锟角凤拷锟斤拷示为锟斤拷锟斤拷示锟饺空硷拷
        if(searchDataList.size()<=0){
            notHistoryV.setVisibility(View.VISIBLE);
        }else{
            notHistoryV.setVisibility(View.GONE);
        }
        historyTagShowView.setDataList(searchDataList);
        //点击搜索
        historyTagShowView.setOnClickTagShowViewListener(new HistoryTagShowView.OnClickTagShowViewListener() {
            @Override
            public void onTagShowViewClick(int pos) {
                String key = searchDataList.get(pos);
                content = key;
                loadData(false);
            }
        });
    }

    @OnClick(R.id.act_search_delete_history)
    void deleteHistory(){
        SearchHistoryUtils.clearData(this);
        updateSearchData();
    }
}
