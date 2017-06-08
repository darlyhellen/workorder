package com.xiangxun.workorder.bean;

import android.os.Parcel;
import android.os.Parcelable;


//@Document(collection="PROPERTY_ASSET_INFO")
public class EquipmentInfo implements Parcelable {
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
    public int cabinetStatus;


    public EquipmentInfo() {
    }

    public EquipmentInfo(Parcel in) {
        //顺序要和writeToParcel写的顺序一样
        id = in.readString();
        deviceid = in.readString();
        assetcode = in.readString();
        assetname = in.readString();
        assetmodel = in.readString();
        assettype = in.readString();
        assetstatus = in.readString();
        factoryId = in.readString();
        guaranteetime = in.readString();
        purchasetime = in.readString();
        manufacturer = in.readString();
        engineering = in.readString();
        installtime = in.readString();
        installplace = in.readString();
        serviceid = in.readString();
        ip = in.readString();
        netStatus = in.readString();
        dataStatus = in.readString();
        payoutstatus = in.readString();
        cabinetStatus = in.readInt();


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(deviceid);
        dest.writeString(assetcode);
        dest.writeString(assetname);
        dest.writeString(assetmodel);
        dest.writeString(assettype);
        dest.writeString(assetstatus);
        dest.writeString(factoryId);
        dest.writeString(guaranteetime);
        dest.writeString(purchasetime);
        dest.writeString(manufacturer);
        dest.writeString(engineering);
        dest.writeString(installtime);
        dest.writeString(installplace);
        dest.writeString(serviceid);
        dest.writeString(ip);
        dest.writeString(netStatus);
        dest.writeString(dataStatus);
        dest.writeString(payoutstatus);
        dest.writeInt(cabinetStatus);
    }

    public static final Parcelable.Creator<EquipmentInfo> CREATOR = new Parcelable.Creator<EquipmentInfo>() {
        public EquipmentInfo createFromParcel(Parcel in) {
            return new EquipmentInfo(in);
        }

        public EquipmentInfo[] newArray(int size) {
            return new EquipmentInfo[size];
        }
    };


}