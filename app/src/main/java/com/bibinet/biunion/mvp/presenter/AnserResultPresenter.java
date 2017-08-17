package com.bibinet.biunion.mvp.presenter;

import android.util.Log;

import com.bibinet.biunion.mvp.model.AnserResultActivityModel;
import com.bibinet.biunion.mvp.view.AnserResultActivityView;
import com.bibinet.biunion.project.bean.PostRatingResultBean;
import com.bibinet.biunion.project.bean.RatingResultBean;
import com.bibinet.biunion.project.builder.MyCallBack;
import com.google.gson.Gson;

/**
 * Created by bibinet on 2017-7-27.
 */

public class AnserResultPresenter extends BasePresenter {
    private AnserResultActivityModel anserResultActivityModel;
    private AnserResultActivityView anserResultActivityView;
    public AnserResultPresenter(AnserResultActivityView anserResultActivityView) {
        super(anserResultActivityView);
        this.anserResultActivityView=anserResultActivityView;
        anserResultActivityModel=new AnserResultActivityModel();
    }
    public void getPostRating(String objectId){
        waitDialog.open();
        anserResultActivityModel.getPostRating(objectId,new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                RatingResultBean ratingInfo = gson.fromJson(s, RatingResultBean.class);
                anserResultActivityView.getPostRatingSucess(ratingInfo);
            waitDialog.close();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                anserResultActivityView.getPostRatingFailed(throwable.getMessage());
                waitDialog.close();
            }
        });
    }
    public void postRating(String answerCode,int commentLevel,String commentContent,String userId){
        waitDialog.open();
        anserResultActivityModel.postRating(answerCode,commentLevel,commentContent,userId,new MyCallBack(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Gson gson=new Gson();
                PostRatingResultBean posInfo = gson.fromJson(s, PostRatingResultBean.class);
                anserResultActivityView.postRatingSucess(posInfo);
                waitDialog.close();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                super.onError(throwable, b);
                waitDialog.close();
            }
        });
    }
}
