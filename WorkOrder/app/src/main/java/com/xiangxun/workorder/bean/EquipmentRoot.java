package com.xiangxun.workorder.bean;

import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/6/7.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 设备信息表
 */
public class EquipmentRoot {
    private String message;

    private int status;

    private int pageNo;

    private int pageSize;

    private int totalSize;

    private int totalPage;


    private List<EquipmentInfo> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setData(List<EquipmentInfo> data) {
        this.data = data;
    }

    public List<EquipmentInfo> getData() {
        return this.data;
    }
}
