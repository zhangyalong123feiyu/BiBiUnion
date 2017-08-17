package com.bibinet.biunion.project.ui.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.FragmentHomePresenter;
import com.bibinet.biunion.mvp.view.FragmentHomeView;
import com.bibinet.biunion.project.adapter.SocailFooterAdapter;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.BannerBean;
import com.bibinet.biunion.project.bean.ProjectInfoBean;
import com.bibinet.biunion.project.bean.cityselectbean.City;
import com.bibinet.biunion.project.ui.activity.H5Activity;
import com.bibinet.biunion.project.ui.activity.LoginActivity;
import com.bibinet.biunion.project.ui.activity.MainActivity;
import com.bibinet.biunion.project.ui.activity.MoreProjectActivity;
import com.bibinet.biunion.project.ui.activity.PrivatePersonActivity;
import com.bibinet.biunion.project.ui.activity.SearchActivity;
import com.bibinet.biunion.project.ui.activity.SelectAddressActivity;
import com.bibinet.biunion.project.ui.activity.SelectCityActivity;
import com.bibinet.biunion.project.ui.expand.PageActivityAdapter;
import com.bibinet.biunion.project.ui.expand.PageFragment;
import com.bibinet.biunion.project.utils.ConvertUtils;
import com.bibinet.biunion.project.utils.HomePopWindowUtils;
import com.bibinet.biunion.project.utils.LoactionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Homex extends PageFragment<ProjectInfoBean.ItemsBean> implements FragmentHomeView, View.OnClickListener {
    private final int projectInfoType = 5;
    private final int tenderProjectInfoType = 6;
    private final int buyProjectInfoType = 7;
    private final int pProjectInfoType = 8;
    private final int applayProjectInfoType = 9;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.projectInfo)
    TextView projectInfo;
    @BindView(R.id.projectNameOne)
    RadioButton projectNameOne;
    @BindView(R.id.projectNameTwo)
    RadioButton projectNameTwo;
    @BindView(R.id.projectNameThree)
    RadioButton projectNameThree;
    @BindView(R.id.moreProject)
    ImageView moreProject;
    @BindView(R.id.homeSearch)
    LinearLayout homeSearch;
    @BindView(R.id.privateOderingImage)
    ImageView privateOderingImage;
    @BindView(R.id.projectNameOneBottomLine)
    View projectNameOneBottomLine;
    @BindView(R.id.projectNameTwoBottomLine)
    View projectNameTwoBottomLine;
    @BindView(R.id.projectNameThreeBottomLine)
    View projectNameThreeBottomLine;
    @BindView(R.id.fra_home_radio)
    RadioGroup radioGroup;
    @BindView(R.id.imageView4)
    View icon;
    private View view;
    private LoactionUtils loactionUtils;
    private int pageNum = 1;
    private int detailType = 1;
    private FragmentHomePresenter presenter;
    private int selectType = 5;

    private HomePopWindowUtils popWindowUtils;
    private List<BannerBean.ItemBean> bannerInfoList = new ArrayList<>();

    private ConvertUtils convertUtils = new ConvertUtils();
    private City citySlect;
    private int nowLocation;

    public Fragment_Homex() {
        // Required empty public constructor
    }


    @Override
    protected int getNotDataViewId() {
        return -1;
    }

    @Override
    protected PageActivityAdapter getPageAdapter(List<ProjectInfoBean.ItemsBean> dataList) {
        return new SocailFooterAdapter(getActivity(), dataList, bannerInfoList, selectType, detailType);
    }

    @Override
    protected void initLoad(int pageNumb) {
        loadData(pageNumb);
    }

    @Override
    protected void onChildCreate(View view, Bundle savedInstanceState) {
        initView();
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.fra_home_recycler;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.fra_home_swipe_refresh;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homex;
    }

    @Override
    protected void nextPageLoad(int pageNumb) {
        loadData(pageNumb);
    }

    @Override
    protected void refreshLoad(int pageNumb) {
        loadData(pageNumb);
        presenter.getBannerData();
    }

    private void initView() {
        presenter = new FragmentHomePresenter(this);
        //发banner图请求
        presenter.getBannerData();
        //定位
        loactionUtils = new LoactionUtils(getActivity(), location);
        loactionUtils.startLoaction();

        //请求定位信息
//        doRefresh();
    }

    public void doRefresh() {
        final int nowLocation = convertUtils.areaConvert(location.getText().toString().trim());
        presenter.getBannerData();
    }

    private void loadData(int pageNum) {
        selectDataSource(pageNum);
    }

    //选择加载不同的数据类型
    private void selectDataSource(int pageNum) {
//        final int nowLocation = convertUtils.areaConvert(location.getText().toString().trim());
        if (citySlect!=null) {
             nowLocation = Integer.parseInt(citySlect.getNo());
            Log.i("TAG","loctionCode---------------------"+nowLocation);
        		}else {
            nowLocation=140000;
        }
        switch (selectType) {
            case projectInfoType:
                presenter.LoadHomeDataProjcetInfo(pageNum, detailType, nowLocation);
                break;
            case tenderProjectInfoType:
                presenter.LoadHomeDataTenderInfo(pageNum, detailType, nowLocation);
                break;
            case buyProjectInfoType:
                presenter.LoadHomeDataBuyInfo(pageNum, detailType, nowLocation);
                break;
            case pProjectInfoType:
                presenter.LoadHomeDataPProjectInfo(pageNum, detailType, nowLocation);
                break;
            case applayProjectInfoType:
                presenter.LoadHomeDataApplayProjectInfo(pageNum, detailType, nowLocation);
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.location, R.id.projectInfo, R.id.projectNameOne, R.id.projectNameTwo, R.id.projectNameThree, R.id.moreProject, R.id.homeSearch, R.id.privateOderingImage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location:
                //选择城市
                Intent intentCityName = new Intent(getActivity(), SelectAddressActivity.class);
                startActivityForResult(intentCityName, 1);
                break;
            case R.id.privateOderingImage:
                if (!checkLogin()) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                Intent intent = new Intent(getActivity(), PrivatePersonActivity.class);
                intent.putExtra("START_TYPE", 1);
                startActivity(intent);
                break;
            case R.id.projectInfo:
                selectProjectType();
                break;
            case R.id.projectNameOne:
                projectNameOneBottomLine.setVisibility(View.VISIBLE);
                projectNameTwoBottomLine.setVisibility(View.INVISIBLE);
                projectNameThreeBottomLine.setVisibility(View.INVISIBLE);
                detailType = 1;
                if (selectType == 8) {
                    selectType = 5;
                }
                initRefresh();
                break;
            case R.id.projectNameTwo:
                projectNameOneBottomLine.setVisibility(View.INVISIBLE);
                projectNameTwoBottomLine.setVisibility(View.VISIBLE);
                projectNameThreeBottomLine.setVisibility(View.INVISIBLE);
                detailType = 2;
                if (selectType == 8) {
                    selectType = 5;
                }
                initRefresh();
                break;
            case R.id.projectNameThree:
                if (projectNameThree.getText().toString().equals("")) {
                    projectNameThree.setChecked(false);
                    return;
                }
                projectNameOneBottomLine.setVisibility(View.INVISIBLE);
                projectNameTwoBottomLine.setVisibility(View.INVISIBLE);
                projectNameThreeBottomLine.setVisibility(View.VISIBLE);

                detailType = 3;
                if (selectType == 5) {
                    selectType = 8;
                    initRefresh();
                } else {
                    initRefresh();
                }
                break;
            case R.id.moreProject:
                Intent intent2 = new Intent(getActivity(), MoreProjectActivity.class);
                intent2.putExtra("selectType", String.valueOf(selectType));
                intent2.putExtra("detailType", String.valueOf(detailType));
                startActivity(intent2);
                break;
            case R.id.homeSearch:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }
    }

    private boolean checkLogin() {
        if (Constants.loginresultInfo != null && Constants.loginresultInfo.getUser() != null && Constants.loginresultInfo.getUser().getUserId() != null && !Constants.loginresultInfo.getUser().getUserId().equals("")) {
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
//                String cityName = data.getStringExtra("resultCityNameHot");
//                location.setText(cityName);
                Bundle cityData = data.getExtras();
                citySlect =(City)cityData.get("resultCityNameHot");
                location.setText(citySlect.getName());
                initRefresh();
            }
        }
    }

    public void updateCollectResult(int pos, boolean isCollect){
        getDataList().get(pos).setProjectIsCollect(isCollect);
        getAdapter().notifyDataSetChanged();
    }

    //弹出选着项目类型对话框
    private void selectProjectType() {
        popWindowUtils = new HomePopWindowUtils(getActivity(), projectInfo, icon);
        popWindowUtils.showPopWindow();
        View popview = popWindowUtils.getPopView();
        TextView popwProjectInfo = (TextView) popview.findViewById(R.id.projectInfo);
        TextView projectTenderInfo = (TextView) popview.findViewById(R.id.tenderInfo);
        TextView projectBuyInfo = (TextView) popview.findViewById(R.id.buyprojectInfo);
        TextView projectProvideInfo = (TextView) popview.findViewById(R.id.provideProjectInfo);
        popwProjectInfo.setOnClickListener(this);
        projectTenderInfo.setOnClickListener(this);
        projectBuyInfo.setOnClickListener(this);
        projectProvideInfo.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 111:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "定位权限开启失败", Toast.LENGTH_SHORT).show();
                } else {
                }
                break;
            case 222:
                if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "存储权限开启失败", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }


    @Override
    public void onLoadBannerSucess(List<BannerBean.ItemBean> bannerInfo) {
        bannerInfoList.clear();
        bannerInfoList.addAll(bannerInfo);
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadBannerFailed() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loactionUtils.destroyLocation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tenderInfo:
                popWindowUtils.disMissPopWindow();
                projectInfo.setText(R.string.tenderInfo);
                projectNameOne.setText("招标公告");
                projectNameTwo.setText("中标候选人公示");
                projectNameThree.setText("中标公告");
                selectType = 6;
                initRefresh();
                break;
            case R.id.projectInfo:
                popWindowUtils.disMissPopWindow();
                projectInfo.setText(R.string.projectInfo);
                projectNameOne.setText("拟在建项目");
                projectNameTwo.setText("业主委托项目");
                projectNameThree.setText("PPP项目");
                if (projectNameThree.isChecked()) {
                    selectType=8;
                		}else {
                    selectType = 5;
                }
                initRefresh();
                break;
            case R.id.buyprojectInfo:
                if (detailType == 3) {
                    detailType = 2;
                    radioGroup.check(R.id.projectNameTwo);
                    projectNameOneBottomLine.setVisibility(View.INVISIBLE);
                    projectNameTwoBottomLine.setVisibility(View.VISIBLE);
                    projectNameThreeBottomLine.setVisibility(View.INVISIBLE);
                }
                popWindowUtils.disMissPopWindow();
                projectInfo.setText(R.string.buyProjectInfo);
                projectNameOne.setText("政府采购");
                projectNameTwo.setText("企业采购");
                projectNameThree.setText("");
                selectType = 7;
                initRefresh();
                break;
            case R.id.provideProjectInfo:
                popWindowUtils.disMissPopWindow();
                projectInfo.setText(R.string.provideProjectInfo);
                projectNameOne.setText("供应商");
                projectNameTwo.setText("采购业主");
                projectNameThree.setText("招标机构");
                selectType = 9;
                initRefresh();
                break;
        }
    }

}
