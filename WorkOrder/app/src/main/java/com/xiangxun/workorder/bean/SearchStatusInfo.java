package com.xiangxun.workorder.bean;

import com.xiangxun.workorder.widget.dialog.TourSelectListener;

/**
 * Created by Zhangyuhui/Darly on 2017/6/9.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:　工单选项中的状态选择。
 */
public class SearchStatusInfo implements TourSelectListener {
    private String name;
    private String status;

    public SearchStatusInfo(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
