package com.xiangxun.workorder.base;

import android.os.Environment;

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

    USERNAME("loginName", 0),

    PASSWORD("password", 0),

    USERID("userid", 0);


    public static final String ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WorkOrder/";

    public static final String MAINRADIO = ROOT + "mainimage/";

    public static final String IMAGE = ROOT + "image/";

    public static final String LOG = ROOT + "log/";

    public static final String DOWN = ROOT + "down/";


    public static String capUri;

    public static final int REQUESTCODE_CAM = 0x1001;

    public static final int REQUESTCODE_CAP = 0x1002;

    public static final int REQUESTCODE_CUT = 0x1003;

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
