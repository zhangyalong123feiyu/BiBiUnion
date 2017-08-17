package com.bibinet.biunion.project.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.PrivatePersonDesinPresenter;
import com.bibinet.biunion.mvp.view.PrivatePersonDesinView;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.BaseBean;
import com.bibinet.biunion.project.bean.PrivatePersonDesinBean;
import com.bibinet.biunion.project.ui.activity.PrivateInfoShowActivity;
import com.bibinet.biunion.project.ui.activity.PrivatePersonActivity;
import com.bibinet.biunion.project.utils.ConvertUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-1.
 */

@SuppressLint("ValidFragment")
public class PrivatePersonDesignFragment extends BaseFragment {

    @BindView(R.id.act_private_design_net_error)
    View netErrorV;

    @BindView(R.id.act_private_update_view)
    View mainV;
    //更新界面
    @BindView(R.id.act_private_update_project_info_type_txt)
    TextView updateProjectInfoTypeTV;
    @BindView(R.id.act_private_update_type_info_img)
    ImageView updateTypeInfoIV;
    @BindView(R.id.act_private_update_area_txt)
    TextView updateAreaTV;
    @BindView(R.id.act_private_update_project_info_type)
    View updateProjectInfoTypeV;
    @BindView(R.id.act_private_update_type_info)
    View updateTypeInfoV;
    @BindView(R.id.act_private_update_area)
    View updateAreaV;

    //初始为空 根据请求结果更新数据
    private String projectInfoType = "";
    private String typeInfoType = "";
    private String AreaTextView = "";
    private ConvertUtils convertUtils;
    private String[] info = {"项目信息", "招标信息", "采购信息"};
    private String[] type = {"工程", "货物", "服务"};
    private int startType = 0;

    private String infoType;
    private String projectType;
    private String areaInfo;

    private PrivatePersonDesinPresenter privatePersonDesinPresenter;
    private PrivatePersonActivity privatePersonActivity;

    public PrivatePersonDesignFragment(String infoType, String projectType, String areaInfo) {
        this.infoType = infoType;
        this.projectType = projectType;
        this.areaInfo = areaInfo;
    }

    private void initView() {
        privatePersonActivity = (PrivatePersonActivity) getActivity();
        privatePersonDesinPresenter = new PrivatePersonDesinPresenter(privatePersonActivity);
        updateProjectInfoTypeTV.setSelected(true);
        updateTypeInfoIV.setSelected(true);
        updateAreaTV.setSelected(true);
        convertUtils = new ConvertUtils();
    }


    @OnClick(R.id.act_private_update_area_delete)
    void updateAreaDelete(){
        AreaTextView = "";
        privatePersonDesinPresenter.onPostPrivatePersonData(Integer.valueOf(Constants.loginresultInfo.getUser().getUserId()), projectInfoType, typeInfoType, AreaTextView);
    }

    @OnClick(R.id.act_private_update_type_info_delete)
    void updateTypeInfoDelete(){
        typeInfoType = "";
        privatePersonDesinPresenter.onPostPrivatePersonData(Integer.valueOf(Constants.loginresultInfo.getUser().getUserId()), projectInfoType, typeInfoType, AreaTextView);
    }

    @OnClick(R.id.act_private_update_project_info_delete)
    void updateProjectInfoDelete(){
        projectInfoType = "";
        privatePersonDesinPresenter.onPostPrivatePersonData(Integer.valueOf(Constants.loginresultInfo.getUser().getUserId()), projectInfoType, typeInfoType, AreaTextView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_private_design;
    }

    @Override
    protected void onBaseViewCreated(View view, Bundle savedInstanceState) {
        initView();

        updateValue(infoType, projectType, areaInfo);
    }

    public void updateValue(String infoType, String projectType, String areaInfo) {
        //根据传入的值初始化界面
        mainV.setVisibility(View.VISIBLE);
        //依次更新定制
        if(infoType.equals("")){
            updateProjectInfoTypeV.setVisibility(View.GONE);
        }else{
            updateProjectInfoTypeV.setVisibility(View.VISIBLE);
            updateProjectInfoTypeTV.setText(infoType);
        }
        projectInfoType = infoType;
        if(projectType.equals("")){
            updateTypeInfoV.setVisibility(View.GONE);
        }else if(projectType.equals("工程")){
            updateTypeInfoV.setVisibility(View.VISIBLE);
            updateTypeInfoIV.setImageResource(R.drawable.select_private_persion_design_project_img);
        }else if(projectType.equals("货物")){
            updateTypeInfoV.setVisibility(View.VISIBLE);
            updateTypeInfoIV.setImageResource(R.drawable.select_private_persion_design_good_img);
        }else if(projectType.equals("服务")){
            updateTypeInfoV.setVisibility(View.VISIBLE);
            updateTypeInfoIV.setImageResource(R.drawable.select_private_persion_design_service_img);
        }
        typeInfoType = projectType;
        if(areaInfo == null || areaInfo.equals("")){
            updateAreaV.setVisibility(View.GONE);
        }else{
            updateAreaV.setVisibility(View.VISIBLE);
            updateAreaTV.setText(areaInfo);
        }
        AreaTextView = areaInfo;
    }
}
