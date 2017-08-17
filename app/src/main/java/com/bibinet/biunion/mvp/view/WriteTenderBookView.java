package com.bibinet.biunion.mvp.view;

import com.bibinet.biunion.project.bean.BaseBean;

/**
 * Created by bibinet on 2017-6-22.
 */

public interface WriteTenderBookView {
    void saveTenderBookSucess(BaseBean bean);
    void saveTenderBookFailed(String s);
}
