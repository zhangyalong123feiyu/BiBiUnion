package com.bibinet.biunion.project.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.AskExpertsPresenter;
import com.bibinet.biunion.mvp.view.AskExpertsActivityView;
import com.bibinet.biunion.project.alipay.AlipayUtils;
import com.bibinet.biunion.project.alipay.PayResult;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.AskExpertsBean;
import com.bibinet.biunion.project.bean.ExpertsDataBean;
import com.bibinet.biunion.project.ui.dialog.WaitDialog;
import com.bibinet.biunion.project.utils.DialogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-19.
 */

public class AskExpertsActivtiy extends BaseActivity implements AskExpertsActivityView, View.OnClickListener, AlipayUtils.OnAlipayUtilsListener {
    public static int requestCode = 177;
    public static int resultCode = 178;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_imageright)
    ImageView titleImageright;
    @BindView(R.id.title_imageleft)
    ImageView titleImageleft;
    @BindView(R.id.userQuestion)
    EditText userQuestion;
    @BindView(R.id.questionContent)
    EditText questionContent;
    @BindView(R.id.isCallExperts)
    LinearLayout isCallExperts;
    @BindView(R.id.firstExperts)
    LinearLayout firstExperts;
    @BindView(R.id.secondExperts)
    LinearLayout secondExperts;
    @BindView(R.id.postData)
    Button postData;
    @BindView(R.id.callTextView)
    TextView callTextView;
    private TextView selectExperts;
    private TextView noSelectExperts;
    private String expertsCode;
    private AskExpertsPresenter askExpertsPresenter;
    private DialogUtils dialogUtils;
    private int expertsType = 1;

    private AlipayUtils alipayUtils;
    private String questionC;
    private String userId;
    private String quetion;
    private String compnyId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askexperts);
        ButterKnife.bind(this);
        initView();
        alipayUtils = new AlipayUtils();
    }

    private void initView() {
        title.setText("专家约谈");
        titleImageleft.setVisibility(View.VISIBLE);
        askExpertsPresenter = new AskExpertsPresenter(this);
    }

    @OnClick({R.id.title_imageleft, R.id.isCallExperts, R.id.firstExperts, R.id.secondExperts, R.id.postData})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_imageleft:
                finish();
                break;
            case R.id.isCallExperts:
                doCallExperts();
                break;
            case R.id.firstExperts:
                if (firstExperts.isSelected()) {
                    firstExperts.setSelected(false);
                } else {
                    firstExperts.setSelected(true);
                    secondExperts.setSelected(false);
                }
                break;
            case R.id.secondExperts:
                if (secondExperts.isSelected()) {
                    secondExperts.setSelected(false);
                } else {
                    secondExperts.setSelected(true);
                    firstExperts.setSelected(false);
                }
                break;
            case R.id.postData:
                doPostData();
                break;
        }
    }

    private void doPostData() {
        if (Constants.loginresultInfo != null) {
             userId = Constants.loginresultInfo.getUser().getUserId();
             compnyId = Constants.loginresultInfo.getUser().getEnterprise().getEnterpriseCode();
             quetion = userQuestion.getText().toString().trim();
             questionC = questionContent.getText().toString().trim();
            if (firstExperts.isSelected()) {
                expertsCode = "de794063d8b44aa0a51d93ea342c90b0";
                expertsType = 3;
            } else if (secondExperts.isSelected()) {
                expertsCode = "f922036506d34586a9c50e43b1b1059c";
                expertsType = 3;
            } else {
                expertsCode = "";
                expertsType = 1;
            }
            Log.e("-=", expertsType+"--"+callTextView.getText().toString());
            if (TextUtils.isEmpty(quetion) && TextUtils.isEmpty(questionC)) {
                Toast.makeText(this, "请确保您要提交的内容不为空", Toast.LENGTH_SHORT).show();
            }else if(expertsType == 1 && callTextView.getText().equals("是")){
                Toast.makeText(this, "请选择一个专家", Toast.LENGTH_SHORT).show();
            } else {
                askExpertsPresenter.postAskExpertsData(userId, compnyId, expertsType, expertsCode, quetion, questionC);
            }
        } else {
            Toast.makeText(this, "您还没有登录，请登录后在进行提问!", Toast.LENGTH_SHORT).show();
        }


    }

    private void doCallExperts() {
        dialogUtils = new DialogUtils();
        dialogUtils.diloagShow(AskExpertsActivtiy.this, R.layout.item_isselectexperts);
        View selectDialog = dialogUtils.getView();
        selectExperts = (TextView) selectDialog.findViewById(R.id.selectExperts);
        noSelectExperts = (TextView) selectDialog.findViewById(R.id.noSelectExperts);
        selectExperts.setOnClickListener(this);
        noSelectExperts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectExperts:
                firstExperts.setVisibility(View.VISIBLE);
                secondExperts.setVisibility(View.VISIBLE);
                callTextView.setText("是");
                dialogUtils.dialogDismiss();
                break;
            case R.id.noSelectExperts:
                expertsCode = "1";
                firstExperts.setVisibility(View.GONE);
                secondExperts.setVisibility(View.GONE);
                callTextView.setText("否");
                dialogUtils.dialogDismiss();
                firstExperts.setSelected(false);
                secondExperts.setSelected(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPostQuestionDataSucess(AskExpertsBean askExpertsBean) {
        if(askExpertsBean.getResCode().equals("0000")){
            String id = askExpertsBean.getOrderId();
            if(id!=null && !id.equals("")){
                Log.e("dsdf9", id+"_+");
                //取到签证 调用支付宝
                alipayUtils.startPay(this, id, this);
            }else{
                Toast.makeText(this, "您的问题已成功提交，稍后将会有人为您进行解答", Toast.LENGTH_SHORT).show();
                setResult(resultCode, null);
                finish();
            }
        }else{
            Toast.makeText(this, askExpertsBean.getResMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onPostQuestionDataFailed(String msg) {
        Toast.makeText(this, "未知错误，您的问题未能成提交，请稍后尝试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetExpertsDataSucess(List<ExpertsDataBean.ItemsBean> itemsBeanList) {

    }

    @Override
    public void onGetExpertsDataFailed(String msg) {

    }


    //支付结果回调
    @Override
    public void onFinish(PayResult payResult) {
        Toast.makeText(this, "您的问题已成功提交，稍后将会有人为您进行解答", Toast.LENGTH_SHORT).show();
        setResult(resultCode, null);
        finish();
    }

    @Override
    public void onFail(PayResult payResult) {
        Toast.makeText(this, "支付异常，请重新提交问题", Toast.LENGTH_SHORT).show();
    }

}
