package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.view.View;

import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.EquipmentRoot;
import com.xiangxun.workorder.ui.biz.EquipmentListListener;
import com.xiangxun.workorder.ui.main.EquipmentListActivity;
import com.xiangxun.workorder.widget.loading.ShowLoading;

/**
 * Created by Zhangyuhui/Darly on 2017/6/7.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
public class EquipmentListPresenter {

    private EquipmentListListener.EquipmentListInterface view;
    private EquipmentListListener biz;
    private ShowLoading loading;

    public EquipmentListPresenter(EquipmentListListener.EquipmentListInterface view) {
        this.view = view;
        biz = new EquipmentListListener();
        loading = new ShowLoading((EquipmentListActivity) view);
        loading.setMessage(R.string.loading);
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
            case R.id.title_view_right_linear:
                DLog.i(getClass().getSimpleName(), "title_view_right_linear");
                break;
            default:
                break;
        }
    }

    public void getEquipment(int currentPage, String type, String devicename, String devicenum, String deviceip) {
        biz.onStart(loading);
        biz.getEquipment(currentPage, type, devicename, devicenum, deviceip, new FrameListener<EquipmentRoot>() {
            @Override
            public void onSucces(EquipmentRoot workOrderRoot) {
                biz.onStop(loading);
                view.onWorkOrderSuccess(workOrderRoot.getData());
            }

            @Override
            public void onFaild(int i, String s) {
                biz.onStop(loading);
                view.onWorkOrderFailed();
                switch (i) {
                    case 0:
                        ToastApp.showToast(s);
                        break;
                    case 1:
                        ToastApp.showToast("网络请求异常");
                        break;
                    default:
                        break;
                }
            }
        });

    }
}
