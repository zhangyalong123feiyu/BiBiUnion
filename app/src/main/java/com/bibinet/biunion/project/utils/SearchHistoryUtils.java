package com.bibinet.biunion.project.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Wh on 2017-7-18.
 */

public class SearchHistoryUtils {
    public static void writeData(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences("history", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        List<String> dataList = getData(context);
//        if(dataList.size()>20){
//            dataList.remove(dataList.size()-1);
//        }
        dataList.add(0, key);
        Set<String> set = new LinkedHashSet<String>();
        for(int i=0 ; i<dataList.size() ; i++){
            set.add(dataList.get(i));
        }
        editor.putStringSet("history", set).commit();
    }

    public static List<String> getData(Context context) {
        SharedPreferences sp = context.getSharedPreferences("history", Context.MODE_PRIVATE);
        Set<String> s = sp.getStringSet("history", new LinkedHashSet<String>());
        List<String> l = new ArrayList<String>();
        for(String st : s){
            l.add(st);
        }
        return l;
    }

    public static void clearData(Context context) {
        SharedPreferences sp = context.getSharedPreferences("history", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet("history", new LinkedHashSet<String>()).commit();
    }
}
