package com.bibinet.biunion.project.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.TenderHelpPresenter;
import com.bibinet.biunion.mvp.view.TenderHelpView;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.BaseBean;
import com.bibinet.biunion.project.utils.PhoneNumberUtils;
import com.bibinet.biunion.project.utils.SoftKeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-14.
 */

public class TenderHelpActivity extends BaseActivity implements TenderHelpView {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_imageleft)
    ImageView titleImageleft;
    @BindView(R.id.writeBookInput)
    EditText writeBookInput;
    @BindView(R.id.contactPersonInput)
    EditText contactPersonInput;
    @BindView(R.id.contactType)
    EditText contactType;
    @BindView(R.id.postTenderhelp)
    Button postTenderhelp;
    @BindView(R.id.title_imageright)
    ImageView titleImageright;
    private TenderHelpPresenter tenderHelpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenderhelp);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title.setText("投标协助");
        titleImageleft.setVisibility(View.VISIBLE);
        titleImageright.setVisibility(View.VISIBLE);
        titleImageright.setImageResource(R.mipmap.daixiebiaoshu_lishijilv);
        tenderHelpPresenter = new TenderHelpPresenter(this);
    }

    @Override
    public void onUpLoadDataSucess(BaseBean bean) {
        if(bean.getResCode().equals("0000")){
            Toast.makeText(this, "提交成功，会有专人与您联系", Toast.LENGTH_SHORT).show();
            //跳转历史记录页面
            startActivity(new Intent(this, TenderHelpHistoryActivity.class));
            finish();
        }else{
            Toast.makeText(this, bean.getResMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onUpLoadDataFailed(String msg) {
        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.title_imageleft, R.id.postTenderhelp,R.id.title_imageright})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_imageleft:
                SoftKeyboardUtils.hintKbTwo(this);
                finish();
                break;
            case R.id.title_imageright:
                startActivity(new Intent(this,TenderHelpHistoryActivity.class));
                break;
            case R.id.postTenderhelp:
                String content = writeBookInput.getText().toString().trim();
                String contactPerson = contactPersonInput.getText().toString().trim();
                String contactWay = contactType.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(this, "请输入您需要协助的内容", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(contactPerson)) {
                    Toast.makeText(this, "请输入联系人", Toast.LENGTH_SHORT).show();
                } else if (!PhoneNumberUtils.isMobileNumber(contactWay)) {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                } else {
                    if (Constants.loginresultInfo != null) {
                        tenderHelpPresenter.upLoadData(contactPerson, contactWay, content, Constants.loginresultInfo.getUser().getUserId());
                    } else {
                        Toast.makeText(this, "您还没有登录，请登录后再进行操作", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
