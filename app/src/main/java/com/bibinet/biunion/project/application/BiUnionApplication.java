package com.bibinet.biunion.project.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.easemob.helpdeskdemo.DemoHelper;
import com.easemob.helpdeskdemo.Preferences;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by bibinet on 2017-5-18.
 */

public class BiUnionApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        // 加载系统默认设置，字体不随用户设置变化
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());

        x.Ext.init(this);
        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);

        //环信移动客服配置
        Preferences.init(this);
        DemoHelper.getInstance().init(this);

        Configs.createMkdirs();
    }

//    //统一字体
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        if (newConfig.fontScale != 1)//非默认值
//            getResources();
//        super.onConfigurationChanged(newConfig);
//    }
//
//    @Override
//    public Resources getResources() {
//        Resources res = super.getResources();
//        if (res.getConfiguration().fontScale != 1) {//非默认值
//            Configuration newConfig = new Configuration();
//            newConfig.setToDefaults();//设置默认
//            res.updateConfiguration(newConfig, res.getDisplayMetrics());
//        }
//        return res;
//    }
}
