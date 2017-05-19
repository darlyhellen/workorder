package com.xiangxun.workorder.bean;

import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/19.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
public class WorkOrderRoot {

    private int status;

    private String message;

    private List<WorkOrderData> data;

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

    public void setData(List<WorkOrderData> data) {
        this.data = data;
    }

    public List<WorkOrderData> getData() {
        return this.data;
    }
}
