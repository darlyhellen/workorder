package com.xiangxun.workorder.bean;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @TODO：巡检信息表
 */
public class TourInfo implements Parcelable {
    public String id;
    public String deviceid;
    public String time;
    public String reason;
    public String note;
    public String Checkingpeople;


    public TourInfo() {
    }

    public TourInfo(Parcel in) {
        //顺序要和writeToParcel写的顺序一样
        id = in.readString();
        deviceid = in.readString();
        id = in.readString();
        deviceid = in.readString();
        time = in.readString();
        reason = in.readString();
        note = in.readString();
        Checkingpeople = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(deviceid);
        dest.writeString(time);
        dest.writeString(reason);
        dest.writeString(note);
        dest.writeString(Checkingpeople);
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