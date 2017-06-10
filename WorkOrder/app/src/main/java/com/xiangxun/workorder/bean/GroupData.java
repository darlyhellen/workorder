package com.xiangxun.workorder.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 * 顶部标签集合
 */

public class GroupData implements Serializable {
    private String name;
    private String num;

    private List<ChildData> data;

    public GroupData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<ChildData> getData() {
        return data;
    }

    public void setData(List<ChildData> data) {
        this.data = data;
    }
}