package com.xiangxun.workorder.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 * 顶部标签集合
 */

public class EquipMenuGroupData implements Serializable {
    private String name;

    private List<EquipMenuChildData> data;

    public EquipMenuGroupData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EquipMenuChildData> getData() {
        return data;
    }

    public void setData(List<EquipMenuChildData> data) {
        this.data = data;
    }
}