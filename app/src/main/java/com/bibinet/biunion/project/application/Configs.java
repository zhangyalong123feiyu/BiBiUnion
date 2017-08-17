package com.bibinet.biunion.project.application;

import android.os.Environment;

import java.io.File;

/**
 * Created by Wh on 2017-7-10.
 */

public class Configs {
    //主体缓存路径
    public final static String cachePathMain = Environment.getExternalStorageDirectory().getAbsolutePath()+"/BiUnion";
    //缓存更新包
    public final static String cachePathUpdate = cachePathMain + "/update";
    //缓存图片
    public final static String cachePathImage = cachePathMain + "/image";

    public static void createMkdirs(){
        if(!new File(cachePathImage).exists()){
            new File(cachePathImage).mkdirs();
        }
        if(!new File(cachePathUpdate).exists()){
            new File(cachePathUpdate).mkdirs();
        }
    }
}
