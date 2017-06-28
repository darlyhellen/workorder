package com.xiangxun.workorder.bean;

import java.io.Serializable;


/**
 * @TODO：巡检信息表
 */
public class TourInfo implements Serializable {
    public String id;
    public String deviceid;
    public String inserttime;
    public String reason;
    public String note;
    public String Checkingpeople;
    //经度
    public String mapx;
    //纬度
    public String mapy;

    public String userid;
    public String mobile;
    public String name;
    public String ip;
    public String installplace;
    public String orgname;
    public String assetname;
    public String code;


    public TourInfo() {
    }



}