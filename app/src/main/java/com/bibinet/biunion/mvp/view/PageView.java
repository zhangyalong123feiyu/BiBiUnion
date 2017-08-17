package com.bibinet.biunion.mvp.view;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public interface PageView<T>  {
    //读取更多 分页
    void loadMoreSuccess(List<T> historyInfo);
    void loadMoreFail(String s);
}
