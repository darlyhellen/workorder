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

    //卡口构造函数
    public EquipmentInfo(String factoryId, String payoutstatus, String netStatus, String installtime, String code, String serviceid, String purchasetime,
                         String ip,
                         String deviceid,
                         String id,
                         String guaranteetime,
                         String installplace,
                         String dataStatus,
                         String assetstatus,
                         String assetcode,
                         String assettype,
                         String assetname,
                         int cabinetStatus) {


        this.factoryId = factoryId;
        this.payoutstatus = payoutstatus;
        this.netStatus = netStatus;
        this.installtime = installtime;
        this.code = code;
        this.serviceid = serviceid;
        this.purchasetime = purchasetime;
        this.ip = ip;
        this.deviceid = deviceid;
        this.id = id;
        this.guaranteetime = guaranteetime;
        this.installplace = installplace;
        this.dataStatus = dataStatus;
        this.assetstatus = assetstatus;
        this.assetcode = assetcode;
        this.assettype = assettype;
        this.assetname = assetname;
        this.cabinetStatus = cabinetStatus;

    }

    //智能机柜构造函数
    public EquipmentInfo(String factoryId,
                         String payoutstatus,
                         String netStatus,
                         String installtime,
                         String engineering,
                         String serviceid,
                         String purchasetime,
                         String ip,
                         String deviceid,
                         String id,
                         String guaranteetime,
                         String installplace,
                         String manufacturer,
                         String dataStatus,
                         String assetstatus,
                         String assetmodel,
                         String assetcode,
                         int cabinetStatus,
                         String assettype,
                         String assetname) {
        this.factoryId = factoryId;
        this.payoutstatus = payoutstatus;
        this.netStatus = netStatus;
        this.installtime = installtime;
        this.engineering = engineering;
        this.serviceid = serviceid;
        this.purchasetime = purchasetime;
        this.ip = ip;
        this.deviceid = deviceid;
        this.id = id;
        this.guaranteetime = guaranteetime;
        this.installplace = installplace;
        this.manufacturer = manufacturer;
        this.dataStatus = dataStatus;
        this.assetstatus = assetstatus;
        this.assetmodel = assetmodel;
        this.assetcode = assetcode;
        this.cabinetStatus = cabinetStatus;
        this.assettype = assettype;
        this.assetname = assetname;
    }

    //服务器构造函数
    public EquipmentInfo(
            String factoryId,
            String payoutstatus,
            String netStatus,
            String installtime,
            String purchasetime,
            String ip,
            String deviceid,
            String id,
            String guaranteetime,
            String powerStatus,
            String installplace,
            String dataStatus,
            String assetstatus,
            String assetcode,
            int cabinetStatus,
            String assettype,
            String assetname
    ) {

        this.factoryId = factoryId;
        this.payoutstatus = payoutstatus;
        this.netStatus = netStatus;
        this.installtime = installtime;
        this.purchasetime = purchasetime;
        this.ip = ip;
        this.deviceid = deviceid;
        this.id = id;
        this.guaranteetime = guaranteetime;
        this.installplace = installplace;
        this.dataStatus = dataStatus;
        this.assetstatus = assetstatus;
        this.assetcode = assetcode;
        this.cabinetStatus = cabinetStatus;
        this.assettype = assettype;
        this.assetname = assetname;

    }

    //数据库  平台信息 构造函数
    public EquipmentInfo(
            String factoryId,
            String payoutstatus,
            String netStatus,
            String installtime,
            String engineering,
            String serviceid,
            String purchasetime,
            String ip,
            String deviceid,
            String id,
            String guaranteetime,
            String powerStatus,
            String refreshtime,
            String installplace,
            String manufacturer,
            String dataStatus,
            String assetstatus,
            String assetcode,
            int cabinetStatus,
            String assettype,
            String assetname
    ) {

        this.factoryId = factoryId;
        this.payoutstatus = payoutstatus;
        this.netStatus = netStatus;
        this.installtime = installtime;
        this.engineering = engineering;
        this.serviceid = serviceid;
        this.purchasetime = purchasetime;
        this.ip = ip;
        this.deviceid = deviceid;
        this.id = id;
        this.guaranteetime = guaranteetime;
        this.installplace = installplace;
        this.manufacturer = manufacturer;
        this.dataStatus = dataStatus;
        this.assetstatus = assetstatus;
        this.assetcode = assetcode;
        this.cabinetStatus = cabinetStatus;
        this.assettype = assettype;
        this.assetname = assetname;

    }
    //FTP信息构造函数
    public EquipmentInfo(
            String factoryId,
            String payoutstatus,
            String netStatus,
            String installtime,
            String serviceid,
            String purchasetime,
            String ip,
            String deviceid,
            String id,
            String guaranteetime,
            String powerStatus,
            String refreshtime,
            String installplace,
            String dataStatus,
            String assetstatus,
            String assetcode,
            int cabinetStatus,
            String assettype,
            String assetname
    ) {
        this.factoryId = factoryId;
        this.payoutstatus = payoutstatus;
        this.netStatus = netStatus;
        this.installtime = installtime;
        this.serviceid = serviceid;
        this.purchasetime = purchasetime;
        this.ip = ip;
        this.deviceid = deviceid;
        this.id = id;
        this.guaranteetime = guaranteetime;
        this.installplace = installplace;
        this.dataStatus = dataStatus;
        this.assetstatus = assetstatus;
        this.assetcode = assetcode;
        this.cabinetStatus = cabinetStatus;
        this.assettype = assettype;
        this.assetname = assetname;
    }

}