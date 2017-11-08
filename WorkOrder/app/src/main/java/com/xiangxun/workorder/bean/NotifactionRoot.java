package com.xiangxun.workorder.bean;

import java.util.List;

/**
 * Created by Darly on 2017/11/8.
 */
public class NotifactionRoot {

    private String message;

    private int status;

    private int pageNo;

    private int pageSize;

    private int totalSize;

    private int totalPage;

    private List<NotifactionData> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public List<NotifactionData> getData() {
        return data;
    }

    public void setData(List<NotifactionData> data) {
        this.data = data;
    }
}
