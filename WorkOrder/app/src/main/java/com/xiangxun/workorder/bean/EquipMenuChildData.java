package com.xiangxun.workorder.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 * 顶部标签集合
 */

public class EquipMenuChildData implements Serializable {
    private String name;
    private String type;
    private int res;

    public EquipMenuChildData(String name, String type, int res) {
        this.name = name;
        this.type = type;
        this.res = res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }
}