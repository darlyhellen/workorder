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

}
