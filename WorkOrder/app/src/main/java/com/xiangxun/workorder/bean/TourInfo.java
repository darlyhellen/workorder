package com.xiangxun.workorder.bean;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @TODO：巡检信息表
 */
public class TourInfo implements Parcelable {
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


    public TourInfo() {
    }

    public TourInfo(Parcel in) {
        //顺序要和writeToParcel写的顺序一样
        id = in.readString();
        deviceid = in.readString();
        id = in.readString();
        deviceid = in.readString();
        inserttime = in.readString();
        reason = in.readString();
        note = in.readString();
        Checkingpeople = in.readString();
        mapx = in.readString();
        mapy = in.readString();
        userid = in.readString();
        mobile = in.readString();
        name = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(deviceid);
        dest.writeString(inserttime);
        dest.writeString(reason);
        dest.writeString(note);
        dest.writeString(Checkingpeople);
        dest.writeString(mapx);
        dest.writeString(mapy);
        dest.writeString(userid);
        dest.writeString(mobile);
        dest.writeString(name);
    }

    public static final Creator<TourInfo> CREATOR = new Creator<TourInfo>() {
        public TourInfo createFromParcel(Parcel in) {
            return new TourInfo(in);
        }

        public TourInfo[] newArray(int size) {
            return new TourInfo[size];
        }
    };


}