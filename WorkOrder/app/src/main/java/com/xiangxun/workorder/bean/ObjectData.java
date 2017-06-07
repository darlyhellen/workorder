package com.xiangxun.workorder.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 * 至尊版标签集合
 */

public class ObjectData implements Serializable {
    private int status;
    private String message;
    private List<GroupData> data;

    public ObjectData() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GroupData> getData() {
        return data;
    }

    public void setData(List<GroupData> data) {
        this.data = data;
    }
}