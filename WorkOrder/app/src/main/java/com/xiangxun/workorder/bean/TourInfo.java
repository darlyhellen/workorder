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

public TourInfo(
        String inserttime,
        String id,
        String reason,
        String installtime,
        String deviceplace,
        String name,
        String userid,
        String devicename,
        String assettype,
        String ip,
        String mobile,
        String deviceid
){
   this.inserttime=inserttime;
   this.id=id;
   this.reason=reason;
   this.installtime=installtime;
   this.deviceplace=deviceplace;
   this.name=name;
   this.userid=userid;
   this.devicename=devicename;
   this.assettype=assettype;
   this.ip=ip;
   this.mobile=mobile;
   this.deviceid=deviceid;
}
}