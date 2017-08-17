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
import com.bibinet.biunion.mvp.view.MoreProjectView;
import com.bibinet.biunion.project.adapter.MoreItemAdapter;
import com.bibinet.biunion.project.adapter.MoreProjectAdapter;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.bean.ProjectInfoBean;
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

public class MoreProjectActivity extends PageActivity<ProjectInfoBean.ItemsBean> implements MoreProjectView, PublicPopWindowUtils.onPopDismissListener {
    @BindView(R.id.backImage)
    ImageView backImage;
    @BindView(R.id.projectIndustry)
    LinearLayout projectIndustry;
    @BindView(R.id.projectAeara)
    LinearLayout projectAeara;
    @BindView(R.id.projectTime)
    LinearLayout projectTime;

    @BindView(R.id.industryTextView)
    TextView industryTextView;
    @BindView(R.id.areaTextView)
    TextView areaTextView;
    @BindView(R.id.timeTextView)
    TextView timeTextView;

    @BindView(R.id.moreTitle)
    TextView moreTitle;
    @BindView(R.id.act_more_not_data)
    View notDataV;

    private MoreProjectPresenter projectPresenter;
    private List<String> projectDatas = new ArrayList<>();
    private String[] time = new String[]{"全部", "近一周", "近一个月", "近三个月"};
    private String[] industry = new String[]{"全部", "农、林、牧、渔业", "采矿业", "制造业", "电力、热力、燃气及水生产和供应业", "建筑业",
            "批发和零售业", "交通运输、仓储和邮政业", "住宿和餐饮业", "信息传输、软件和信息技术服务业", "金融业", "房地产业", "租赁和商务服务业", "科学研究和技术服务业", "水利、环境和公共设施管理业",
            "居民服务、修理和其他服务业", "教育", "卫生和社会工作", "文化、体育和娱乐业", "公共管理、社会保障和社会组织", "国际组织"};
    private String[] area = new String[]{"全部", "北京市", "天津市", "河北省", "山西省", "内蒙古自治区", "辽宁省", "吉林省", "黑龙江省", "上海市", "江苏省", "浙江省", "安徽省", "福建省", "江西省", "山东省"
            , "河南省", "湖北省", "湖南省", "广东省", "广西壮族自治区", "重庆市", "四川省", "贵州省", "云南省", "西藏自治区", "陕西省", "甘肃省", "青海省", "宁夏回族自治区", "新疆维吾尔自治区",
            "台湾省", "香港特别行政区", "澳门特别行政区"};
    private String selectText;
    private String selectType = "6";
    private String detailType;

    private ConvertUtils convert = new ConvertUtils();

    @Override
    protected int getNotDataViewId() {
        return R.id.act_more_not_data;
    }

    @Override
    protected PageActivityAdapter getPageAdapter(List<ProjectInfoBean.ItemsBean> dataList) {
        return new MoreProjectAdapter(this, dataList, selectType, detailType);
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
        return R.id.act_more_recycler;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.act_more_swipe_refresh;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_more;
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
        setTitl();
        projectPresenter = new MoreProjectPresenter(this);
    }

    private void setTitl() {
        Intent intent = getIntent();
        selectType = intent.getStringExtra("selectType");
        switch (Integer.parseInt(selectType)) {
            case 5:
                moreTitle.setText("项目信息");
                break;
            case 6:
                moreTitle.setText("招标信息");
                break;
            case 7:
                moreTitle.setText("采购信息");
                break;
            case 9:
                moreTitle.setText("供应商信息");
                break;
        }

    }

    //首页过来之后进行数据加载
    private void loadData(int pageNumb) {
        Intent intent = getIntent();
        selectType = intent.getStringExtra("selectType");
        detailType = intent.getStringExtra("detailType");
        doSelect(String.valueOf(convert.timeConvert(timeTextView.getText().toString())), String.valueOf(convert.industryConvert(industryTextView.getText().toString())), String.valueOf(convert.areaConvert(areaTextView.getText().toString())), pageNumb);
    }

    @OnClick({R.id.backImage, R.id.projectIndustry, R.id.projectAeara, R.id.projectTime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImage:
                finish();
                break;
            case R.id.projectIndustry:
                projectDatas = Arrays.asList(industry);
                select_type=1;
                if (projectIndustry.isSelected()) {
                    projectIndustry.setSelected(false);
                    publicPopWindowUtils.dissMisPopWindow();
                }else {
                    projectIndustry.setSelected(true);
                    projectAeara.setSelected(false);
                    projectTime.setSelected(false);
                    initPopView(projectDatas, industryTextView);
                }
                break;
            case R.id.projectAeara:
                projectDatas = Arrays.asList(area);
                select_type=2;
                if (projectAeara.isSelected()) {
                    projectAeara.setSelected(false);
                    publicPopWindowUtils.dissMisPopWindow();
                }else {
                    projectAeara.setSelected(true);
                    projectIndustry.setSelected(false);
                    projectTime.setSelected(false);
                    initPopView(projectDatas, areaTextView);
                }
                break;
            case R.id.projectTime:
                projectDatas = Arrays.asList(time);
                select_type=3;
                if (projectTime.isSelected()) {
                    projectTime.setSelected(false);
                    publicPopWindowUtils.dissMisPopWindow();
                }else {
                    projectTime.setSelected(true);
                    projectIndustry.setSelected(false);
                    projectAeara.setSelected(false);
                    initPopView(projectDatas, timeTextView);
                }
                break;
        }
    }
    private PublicPopWindowUtils publicPopWindowUtils = new PublicPopWindowUtils(this, this);
    private int select_type=1;

    @Override
    public void onDismiss() {
        switch (select_type) {
            case 1:
                projectIndustry.setSelected(false);
                break;
            case 2:
                projectAeara.setSelected(false);
                break;
            case 3:
                projectTime.setSelected(false);
                break;
            default:
                break;
        }
    }

    //开始进行查询
    private void initPopView(List<String> projectDatas, final TextView textView) {
        publicPopWindowUtils.showPopWindow(R.layout.item_popwindow);
        View view = publicPopWindowUtils.getPopview();
        RecyclerView popViewRecyclerView = (RecyclerView) view.findViewById(R.id.popRecyclerView);
        MoreItemAdapter adapter = new MoreItemAdapter(projectDatas, this);
        popViewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        popViewRecyclerView.setAdapter(adapter);
        adapter.setOnclickListioner(new MoreItemAdapter.ProjectTextClickListioner() {
            @Override
            public void onProjectTextClickLitioner(View view) {
                selectText = ((TextView) view).getText().toString();
                publicPopWindowUtils.dissMisPopWindow();
                switch (select_type) {
                    case 1:
                        projectIndustry.setSelected(false);
                        break;
                    case 2:
                        projectAeara.setSelected(false);
                        break;
                    case 3:
                        projectTime.setSelected(false);
                        break;

                    default:
                        break;
                }
                textView.setText(selectText);
                Log.i("TAG", "" + industryTextView.getText().toString());
                Log.i("TAG", "String.valueOf(convert.industryConvert(industryTextView.getText().toString()))" + String.valueOf(convert.industryConvert(industryTextView.getText().toString())));
                Log.i("TAG", "convert.timeConvert(timeTextView.getText().toString()" + String.valueOf(convert.timeConvert(timeTextView.getText().toString())));
                Log.i("TAG", "String.valueOf(convert.areaConvert(areaTextView.getText().toString()))" + String.valueOf(convert.areaConvert(areaTextView.getText().toString())));
                Log.i("TAG", "detailType" + detailType.toString());
                //直接请求 不采用父类的加载效果
//                doSelect(String.valueOf(convert.timeConvert(timeTextView.getText().toString())), String.valueOf(convert.industryConvert(industryTextView.getText().toString())), String.valueOf(convert.areaConvert(areaTextView.getText().toString())), 1);
                initRefresh();
            }
        });
        publicPopWindowUtils.showPopWindow(textView);
    }

    private void doSelect(String time, String industry, String ar, int pageNumb) {
        switch (Integer.parseInt(selectType)) {
            case 5:
                projectPresenter.LoadHomeDataMoreProjcetInfo(pageNumb, Integer.parseInt(detailType), Integer.parseInt(time), industry, Integer.parseInt(ar));
                break;
            case 6:
                projectPresenter.LoadHomeDataMoreTenderInfo(pageNumb, Integer.parseInt(detailType), Integer.parseInt(time), industry, Integer.parseInt(ar));
                break;
            case 7:
                projectPresenter.LoadHomeDataMoreBuyInfo(pageNumb, Integer.parseInt(detailType), Integer.parseInt(time), industry, Integer.parseInt(ar));
                break;
            case 8:
                projectPresenter.LoadHomeDataMorePProjectInfo(pageNumb, Integer.parseInt(detailType), Integer.parseInt(time), industry, Integer.parseInt(ar));
                break;
            case 9:
                projectPresenter.LoadHomeDataMoreApplayProjectInfo(pageNumb, Integer.parseInt(detailType), Integer.parseInt(time), industry, Integer.parseInt(ar));
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //详情页回调关注结果
        if(requestCode == H5Activity.requestCode && resultCode == H5Activity.resultCode){
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
