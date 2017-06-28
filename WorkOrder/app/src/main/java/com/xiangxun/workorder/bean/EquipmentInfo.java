package com.xiangxun.workorder.bean;

import com.xiangxun.workorder.widget.dialog.TourSelectListener;

import java.io.Serializable;


public class EquipmentInfo implements Serializable, TourSelectListener {
    public String id;
    public String deviceid;
    public String assetcode;
    public String assetname;
    public String assetmodel;
    public String assettype;
    public String assetstatus;
    public String factoryId;
    public String guaranteetime;
    public String purchasetime;
    public String manufacturer;
    public String engineering;
    public String installtime;
    public String installplace;
    public String serviceid;
    public String ip;
    public String netStatus;
    public String dataStatus;
    public String payoutstatus;
    //经度
    public String mapx;
    //纬度
    public String mapy;
    public int cabinetStatus;
    public String code;
    public String name;
    public String orgname;

    public EquipmentInfo() {
    }

}