package com.bibinet.biunion.project.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.bibinet.biunion.project.utils.DialogUtils;
import com.bibinet.biunion.project.utils.cityselectutil.Base64MapUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-19.
 */

public class AskExpertsActivtiyx extends BaseActivity implements AskExpertsActivityView, View.OnClickListener, AlipayUtils.OnAlipayUtilsListener {
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
    @BindView(R.id.postData)
    Button postData;
    @BindView(R.id.callTextView)
    TextView callTextView;
    @BindView(R.id.expertsContainer)
    LinearLayout expertsContainer;
    private TextView selectExperts;
    private TextView noSelectExperts;
    private String expertsCode="";
    private AskExpertsPresenter askExpertsPresenter;
    private DialogUtils dialogUtils;
    private int expertsType = 3;
    private AlipayUtils alipayUtils;
    private String questionC;
    private String compnyId;
    private String quetion;
    private String userId;
    private String questionCode="";
    private int selectId=-1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askexpertsx);
        ButterKnife.bind(this);
        initView();
        alipayUtils = new AlipayUtils();
    }

    private void initView() {
        title.setText("专家约谈");
        titleImageleft.setVisibility(View.VISIBLE);
        askExpertsPresenter = new AskExpertsPresenter(this);
        askExpertsPresenter.getExpertsData();
    }

    @OnClick({R.id.title_imageleft, R.id.isCallExperts, R.id.postData})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_imageleft:
                finish();
                break;
            case R.id.isCallExperts:
//                doCallExperts();
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

            if (TextUtils.isEmpty(quetion)||TextUtils.isEmpty(questionC)) {
                Toast.makeText(this, "请确保您要提交的问题或内容不为空", Toast.LENGTH_SHORT).show();
            } else if (expertsType == 1 && callTextView.getText().equals("是")) {
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
        dialogUtils.diloagShow(AskExpertsActivtiyx.this, R.layout.item_isselectexperts);
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
                callTextView.setText("是");
                dialogUtils.dialogDismiss();
                expertsType=3;
                expertsContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.noSelectExperts:
                callTextView.setText("否");
                expertsType=1;
                expertsContainer.setVisibility(View.GONE);
                dialogUtils.dialogDismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPostQuestionDataSucess(AskExpertsBean askExpertsBean) {
        if (askExpertsBean.getResCode().equals("0000")) {
            String id = askExpertsBean.getOrderId();
            if (id != null && !id.equals("")) {
                Log.e("dsdf9", id + "_+");
                //取到签证 调用支付宝
                alipayUtils.startPay(this, id, this);
            } else {
                Toast.makeText(this, "您的问题已提交，稍后会有专员为您解答", Toast.LENGTH_SHORT).show();
                setResult(resultCode, null);
                finish();
            }
        } else {
            Toast.makeText(this, askExpertsBean.getResMessage(), Toast.LENGTH_SHORT).show();
        }
        questionCode=askExpertsBean.getQuestionCode();
    }

    @Override
    public void onPostQuestionDataFailed(String msg) {
        Toast.makeText(this, "未知错误，您的问题未能成提交，请稍后尝试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetExpertsDataSucess(final List<ExpertsDataBean.ItemsBean> itemsBeanList) {
        expertsContainer.removeAllViews();
        	for (int i = 0; i < itemsBeanList.size(); i++) {
                final View view = LayoutInflater.from(this).inflate(R.layout.item_expets, null);
                ImageView expertsPhoto = (ImageView) view.findViewById(R.id.expertsPhoto);
                final LinearLayout expertsLinear = (LinearLayout) view.findViewById(R.id.expertsLinear);
                TextView expertsTitle = (TextView) view.findViewById(R.id.expertTitle);
                TextView expertsContent = (TextView) view.findViewById(R.id.expertsContent);
                TextView expertsMoney = (TextView) view.findViewById(R.id.expertsMoney);
                if (itemsBeanList.get(i).getCode().equals("44782de2a71e4b9fba3ff7d9dad895ee")) {
                    expertsPhoto.setImageResource(R.mipmap.chengzhuanjia_touxiang);
                		}else if (itemsBeanList.get(i).getCode().equals("eec92cc6279d40d4b3c400dced3dceec")) {
                    expertsPhoto.setImageResource(R.mipmap.yuzhuanjia_touxiang2);
                				}else if (itemsBeanList.get(i).getCode().equals("de695c2cdb71401b9af782a58f71f9df")) {
                    expertsPhoto.setImageResource(R.mipmap.shizhuanjia);
                						}else {
                    expertsPhoto.setImageResource(R.mipmap.zhangzhuanjia);
                }
                expertsTitle.setText(itemsBeanList.get(i).getTitle());
                expertsContent.setText(itemsBeanList.get(i).getDescription());
                expertsMoney.setText(itemsBeanList.get(i).getPrice()+"￥");
                expertsContainer.addView(view);
                final int finalI = i;
                expertsLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectId = finalI;
                    	for (int i = 0; i <expertsContainer.getChildCount(); i++) {
                            if (selectId==i) {
                                expertsContainer.getChildAt(i).setSelected(true);
                                expertsCode=itemsBeanList.get(i).getCode();
                            		}else {
                                expertsContainer.getChildAt(i).setSelected(false);

                            }
                    		}
                    }
                });
        		}

    }

    @Override
    public void onGetExpertsDataFailed(String msg) {

    }

    //支付结果回调
    @Override
    public void onFinish(PayResult payResult) {
        Toast.makeText(this, "您的问题已成功提交，稍后将会有人为您进行解答", Toast.LENGTH_SHORT).show();
        askExpertsPresenter.upLoadPayResult(questionCode,1);
        setResult(resultCode, null);
        finish();
    }

    @Override
    public void onFail(PayResult payResult) {
        askExpertsPresenter.upLoadPayResult(questionCode,0);
        Toast.makeText(this, "支付异常，请重新提交问题", Toast.LENGTH_SHORT).show();
    }

}
