package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.BaseModel;
import com.xiangxun.workorder.ui.biz.MainListener;
import com.xiangxun.workorder.ui.biz.TourListener;
import com.xiangxun.workorder.ui.main.SetActivity;
import com.xiangxun.workorder.ui.main.TourActivity;
import com.xiangxun.workorder.widget.dialog.TourSelectDialog;
import com.xiangxun.workorder.widget.loading.ShowLoading;

import java.util.ArrayList;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 新增巡检工单解析类
 */
public class TourPresenter {

    private TourListener biz;

    private TourListener.TourInterface view;

    private ShowLoading loading;

    public TourPresenter(TourListener.TourInterface view) {
        this.view = view;
        this.biz = new TourListener();
        loading = new ShowLoading((TourActivity) view);
    }


    /**
     * @param context
     * @param v       TODO点击事件在这里进行处理
     */
    public void onClickDown(Context context, View v) {
        switch (v.getId()) {
            case R.id.title_view_back_llayout:
                view.end();
                break;
            case R.id.title_view_right_Flipper01:
                DLog.i("点击提交");
                break;
            case R.id.id_order_name_click:
                new TourSelectDialog(context, new ArrayList<BaseModel.Type>(), view.findName(), "请选择设备名称").show();
                break;
        }
    }

    /**
     * @TODO：获取设备信息列表。
     */
    public void getEquipment() {
        biz.onStart(loading);
        biz.getEquipment(new FrameListener<Object>() {
            @Override
            public void onSucces(Object o) {

            }

            @Override
            public void onFaild(int i, String s) {

            }
        });
    }
}
