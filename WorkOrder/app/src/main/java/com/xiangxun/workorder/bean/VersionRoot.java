package com.xiangxun.workorder.bean;

/**
 * Created by Zhangyuhui/Darly on 2017/5/18.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
public class VersionRoot {
    private int status;

    private String message;

    private VersionData data;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setData(VersionData data) {
        this.data = data;
    }

    public VersionData getData() {
        return this.data;
    }
}
