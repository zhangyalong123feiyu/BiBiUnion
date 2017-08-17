package com.bibinet.biunion.mvp.view;

import com.bibinet.biunion.project.bean.PostRatingResultBean;
import com.bibinet.biunion.project.bean.RatingResultBean;

/**
 * Created by bibinet on 2017-7-27.
 */

public interface AnserResultActivityView {
    void getPostRatingSucess(RatingResultBean ratingInfo);
    void getPostRatingFailed(String msg);
    void postRatingSucess(PostRatingResultBean postRatingResultBean);
    void psotRatingFailed(String msg);
}
