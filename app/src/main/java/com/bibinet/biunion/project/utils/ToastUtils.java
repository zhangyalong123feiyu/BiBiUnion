package com.bibinet.biunion.project.utils;

import android.widget.Toast;

import com.bibinet.biunion.project.application.BiUnionApplication;

/**
 * Created by Wh on 2017-8-11.
 */

public class ToastUtils {
    public static void showShort(String txt){
        Toast.makeText(BiUnionApplication.context, txt, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String txt){
        Toast.makeText(BiUnionApplication.context, txt, Toast.LENGTH_LONG).show();
    }
}
