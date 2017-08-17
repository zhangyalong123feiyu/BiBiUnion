package com.bibinet.biunion.project.application;

import com.bibinet.biunion.project.bean.LoginResultBean;
/**
 * Created by bibinet on 2017-5-18.
 */

public class Constants {
    //APP所用常量
    public final int READPHONE_STATE = 1;
    public static LoginResultBean loginresultInfo = null;

    public static String loginData = null;

    public final static int NET_TYPE_INSIDE_ZHIYUN = 0;  //内网接口 app1913 志云
    public final static int NET_TYPE_INSIDE_YANGZHI = 1;  //内网接口 app1913 杨志
    public final static int NET_TYPE_OUTSIDE = 2; //映射接口 流量
    public final static int NET_TYPE_TEST = 3;  //测试服务接口
    public final static int NET_TYPE_TEST_OUTSIDE = 4;  //测试服务 外网
    public final static int NET_TYPE_FORMAL = 5;  //正式接口 全能

    public static int NET_INTERFACE_TYPE = 5;
}
