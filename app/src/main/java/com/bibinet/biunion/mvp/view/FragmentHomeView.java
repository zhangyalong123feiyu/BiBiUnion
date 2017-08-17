package com.bibinet.biunion.mvp.view;

import com.bibinet.biunion.project.bean.BannerBean;
import com.bibinet.biunion.project.bean.ProjectInfoBean;

import java.util.List;

/**
 * Created by bibinet on 2017-6-1.
 */

public interface FragmentHomeView extends PageView<ProjectInfoBean.ItemsBean>{
   void onLoadBannerSucess(List<BannerBean.ItemBean> bannerInfo);
   void onLoadBannerFailed();
}
