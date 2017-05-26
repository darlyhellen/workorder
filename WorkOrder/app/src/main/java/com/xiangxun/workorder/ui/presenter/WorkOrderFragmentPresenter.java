package com.xiangxun.workorder.ui.presenter;

import android.app.Activity;
import android.view.View;

import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.bean.WorkOrderRoot;
import com.xiangxun.workorder.ui.biz.WorkOrderListener;
import com.xiangxun.workorder.ui.main.FragmentWorkOrder;
import com.xiangxun.workorder.ui.main.SearchWorkOrderDialogFragment;
import com.xiangxun.workorder.widget.loading.ShowLoading;

import java.util.Map;

/**
 * Created by Zhangyuhui/Darly on 2017/5/19.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:我的工单解析类
 */
public class WorkOrderFragmentPresenter {

    private WorkOrderListener biz;

    private WorkOrderListener.WorkOrderInterface view;

    private ShowLoading loading;

    public WorkOrderFragmentPresenter(WorkOrderListener.WorkOrderInterface view) {
        this.view = view;
        this.biz = new WorkOrderListener();
        loading = new ShowLoading(((FragmentWorkOrder) view).getActivity());
        loading.setMessage(R.string.loading);
    }

    /**
     * @param context
     * @param v       TODO点击事件在这里进行处理
     */
    public void onClickDown(Activity context, View v) {
        switch (v.getId()) {
            case R.id.title_view_back_llayout:
                view.end();
                break;
            case R.id.xw_share:
                DLog.i("搜索按钮点击，跳转到搜索页面。在搜索页面中显示搜索结果");
                SearchWorkOrderDialogFragment dialog = new SearchWorkOrderDialogFragment();
                dialog.show(context.getFragmentManager(), "SearchWorkOrderDialogFragment");
                break;
            default:
                break;
        }
    }

    public void getWorkOrderByPage(int page, String status, String devicename, String devicecode, String deviceip) {

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
