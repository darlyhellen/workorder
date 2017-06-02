package com.xiangxun.workorder.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Zhangyuhui/Darly on 2017/5/19.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
public class WorkOrderData implements Parcelable {

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
    public double latitude;
    public double longitude;
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

    public WorkOrderData(Parcel in) {
        //顺序要和writeToParcel写的顺序一样
        position = in.readString();
        isleave = in.readString();
        offtime = in.readString();
        deviceip = in.readString();
        status = in.readInt();
        isouter = in.readInt();
        contact = in.readString();
        messages = in.readString();
        id = in.readString();
        companyid = in.readString();
        assigntime = in.readString();
        devicecode = in.readString();
        assignaccount = in.readString();
        devicename = in.readString();
        devicetype = in.readString();
        offaccount = in.readString();
        telephone = in.readString();
        assetid = in.readString();
        isreassign = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        exceptionid = in.readString();
        orgid = in.readString();
        reason = in.readString();
        note = in.readString();
        contactname = in.readString();
        orgname = in.readString();
        companyname = in.readString();
        mapx = in.readString();
        mapy = in.readString();
        photo1 = in.readString();
        photo2 = in.readString();
        photo3 = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(position);
        dest.writeString(isleave);
        dest.writeString(offtime);
        dest.writeString(deviceip);
        dest.writeInt(status);
        dest.writeInt(isouter);
        dest.writeString(contact);
        dest.writeString(messages);
        dest.writeString(id);
        dest.writeString(companyid);
        dest.writeString(assigntime);
        dest.writeString(devicecode);
        dest.writeString(assignaccount);
        dest.writeString(devicename);
        dest.writeString(devicetype);
        dest.writeString(offaccount);
        dest.writeString(telephone);
        dest.writeString(assetid);
        dest.writeString(isreassign);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(exceptionid);
        dest.writeString(orgid);
        dest.writeString(reason);
        dest.writeString(note);
        dest.writeString(contactname);
        dest.writeString(orgname);
        dest.writeString(companyname);
        dest.writeString(mapx);
        dest.writeString(mapy);
        dest.writeString(photo1);
        dest.writeString(photo2);
        dest.writeString(photo3);
    }

    public static final Parcelable.Creator<WorkOrderData> CREATOR = new Parcelable.Creator<WorkOrderData>() {
        public WorkOrderData createFromParcel(Parcel in) {
            return new WorkOrderData(in);
        }

        public WorkOrderData[] newArray(int size) {
            return new WorkOrderData[size];
        }
    };

}
