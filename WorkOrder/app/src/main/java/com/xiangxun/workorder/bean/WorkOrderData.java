package com.xiangxun.workorder.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

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
    public String status;
    public String isouter;
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

    public WorkOrderData() {
    }

    public WorkOrderData(Parcel in) {
        //顺序要和writeToParcel写的顺序一样
        position = in.readString();
        isleave = in.readString();
        offtime = in.readString();
        deviceip = in.readString();
        status = in.readString();
        isouter = in.readString();
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
        dest.writeString(status);
        dest.writeString(isouter);
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
