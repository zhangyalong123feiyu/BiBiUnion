package com.bibinet.biunion.project.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.WriteTenderBookPresenter;
import com.bibinet.biunion.mvp.view.WriteTenderBookView;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.BaseBean;
import com.bibinet.biunion.project.utils.DialogUtils;
import com.bibinet.biunion.project.utils.PhoneNumberUtils;
import com.bibinet.biunion.project.utils.SoftKeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-6-14.
 */

public class WriteTenderBookActivity extends BaseActivity implements View.OnClickListener, WriteTenderBookView {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_imageright)
    ImageView titleImageright;
    @BindView(R.id.title_imageleft)
    ImageView titleImageleft;
    @BindView(R.id.writeBookInput)
    TextView writeBookInput;
    @BindView(R.id.projectTypeInput)
    TextView projectTypeInput;
    @BindView(R.id.selectTenderType)
    TextView selectTenderType;
    @BindView(R.id.tenderTypeInput)
    TextView tenderTypeInput;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.contanctPersonInput)
    EditText contanctPersonInput;
    @BindView(R.id.contactTypeInput)
    EditText contactTypeInput;
    @BindView(R.id.emailInput)
    EditText emailInput;
    @BindView(R.id.saveTenderBook)
    Button saveTenderBook;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.text1)
    TextView text1;
    private DialogUtils dialogUtils = new DialogUtils();
    private WriteTenderBookPresenter writeTenderBookPresenter;
    private int tenderbookselect = -1;
    private int tenderbookProjectType = -1;
    private int tenderbookType = -1;
    private int tenderbookTenderType = -1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_book);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title.setText("代写标书");
        titleImageleft.setVisibility(View.VISIBLE);
        titleImageright.setVisibility(View.VISIBLE);
        titleImageright.setImageResource(R.mipmap.daixiebiaoshu_lishijilv);
        writeTenderBookPresenter = new WriteTenderBookPresenter(this);
    }

    @OnClick({R.id.title_imageright, R.id.title_imageleft, R.id.writeBookInput, R.id.projectTypeInput, R.id.selectTenderType, R.id.tenderTypeInput, R.id.saveTenderBook})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_imageright:
                startActivity(new Intent(this, WriteTenderHistoryActivity.class));
                break;
            case R.id.title_imageleft:
                SoftKeyboardUtils.hintKbTwo(this);
                finish();
                break;
            case R.id.writeBookInput:  //标书
                dialogUtils.diloagShow(this, R.layout.item_bottomtenderbookselectdialog);
                View bottomView = dialogUtils.getView();
                TextView tenderbook = (TextView) bottomView.findViewById(R.id.TenderBook);
                TextView bidDoucments = (TextView) bottomView.findViewById(R.id.bidDoucments);
                tenderbook.setOnClickListener(this);
                bidDoucments.setOnClickListener(this);
                bottomView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogUtils.dialogDismiss();
                    }
                });
                break;
            case R.id.projectTypeInput:  //项目类型
                dialogUtils.diloagShow(this, R.layout.item_bottomprojecttypedialog);
                View bottomViewProjectType = dialogUtils.getView();
                TextView governmentText = (TextView) bottomViewProjectType.findViewById(R.id.governmentBuy);
                TextView noGovernmentText = (TextView) bottomViewProjectType.findViewById(R.id.noGovernmentBuy);
                governmentText.setOnClickListener(this);
                noGovernmentText.setOnClickListener(this);
                bottomViewProjectType.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogUtils.dialogDismiss();
                    }
                });
                break;
            case R.id.selectTenderType:  //招标方式
                dialogUtils.diloagShow(this, R.layout.item_bottomtendertypedialog);
                View bottomViewTenderType = dialogUtils.getView();
                TextView publiseTenderText = (TextView) bottomViewTenderType.findViewById(R.id.publishTender);
                TextView useLegalTenderText = (TextView) bottomViewTenderType.findViewById(R.id.useLegalTender);
                TextView askTenderText = (TextView) bottomViewTenderType.findViewById(R.id.askTender);
                publiseTenderText.setOnClickListener(this);
                useLegalTenderText.setOnClickListener(this);
                askTenderText.setOnClickListener(this);
                bottomViewTenderType.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogUtils.dialogDismiss();
                    }
                });
                break;
            case R.id.tenderTypeInput:   //标书类型
                dialogUtils.diloagShow(this, R.layout.item_bottomtenderbooktypedialog);
                View bottomViewTenderBook = dialogUtils.getView();
                TextView projectText = (TextView) bottomViewTenderBook.findViewById(R.id.project);
                TextView goodsText = (TextView) bottomViewTenderBook.findViewById(R.id.goods);
                TextView serviceText = (TextView) bottomViewTenderBook.findViewById(R.id.service);
                projectText.setOnClickListener(this);
                goodsText.setOnClickListener(this);
                serviceText.setOnClickListener(this);
                bottomViewTenderBook.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogUtils.dialogDismiss();
                    }
                });
                break;
            case R.id.saveTenderBook:
                String tenderBook = writeBookInput.getText().toString().trim();
                String tenderPro = projectTypeInput.getText().toString().trim();
                String tenderSelect = selectTenderType.getText().toString().trim();
                String tenderInput = tenderTypeInput.getText().toString().trim();
                String person = contanctPersonInput.getText().toString().trim();
                String contactWay = contactTypeInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                if (tenderbookselect == -1) {
                    Toast.makeText(this, "标书选择为空", Toast.LENGTH_SHORT).show();
                } else if (tenderbookProjectType == -1) {
                    Toast.makeText(this, "项目类型为空", Toast.LENGTH_SHORT).show();
                } else if (tenderbookType == -1) {
                    Toast.makeText(this, "招标方式为空", Toast.LENGTH_SHORT).show();
                } else if (tenderbookTenderType == -1) {
                    Toast.makeText(this, "标书类型为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(person)) {
                    Toast.makeText(this, "联系人不能为空", Toast.LENGTH_SHORT).show();
                } else if (!PhoneNumberUtils.isMobileNumber(contactWay)) {
                    Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                } else if (!PhoneNumberUtils.isEmail(email)) {
                    Toast.makeText(this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                } else {
                    if (Constants.loginresultInfo != null) {
                        writeTenderBookPresenter.saveWriteTenderBook(tenderbookselect, tenderbookProjectType, tenderbookType, tenderbookTenderType, person, contactWay, email, Constants.loginresultInfo.getUser().getUserId());
                    } else {
                        Toast.makeText(this, "您还没有登录，请登录后再进行操作", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.TenderBook://招标文件
                Log.i("TAG", "tenderbook点击");
                writeBookInput.setText("招标文件");
                dialogUtils.dialogDismiss();
                tenderbookselect = 0;
                break;
            case R.id.bidDoucments://投标文件
                writeBookInput.setText("投标文件");
                dialogUtils.dialogDismiss();
                tenderbookselect = 1;
                break;
            case R.id.governmentBuy://政府采购
                projectTypeInput.setText("政府采购");
                dialogUtils.dialogDismiss();
                tenderbookProjectType = 0;
                break;
            case R.id.noGovernmentBuy://非政府采购
                projectTypeInput.setText("非政府采购");
                dialogUtils.dialogDismiss();
                tenderbookProjectType = 1;
                break;
            case R.id.project://工程
                tenderTypeInput.setText("工程");
                dialogUtils.dialogDismiss();
                tenderbookTenderType = 0;
                break;
            case R.id.goods://货物
                tenderTypeInput.setText("货物");
                dialogUtils.dialogDismiss();
                tenderbookTenderType = 1;
                break;
            case R.id.service://服务
                tenderTypeInput.setText("服务");
                dialogUtils.dialogDismiss();
                tenderbookTenderType = 2;
                break;
            case R.id.publishTender://公开招标
                selectTenderType.setText("公开招标");
                dialogUtils.dialogDismiss();
                tenderbookType = 0;
                break;
            case R.id.useLegalTender://依法招标
                selectTenderType.setText("依法招标");
                tenderbookType = 1;
                dialogUtils.dialogDismiss();
                break;
            case R.id.askTender://邀请招标
                selectTenderType.setText("邀请招标");
                dialogUtils.dialogDismiss();
                tenderbookType = 2;
                break;
            default:
                break;
        }
    }



    @Override
    public void saveTenderBookSucess(BaseBean bean) {
        if(bean.getResCode().equals("0000")){
            Toast.makeText(this, "提交成功，会有专人与您联系", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, WriteTenderHistoryActivity.class));
            finish();
        }else{
            Toast.makeText(this, "很抱歉，您的标书申请提交失败", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void saveTenderBookFailed(String s) {
        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
    }
}
