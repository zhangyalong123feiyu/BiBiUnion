package com.bibinet.biunion.mvp.presenter;

import android.content.Context;

import com.bibinet.biunion.project.ui.dialog.WaitDialog;

/**
 * Created by Wh on 2017-7-17.
 */

public class BasePresenter {
    protected WaitDialog waitDialog;

    public BasePresenter(Object context){
        waitDialog = new WaitDialog((Context) context);
    }
}
