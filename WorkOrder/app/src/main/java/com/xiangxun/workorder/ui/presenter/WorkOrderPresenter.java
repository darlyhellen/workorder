package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.StaticListener;
import com.xiangxun.workorder.bean.WorkOrderRoot;
import com.xiangxun.workorder.ui.biz.WorkOrderListener;
import com.xiangxun.workorder.ui.fragment.DetailOrderFragment;
import com.xiangxun.workorder.ui.fragment.SearchWorkOrderDialogFragment;
import com.xiangxun.workorder.ui.main.TourActivity;
import com.xiangxun.workorder.ui.main.WorkOrderActivity;
import com.xiangxun.workorder.widget.loading.ShowLoading;

/**
 * Created by Zhangyuhui/Darly on 2017/5/19.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:我的工单解析类
 */
public class WorkOrderPresenter {

    private WorkOrderListener biz;

    private WorkOrderListener.WorkOrderInterface view;

    private ShowLoading loading;

    public WorkOrderPresenter(WorkOrderListener.WorkOrderInterface view) {
        this.view = view;
        this.biz = new WorkOrderListener();
        loading = new ShowLoading((WorkOrderActivity) view);
        loading.setMessage(R.string.loading);
    }

    /**
     * @param context
     * @param patrol
     * @param v         TODO点击事件在这里进行处理
     * @param workorder
     */
    public void onClickDown(Context context, int patrol, View v, String workorder) {
        switch (v.getId()) {
            case R.id.title_view_back_llayout:
                view.end();
                break;
            case R.id.xw_share:
                if (patrol == 20) {
                    DLog.i("新增按钮点击，跳转到新增巡检页面。在列表页面中显示巡检列表");
                    context.startActivity(new Intent(context, TourActivity.class));
                } else {
                    DLog.i("搜索按钮点击，跳转到搜索页面。在搜索页面中显示搜索结果");
                    SearchWorkOrderDialogFragment dialog = new SearchWorkOrderDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("NAME", view.getDevicename());
                    bundle.putString("NUM", view.getDevicenum());
                    bundle.putString("IP", view.getDeviceip());
                    if (patrol == 5) {
                        //查询全部工单信息
                        bundle.putInt("PATROL", patrol);
                        bundle.putString("WORKORDER", workorder);
                    }
                    dialog.setArguments(bundle);
                    dialog.show(((WorkOrderActivity) context).getFragmentManager(), "SearchWorkOrderDialogFragment");
                }
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
                if ("0".equals(status)) {
                    StaticListener.getInstance().getRefreshMainUIListener().refreshMainUI(data.getTotalSize());
                }
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
