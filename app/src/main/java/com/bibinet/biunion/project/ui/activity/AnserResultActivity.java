package com.bibinet.biunion.project.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bibinet.biunion.R;
import com.bibinet.biunion.mvp.presenter.AnserResultPresenter;
import com.bibinet.biunion.mvp.view.AnserResultActivityView;
import com.bibinet.biunion.project.application.BaseActivity;
import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.bean.PostRatingResultBean;
import com.bibinet.biunion.project.bean.RatingResultBean;
import com.bibinet.biunion.project.utils.cityselectutil.Base64MapUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bibinet on 2017-7-27.
 */

public class AnserResultActivity extends BaseActivity implements AnserResultActivityView {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_imageleft)
    ImageView titleImageleft;
    @BindView(R.id.questionTitle)
    TextView questionTitle;
    @BindView(R.id.Askcontent)
    TextView Askcontent;
    @BindView(R.id.expertsPhoto)
    ImageView expertsPhoto;
    @BindView(R.id.expertsAnser)
    TextView expertsAnser;
    @BindView(R.id.expertsStar)
    RatingBar expertsStar;
    @BindView(R.id.writeRatingContent)
    EditText writeRatingContent;
    @BindView(R.id.postRating)
    Button postRating;
    @BindView(R.id.isHasExperts)
    LinearLayout isHasExperts;
    @BindView(R.id.anserTime)
    TextView anserTime;
    private AnserResultPresenter anserResultPresenter;
    private String anserCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anserresult);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        title.setText("问答结果");
        titleImageleft.setVisibility(View.VISIBLE);
        String objectId = getIntent().getStringExtra("userobjectId");
        anserResultPresenter = new AnserResultPresenter(this);
        anserResultPresenter.getPostRating(objectId);
    }

    @OnClick({R.id.title_imageleft, R.id.postRating})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_imageleft:
                finish();
                break;
            case R.id.postRating:
                String ratingContent = writeRatingContent.getText().toString();
                if (TextUtils.isEmpty(ratingContent)) {
                    Toast.makeText(this,"请确保您的评价内容不要为空！",Toast.LENGTH_SHORT).show();
                		}else {
                    int ratingStars =(int)expertsStar.getRating();
                    anserResultPresenter.postRating(anserCode,ratingStars,ratingContent, Constants.loginresultInfo.getUser().getUserId());
                }
                break;
        }
    }

    @Override
    public void getPostRatingSucess(RatingResultBean ratingInfo) {
        questionTitle.setText(ratingInfo.getQuestionTitle());
        Askcontent.setText(ratingInfo.getQuestionContent());
        anserTime.setText(ratingInfo.getAnswerTime());
        expertsAnser.setText(ratingInfo.getAnswerContent());
        writeRatingContent.setText(ratingInfo.getCommentContent());
        int raitingLevel = ratingInfo.getCommentLevel();
        if (raitingLevel!=0) {
            expertsStar.setRating(raitingLevel);
            expertsStar.setIsIndicator(true);
        		}
        if (!TextUtils.isEmpty(writeRatingContent.getText().toString())) {
            writeRatingContent.setEnabled(false);
            writeRatingContent.setFocusable(false);
            postRating.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(ratingInfo.getHeadPortrait())) {
            expertsPhoto.setImageResource(R.mipmap.zhuanjiamoren);
        		}else {
            Bitmap bitmap = Base64MapUtils.stringToBitmap(ratingInfo.getHeadPortrait());
            expertsPhoto.setImageBitmap(bitmap);
        }

        anserCode=ratingInfo.getAnswerCode();
        Log.i("TAG","成功评价---------------"+ratingInfo);
    }

    @Override
    public void getPostRatingFailed(String msg) {
        Log.i("TAG","提交评分错误---------------"+msg);
    }

    @Override
    public void postRatingSucess(PostRatingResultBean postRatingResultBean) {
            if (postRatingResultBean.isSuccess()) {
                Toast.makeText(this,"评论成功！",Toast.LENGTH_SHORT).show();
                writeRatingContent.setEnabled(false);
                expertsStar.setIsIndicator(true);
                postRating.setVisibility(View.GONE);
            		}else {
                Toast.makeText(this,"评论失败！",Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void psotRatingFailed(String msg) {
        Log.i("TAG","提交评论失败---------------------------"+msg);
    }
}
