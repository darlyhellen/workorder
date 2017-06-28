package com.xiangxun.workorder.bean;

import java.io.Serializable;


/**
 * @TODO：巡检信息表
 */
public class TourInfo implements Serializable {
    public String id;
    public String deviceid;

    public String inserttime;
    public String userid;
    public String name;
    public String mobile;
    public String devicename;
    public String ip;
    public String assettype;
    public String deviceplace;
    public String installtime;

    public String code;

    public String reason;
    public String note;
    //经度
    public String mapx;
    //纬度
    public String mapy;


}