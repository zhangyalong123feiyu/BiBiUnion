package com.bibinet.biunion.project.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.PrivatePersonAddPresenter;
import com.bibinet.biunion.mvp.presenter.PrivatePersonDesinPresenter;
import com.bibinet.biunion.mvp.view.PrivatePersonDesinView;
import com.bibinet.biunion.project.adapter.PrivateAeraAdapter;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.BaseBean;
import com.bibinet.biunion.project.bean.PrivatePersonDesinBean;
import com.bibinet.biunion.project.ui.activity.PrivateInfoShowActivity;
import com.bibinet.biunion.project.ui.activity.PrivatePersonActivity;
import com.bibinet.biunion.project.utils.ConvertUtils;
import com.bibinet.biunion.project.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-1.
 */

@SuppressLint("ValidFragment")
public class PrivatePersonAddFragment extends BaseFragment {

    public static final int requestCode = 4001;
    public static final int resultCode = 4002;
    @BindView(R.id.act_private_add_net_error)
    View errorV;
    @BindView(R.id.areaRecyclerView)
    RecyclerView areaRecyclerView;
    private PrivatePersonAddPresenter privatePersonAddPresenter;
    //初始为空 根据请求结果更新数据 传入
    private String projectInfoType = "";
    private String typeInfoType = "";
    private String AreaTextView = "";
    private ConvertUtils convertUtils;
    private String[] info = {"项目信息", "招标信息", "采购信息"};
    private String[] type = {"工程", "货物", "服务"};
    private PrivateAeraAdapter aeraAdapter;
    private String infoType;
    private String projectType;
    private String areaInfo;
    private PrivatePersonDesinView designView = new PrivatePersonDesinView() {
        @Override
        public void onDesinSucess(BaseBean bean) {
            if (bean.getResCode().equals("0000")) {
                ToastUtils.showShort("您已订阅成功，请去首页点击右上角查看");
                PrivatePersonActivity activity = (PrivatePersonActivity) getActivity();
                activity.addSuccess();
            } else {
                Toast.makeText(getActivity(), bean.getResMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onDesinFailed(String s) {
            Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onGetDesinSucess(PrivatePersonDesinBean bean) {

        }

        @Override
        public void onGetDesinFailed(String s) {

        }
    };
    private View v;

    public PrivatePersonAddFragment(String infoType, String projectType, String areaInfo) {
        this.infoType = infoType;
        this.projectType = projectType;
        this.areaInfo = areaInfo;
    }

    private void load() {
        privatePersonAddPresenter.getPersonDesinPresenter(Integer.valueOf(Constants.loginresultInfo.getUser().getUserId()));
    }

    private void initView() {
        privatePersonAddPresenter = new PrivatePersonAddPresenter(getActivity(), designView);
        convertUtils = new ConvertUtils();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        areaRecyclerView.setLayoutManager(gridLayoutManager);
        aeraAdapter = new PrivateAeraAdapter(getActivity());
        aeraAdapter.setOnAreaClickLitioner(new PrivateAeraAdapter.AreaOnclickListioner() {
            @Override
            public void onAearClickListioner(View view, boolean isCancel) {
                if (isCancel) {
                    AreaTextView = "";
                    return;
                }
                AreaTextView = ((TextView) view).getText().toString();
            }
        });
        areaRecyclerView.setAdapter(aeraAdapter);
    }

    @OnClick({R.id.projcetInfo, R.id.tenderInfo, R.id.buyInfo, R.id.project, R.id.goods, R.id.service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.projcetInfo:
                if (v.findViewById(view.getId()).isSelected()) {
                    v.findViewById(view.getId()).setSelected(false);
                    projectInfoType = "";
                } else {
                    v.findViewById(view.getId()).setSelected(true);
                    projectInfoType = info[0];
                }
                v.findViewById(R.id.tenderInfo).setSelected(false);
                v.findViewById(R.id.buyInfo).setSelected(false);
                break;
            case R.id.tenderInfo:
                if (v.findViewById(view.getId()).isSelected()) {
                    v.findViewById(view.getId()).setSelected(false);
                    projectInfoType = "";
                } else {
                    v.findViewById(view.getId()).setSelected(true);
                    projectInfoType = info[1];
                }
                v.findViewById(R.id.projcetInfo).setSelected(false);
                v.findViewById(R.id.buyInfo).setSelected(false);
                break;
            case R.id.buyInfo:
                if (v.findViewById(view.getId()).isSelected()) {
                    v.findViewById(view.getId()).setSelected(false);
                    projectInfoType = "";
                } else {
                    v.findViewById(view.getId()).setSelected(true);
                    projectInfoType = info[2];
                }
                v.findViewById(R.id.projcetInfo).setSelected(false);
                v.findViewById(R.id.tenderInfo).setSelected(false);
                break;
            case R.id.project:
                if (v.findViewById(view.getId()).isSelected()) {
                    v.findViewById(view.getId()).setSelected(false);
                    typeInfoType = "";
                } else {
                    v.findViewById(view.getId()).setSelected(true);
                    typeInfoType = type[0];
                }
                v.findViewById(R.id.goods).setSelected(false);
                v.findViewById(R.id.service).setSelected(false);
                break;
            case R.id.goods:
                if (v.findViewById(view.getId()).isSelected()) {
                    v.findViewById(view.getId()).setSelected(false);
                    typeInfoType = "";
                } else {
                    v.findViewById(view.getId()).setSelected(true);
                    typeInfoType = type[1];
                }
                v.findViewById(R.id.project).setSelected(false);
                v.findViewById(R.id.service).setSelected(false);
                break;
            case R.id.service:
                if (v.findViewById(view.getId()).isSelected()) {
                    v.findViewById(view.getId()).setSelected(false);
                    typeInfoType = "";
                } else {
                    v.findViewById(view.getId()).setSelected(true);
                    typeInfoType = type[2];
                }
                v.findViewById(R.id.project).setSelected(false);
                v.findViewById(R.id.goods).setSelected(false);
                break;
        }
    }

    private boolean check() {
        if (projectInfoType.equals("") && typeInfoType.equals("") && AreaTextView.equals("")) {
            Toast.makeText(getActivity(), "请至少选择一个栏目", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

//    //查询订阅信息
//    @Override
//    public void onGetDesinSucess(PrivatePersonDesinBean bean) {
//        errorV.setVisibility(View.GONE);
//        addV.setVisibility(View.VISIBLE);
//        //根据信息结果判断界面展示
//        String infoType = bean.getInfoType();
//        String projectType = bean.getProjectType();
//        String areaInfo = bean.getRegionCode();
//        Log.e("x-x--x", infoType + "-" + projectType + "-" + areaInfo);
//
//    }
//
//    @Override
//    public void onGetDesinFailed(String s) {
//        Toast.makeText(getActivity(), "网络异常" + s, Toast.LENGTH_SHORT).show();
//        errorV.setVisibility(View.VISIBLE);
//        addV.setVisibility(View.GONE);
//    }

    //提交订阅
    @OnClick(R.id.postDesinInfo)
    void submit() {
        if (check()) {
            privatePersonAddPresenter.onPostPrivatePersonData(Integer.valueOf(Constants.loginresultInfo.getUser().getUserId()), projectInfoType, typeInfoType, AreaTextView);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_private_add;
    }

    @Override
    protected void onBaseViewCreated(View view, Bundle savedInstanceState) {
        this.v = view;
        //获取初始状态
        initView();
        areaRecyclerView.setFocusable(false);

        updateValue(infoType, projectType, areaInfo);
    }

    public void updateValue(String infoType, String projectType, String areaInfo) {
        //三个都是空 展示添加界面
        if ((infoType == null || infoType.equals("")) && (projectType == null || projectType.equals("")) && (areaInfo == null || areaInfo.equals(""))) {
            projectInfoType = "";
            typeInfoType = "";
            AreaTextView = "";
        } else {
            projectInfoType = infoType;
            typeInfoType = projectType;
            AreaTextView = areaInfo;
            switch (projectInfoType) {
                case "项目信息":
                    v.findViewById(R.id.projcetInfo).setSelected(true);
                    break;
                case "招标信息":
                    v.findViewById(R.id.tenderInfo).setSelected(true);
                    break;
                case "采购信息":
                    v.findViewById(R.id.buyInfo).setSelected(true);
                    break;
            }
            switch (typeInfoType) {
                case "工程":
                    v.findViewById(R.id.project).setSelected(true);
                    break;
                case "货物":
                    v.findViewById(R.id.goods).setSelected(true);
                    break;
                case "服务":
                    v.findViewById(R.id.service).setSelected(true);
                    break;
            }
            aeraAdapter.setSelectAreaTest(AreaTextView);
        }
    }
}
