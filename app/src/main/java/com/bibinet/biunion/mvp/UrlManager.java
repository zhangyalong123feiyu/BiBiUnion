package com.bibinet.biunion.mvp;


import static com.bibinet.biunion.project.application.Constants.*;
/**
 * Created by Wh on 2017-7-13.
 */

public class UrlManager {
    public static int TYPE_PIS = 0;
    public static int TYPE_IIP = 1;
    public static String INSIDE_BASE_ZHIYUN = "http://192.168.1.74:8080/";
    public static String INSIDE_BASE_YANGZHI = "http://192.168.1.78:8080/";
    public static String OUTSIDE_BASE = "http://202.99.212.204:8080/";
    public static String TEST_PIS = "http://192.168.1.17:8100/pis/";   //改ip端口
    public static String TEST_IIP = "http://192.168.1.17:8090/iip/";   //改ip端口
    public static String TEST_PIS_OUTSIDE = "http://202.99.212.204:8100/pis/";   //改ip端口
    public static String TEST_IIP_OUTSIDE = "http://202.99.212.204:8090/iip/";   //改ip端口

    public static String FORMAL_PIS = "http://221.204.177.104:19180/";
    public static String FORMAL_IIP = "http://iip.bibenet.com/";

    public static String getBaseUrl(int type){
        String base = "";
        switch (NET_INTERFACE_TYPE){
            case NET_TYPE_INSIDE_ZHIYUN:
                base +=INSIDE_BASE_ZHIYUN;
                if(type==TYPE_PIS){
                    base+="pis/";
                }else{
                    base+="iip/";
                }
                return base;
            case NET_TYPE_INSIDE_YANGZHI:
                base +=INSIDE_BASE_YANGZHI;
                if(type==TYPE_PIS){
                    base+="pis/";
                }else{
                    base+="iip/";
                }
                return base;
            case NET_TYPE_OUTSIDE:
                base +=OUTSIDE_BASE;
                if(type==TYPE_PIS){
                    base+="pis/";
                }else{
                    base+="iip/";
                }
                return base;
            case NET_TYPE_TEST:
                if(type==TYPE_PIS){
                    base+=TEST_PIS;
                }else{
                    base+=TEST_IIP;
                }
                return base;
            case NET_TYPE_TEST_OUTSIDE:
                if(type==TYPE_PIS){
                    base+=TEST_PIS_OUTSIDE;
                }else{
                    base+=TEST_IIP_OUTSIDE;
                }
                break;
            case NET_TYPE_FORMAL:
                if(type==TYPE_PIS){
                    base+=FORMAL_PIS;
                }else{
                    base+=FORMAL_IIP;
                }
                return base;
        }
        return base;
    }
}
