package com.xiangxun.workorder.ui.biz;

import android.app.Dialog;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.application.FramePresenter;
import com.hellen.baseframe.application.FrameView;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.utiltools.SharePreferHelp;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.base.Api;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.bean.NotifactionData;
import com.xiangxun.workorder.bean.WorkOrderData;
import com.xiangxun.workorder.bean.WorkOrderRoot;
import com.xiangxun.workorder.common.retrofit.RxTestJson;
import com.xiangxun.workorder.common.retrofit.RxjavaRetrofitRequestUtil;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Zhangyuhui/Darly on 2017/5/19.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 通知監聽
 */
public class NotificationListener implements FramePresenter {

    public interface NotificationInterface extends FrameView {

        void onNotificationSuccess(List<NotifactionData> datas);

        void onNotificationFailed();
    }

    @Override
    public void onStart(Dialog dialog) {
        if (dialog != null)
            dialog.show();
    }

    public void getNotification(final int page, final String status, String devicename, String devicecode, String deviceip, final FrameListener<WorkOrderRoot> listener) {
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
                        if (root != null && root.getStatus() == 1 && root.getData() != null) {
                            SharePreferHelp.putValue(AppEnum.WorkOrderRoot.getDec() + page + status, root);
                        }
                        return root;
                    }
                })
                .subscribe(new Observer<WorkOrderRoot>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        WorkOrderRoot root = (WorkOrderRoot) SharePreferHelp.getValue(AppEnum.WorkOrderRoot.getDec() + page + status);
                        if (root != null) {
                            listener.onSucces(root);
                        } else {
                            listener.onFaild(1, e.getMessage());
                        }
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


}
