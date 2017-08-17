package com.bibinet.biunion.project.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bibinet.biunion.R;
import com.bibinet.biunion.project.adapter.SearchActivityAdapter;

/**
 * Created by Wh on 2017-8-5.
 */

public class AdapterHolderUtils {
    public static void setType(View main, ImageView image, TextView text, String type){
        if(type==null){
            main.setVisibility(View.GONE);
        }else{
            if (type.equals("A")) {
                image.setImageResource(R.mipmap.shouye_gongcheng);
                text.setText("工程");
                main.setVisibility(View.VISIBLE);
            } else if (type.equals("B")) {
                image.setImageResource(R.mipmap.shouye_huowu);
                text.setText("货物");
                main.setVisibility(View.VISIBLE);
            } else if(type.equals("C")){
                image.setImageResource(R.mipmap.shouye_fuw);
                text.setText("服务");
                main.setVisibility(View.VISIBLE);
            }else{
                main.setVisibility(View.GONE);
            }
        }
    }
}
