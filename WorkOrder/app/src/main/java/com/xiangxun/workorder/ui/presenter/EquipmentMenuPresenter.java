package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.view.View;

import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.ObjectData;
import com.xiangxun.workorder.ui.EquipmentMenuAcitvity;
import com.xiangxun.workorder.ui.biz.EquipmentMenuListener;
import com.xiangxun.workorder.widget.loading.ShowLoading;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 新增巡检工单解析类
 */
public class EquipmentMenuPresenter {

    private EquipmentMenuListener biz;

    private EquipmentMenuListener.EquipmentMenuInterface view;

    private ShowLoading loading;

    public EquipmentMenuPresenter(EquipmentMenuListener.EquipmentMenuInterface view) {
        this.view = view;
        this.biz = new EquipmentMenuListener();
        loading = new ShowLoading((EquipmentMenuAcitvity) view);
    }

    /**
     * @TODO：获取设备信息列表。
     */
    public void getEquipment() {
        biz.onStart(loading);
        biz.getEquipment(new FrameListener<ObjectData>() {
            @Override
            public void onSucces(ObjectData o) {
                biz.onStop(loading);
                view.onLoginSuccess(o.getData());
            }

            @Override
            public void onFaild(int i, String s) {
                biz.onStop(loading);
                view.onLoginFailed();
            }
        });
    }
}
