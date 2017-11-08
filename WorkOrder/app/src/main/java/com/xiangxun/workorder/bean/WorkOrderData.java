package com.xiangxun.workorder.bean;

import java.io.Serializable;

/**
 * Created by Zhangyuhui/Darly on 2017/5/19.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
public class WorkOrderData implements Serializable {

    public String position;
    public String isleave;
    public String offtime;
    public String deviceip;
    public int status;
    public int isouter;//0:场外  1:场内
    public String contact;
    public String messages;
    public String id;
    public String companyid;
    public String assigntime;
    public String devicecode;
    public String assignaccount;
    public String devicename;
    public String devicetype;
    public String offaccount;
    public String telephone;
    public String assetid;
    public String isreassign;
    public String exceptionid;
    public String orgid;
    public String reason;
    public String note;
    //	联系人
    public String contactname;
    //所属部门
    public String orgname;
    //建设服务厂商
    public String companyname;
    //经度
    public String mapx;
    //纬度
    public String mapy;
    //图片1
    public String photo1;
    //图片2
    public String photo2;
    //图片3
    public String photo3;


    public WorkOrderData() {
    }

    public WorkOrderData(
             String position,
             String isleave,
             String offtime,
             String deviceip,
             String photo1,
             String reason,
             String isouter,
             String contact,
             String messages,
             String id,
             String companyid,
             String orgname,
             String devicecode,
             String note,
             int status,
             String companyname,
             String exceptionid,
             String contactname,
             String assigntime,
             String assignaccount,
             String devicename,
             String devicetype,
             String photo2,
             String photo3,
             String offaccount,
             String telephone,
             String mapx,
             String assetid,
             String mapy,
             String isreassign
    ){
      this.position=  position;
      this.isleave=  isleave;
      this.offtime=  offtime;
      this.deviceip=  deviceip;
      this.photo1=  photo1;
      this.reason=  reason;
      this.contact=  contact;
      this.messages=  messages;
      this.id=  id;
      this.companyid=  companyid;
      this.orgname=  orgname;
      this.devicecode=  devicecode;
      this.note=  note;
      this.status=  status;
      this.companyname=  companyname;
      this.exceptionid=  exceptionid;
      this.contactname=  contactname;
      this.assigntime=  assigntime;
      this.assignaccount=  assignaccount;
      this.devicename=  devicename;
      this.devicetype=  devicetype;
      this.photo2=  photo2;
      this.photo3=  photo3;
      this.offaccount=  offaccount;
      this.telephone=  telephone;
      this.mapx=  mapx;
      this.assetid=  assetid;
      this.mapy=  mapy;
      this.isreassign=  isreassign;
    }
}
