package com.xiangxun.workorder.ui.biz;

import android.app.Dialog;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.application.FramePresenter;
import com.hellen.baseframe.application.FrameView;
import com.hellen.baseframe.common.db.ThreadInfo;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.bean.WorkOrderRoot;
import com.xiangxun.workorder.common.retrofit.RxjavaRetrofitRequestUtil;
import com.xiangxun.workorder.widget.camera.PhotoPop;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 运维首页接口类
 */
public class MaintenanceListener implements FramePresenter {

    @Override
    public void onStart(Dialog dialog) {
        if (dialog != null)
            dialog.show();
    }

    public void getWorkOrder(int page, String status, String devicename, String devicecode, String deviceip, final FrameListener<WorkOrderRoot> listener) {

        if (!APP.isNetworkConnected(APP.getInstance())) {
            listener.onFaild(0, "网络异常,请检查网络");
            return;
        }
        //在这里进行数据请求
        RxjavaRetrofitRequestUtil.getInstance().get().getWorkOrder(page, status, devicename, devicecode, deviceip).
                subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<JsonObject, WorkOrderRoot>() {
                    @Override
                    public WorkOrderRoot call(JsonObject jsonObject) {
                        DLog.json("Func1", jsonObject.toString());
                        WorkOrderRoot root = new Gson().fromJson(jsonObject, new TypeToken<WorkOrderRoot>() {
                        }.getType());
                        return root;
                    }
                })
                .subscribe(new Observer<WorkOrderRoot>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(1, e.getMessage());
                    }

                    @Override
                    public void onNext(WorkOrderRoot data) {
                        if (data != null) {
                            if (data.getData() != null && data.getStatus() == 1) {
                                listener.onSucces(data);
                            } else {
                                listener.onFaild(0, data.getMessage());
                            }
                        } else {
                            listener.onFaild(0, "解析错误");
                        }
                    }
                });
    }

    @Override
    public void onStop(Dialog dialog) {
        if (dialog != null)
            dialog.dismiss();
    }


    public interface MaintenanceInterface extends FrameView {
        void onWorkOrderSuccess(List<WorkOrderData> datas);

        void onWorkOrderFailed();

        void goBack();
    }
}
