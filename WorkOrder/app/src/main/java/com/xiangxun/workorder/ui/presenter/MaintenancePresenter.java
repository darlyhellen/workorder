package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.StaticListener;
import com.xiangxun.workorder.bean.Patrol;
import com.xiangxun.workorder.bean.WorkOrderRoot;
import com.xiangxun.workorder.ui.MaintenanceActivity;
import com.xiangxun.workorder.ui.biz.MaintenanceListener;
import com.xiangxun.workorder.ui.biz.MaintenanceListener.MaintenanceInterface;
import com.xiangxun.workorder.ui.main.SetActivity;
import com.xiangxun.workorder.widget.loading.ShowLoading;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/26.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 运维首页解析类
 */
public class MaintenancePresenter {

    private MaintenanceInterface view;

    private MaintenanceListener biz;

    private ShowLoading loading;

    public MaintenancePresenter(MaintenanceInterface view) {
        this.view = view;
        this.biz = new MaintenanceListener();
        loading = new ShowLoading((MaintenanceActivity) view);
        loading.setMessage(R.string.loading);
    }

    public List<Patrol> findMaintenance() {
        List<Patrol> data = new ArrayList<>();
        data.add(new Patrol(10, R.string.maintenance_order, R.mipmap.work_order_search, 0));
        data.add(new Patrol(20, R.string.maintenance_tour, R.mipmap.patrol_normal, 0));
        data.add(new Patrol(30, R.string.maintenance_equipment, R.mipmap.work_order_search, 0));
        data.add(new Patrol(40, R.string.maintenance_notification, R.mipmap.patrol_normal, 0));
        return data;
    }

    public void onClickDown(Context context, View v) {
        switch (v.getId()) {
            case R.id.xw_share:
                //進入設置頁面
                DLog.i("设置按钮点击");
                context.startActivity(new Intent(context, SetActivity.class));
                break;
            default:
                break;
        }
    }

    public void getWorkOrderByPage(int page, final String status, String devicename, String devicecode, String deviceip) {

        biz.onStart(loading);

        biz.getWorkOrder(page, status, devicename, devicecode, deviceip, new FrameListener<WorkOrderRoot>() {
            @Override
            public void onSucces(WorkOrderRoot data) {
                biz.onStop(loading);
                view.onWorkOrderSuccess(data.getData());
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
