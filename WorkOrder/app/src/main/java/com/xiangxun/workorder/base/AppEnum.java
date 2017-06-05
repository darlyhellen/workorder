package com.xiangxun.workorder.base;

import android.os.Environment;

import com.xiangxun.workorder.bean.WorkOrderData;

/**
 * Created by Zhangyuhui/Darly on 2017/5/19.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:整个APP使用的枚举类别
 */
public enum AppEnum {

    WIDTH("screen width", 0),

    HEIGHT("screen height", 0),

    USERREALNAME("userrealname", 0),

    USERNAME("loginName", 0),

    PASSWORD("password", 0),

    USERID("userid", 0),

    COOKIE("cookie", 0);

    //单页调试的假数据
    public static final boolean TEST = false;


    public static WorkOrderData getData() {
        WorkOrderData data = new WorkOrderData();
        data.id = "17051120161587511c2d";
        data.devicename = "G70 K1741 900";
        data.devicecode = "610000000007031075";
        data.deviceip = "172.159.91.133";
        data.devicetype = "device";
        data.isouter = 0;
        data.position = "G70 K1741 900";
        data.companyid = "160321194637834dc78d";
        data.contact = "130521090208040c87f4ab27fd194da7";
        data.telephone = "14785236985";
        data.messages = "西安翔迅科技有限责任公司的李斯，你好：位于[G70 K1741 900]上的设备编号为[610000000007031075]的设备[G70 K1741 900]发生故障，请及时维修。";
        data.assignaccount = "00";
        data.assigntime = "2017-05-11 20:16:15";
        data.isreassign = "0";
        //data.orgid = "170405094524399e776b";
        data.status = 0;
        data.isreassign = "0";
        data.orgid = "170405094524399e776b";
        data.isleave = "0";
        data.assetid = "1704050945245628932d";
        return data;
    }


    public static final String ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WorkOrder/";

    public static final String CACTH = ROOT + "cache/";

    public static final String DOWNS = ROOT + "local/";

    public static final String DATA = DOWNS + "data/";
    
    public static final String MAP = DATA + "map/";

    public static final String MAINRADIO = CACTH + "mainimage/";

    public static final String IMAGE = CACTH + "image/";

    public static final String LOG = CACTH + "log/";

    public static final String DOWN = CACTH + "down/";

    public static final String VIDEO = CACTH + "video/";


    public static String capUri;

    public static final int REQUESTCODE_CAM = 0x1001;

    public static final int REQUESTCODE_CAP = 0x1002;

    public static final int REQUESTCODE_CUT = 0x1003;

    public static final int LISTSTATEFIRST = 0x1004;

    public static final int LISTSTATEREFRESH = 0x1005;

    public static final int LISTSTATELOADMORE = 0x1006;

    private String dec;

    private int len;

    /**
     * 下午1:36:38
     *
     * @author zhangyh2
     */
    private AppEnum(String dec, int len) {
        this.dec = dec;
        this.len = len;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }


}
