package com.bibinet.biunion.project.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.PrivatePersonDesinPresenter;
import com.bibinet.biunion.mvp.view.PrivatePersonDesinView;
import com.bibinet.biunion.project.adapter.PrivateAeraAdapter;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.BaseBean;
import com.bibinet.biunion.project.bean.PrivatePersonDesinBean;
import com.bibinet.biunion.project.ui.fragment.PrivatePersonAddFragment;
import com.bibinet.biunion.project.ui.fragment.PrivatePersonDesignFragment;
import com.bibinet.biunion.project.utils.ConvertUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-1.
 */

public class PrivatePersonActivity extends BaseActivity implements PrivatePersonDesinView {

    @BindView(R.id.act_private_net_error)
    View netErrorV;

    @BindView(R.id.act_private_main_view)
    View mainV;

    @BindView(R.id.act_private_add)
    View rightAddV;

    private PrivatePersonDesinPresenter privatePersonDesinPresenter;
    private PrivatePersonAddFragment privatePersonAddFragment;

    private int startType = 0;


    private int REQUEST_CODE = 2001;
    private int RESULT_CODE = 2002;
    private PrivatePersonDesignFragment privatePersonDesignFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private);
        ButterKnife.bind(this);

        startType = getIntent().getIntExtra("START_TYPE", 0);
        initView();

        //查询订阅
        load();
    }

    private void load() {
        privatePersonDesinPresenter.getPersonDesinPresenter(Integer.valueOf(Constants.loginresultInfo.getUser().getUserId()));
    }

    private void initView() {
        privatePersonDesinPresenter = new PrivatePersonDesinPresenter(this);
    }

    @OnClick(R.id.act_private_exit)
    public void onViewClicked(View view) {
        finish();
    }

    @Override
    public void onDesinSucess(BaseBean bean) {
        if (bean.getResCode().equals("0000")) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
            load();
        } else {
            Toast.makeText(this, bean.getResMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDesinFailed(String s) {
        Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
    }

    //查询订阅信息
    @Override
    public void onGetDesinSucess(PrivatePersonDesinBean bean) {
        netErrorV.setVisibility(View.GONE);
        //根据信息结果判断界面展示
        String infoType = bean.getInfoType();
        String projectType = bean.getProjectType();
        String areaInfo = bean.getRegionCode();
        Log.e("x-x--x", infoType + "-" + projectType + "-" + areaInfo);
        //三个都是空 跳转编辑界面
        if (startType == 2 || (infoType == null || infoType.equals("")) && (projectType == null || projectType.equals("")) && (areaInfo == null || areaInfo.equals(""))) {
            if (true) {
                privatePersonAddFragment = new PrivatePersonAddFragment(infoType, projectType, areaInfo);
                getSupportFragmentManager().beginTransaction().replace(R.id.act_private_main_view, privatePersonAddFragment).commit();
            } else {
                privatePersonAddFragment.updateValue(infoType, projectType, areaInfo);
            }

            rightAddV.setVisibility(View.GONE);
            Log.e("x-x--x", "add");
        } else {
            //跳转页面
            if (startType == 1) {
                startActivity(new Intent(this, PrivateInfoShowActivity.class));
                finish();
                return;
            }
            rightAddV.setVisibility(View.VISIBLE);
            if (true) {
                privatePersonDesignFragment = new PrivatePersonDesignFragment(infoType, projectType, areaInfo);
                getSupportFragmentManager().beginTransaction().replace(R.id.act_private_main_view, privatePersonDesignFragment).commit();
            } else {
                privatePersonDesignFragment.updateValue(infoType, projectType, areaInfo);
            }

            Log.e("x-x--x", "design");
        }
    }

    @OnClick(R.id.act_private_add)
    void add() {
        Intent intent = new Intent(this, PrivatePersonActivity.class);
        intent.putExtra("START_TYPE", 2);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onGetDesinFailed(String s) {
        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
        netErrorV.setVisibility(View.VISIBLE);
        mainV.setVisibility(View.GONE);
    }


    //订阅成功
    public void addSuccess() {
        //根据类型判断退出或者退出跳转
//            if (startType == 1) {
//                startActivity(new Intent(this, PrivateInfoShowActivity.class));
//                finish();
//            } else {
//                setResult(resultCode);
//                startActivity(new Intent(this, PrivatePersonDesignActivity.class));
//                finish();
//            }
        switch (startType) {
            case 2:
                //返回上个页面加载
                setResult(RESULT_CODE);
                finish();
                break;
            case 1:
                //直接跳转
                Intent intent = new Intent(this, PrivateInfoShowActivity.class);
                intent.putExtra("START_TYPE", 1);
                startActivity(intent);
                finish();
                break;
            default:
                load();
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            load();
        }
    }
}
