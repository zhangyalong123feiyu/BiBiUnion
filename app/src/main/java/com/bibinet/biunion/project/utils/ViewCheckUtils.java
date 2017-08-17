package com.bibinet.biunion.project.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Administrator on 2017/6/11.
 */

public class ViewCheckUtils {
    /**
     * 初始化时调用
     * @param confirmView
     * @param inputView
     */
    public static void inputConfirm(final View confirmView, final EditText... inputView){
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isAccess = true;
                for(int i=0 ; i<inputView.length ; i++){
                    String txt = inputView[i].getText().toString();
                    if(txt.equals("")){
                        isAccess = false;
                    }
                }
                if(isAccess){
                    confirmView.setSelected(true);
                    confirmView.setEnabled(true);
                }else{
                    confirmView.setSelected(false);
                    confirmView.setEnabled(false);
                }
            }
        };
        tw.afterTextChanged(null);
        for(int i=0 ; i<inputView.length ; i++){
            inputView[i].addTextChangedListener(tw);
        }
    }
}
