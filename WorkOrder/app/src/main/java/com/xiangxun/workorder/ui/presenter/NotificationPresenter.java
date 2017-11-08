package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hellen.baseframe.application.FrameListener;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.Api;
import com.xiangxun.workorder.base.StaticListener;
import com.xiangxun.workorder.bean.WorkOrderRoot;
import com.xiangxun.workorder.common.retrofit.RxTestJson;
import com.xiangxun.workorder.ui.biz.NotificationListener;
import com.xiangxun.workorder.ui.biz.WorkOrderListener;
import com.xiangxun.workorder.ui.fragment.SearchWorkOrderDialogFragment;
import com.xiangxun.workorder.ui.main.NotifactionActivity;
import com.xiangxun.workorder.ui.main.TourActivity;
import com.xiangxun.workorder.ui.main.WorkOrderActivity;
import com.xiangxun.workorder.widget.loading.ShowLoading;

/**
 * Created by Zhangyuhui/Darly on 2017/5/19.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:通知解析類
 */
public class NotificationPresenter {

    private NotificationListener biz;

    private NotificationListener.NotificationInterface view;

    private ShowLoading loading;

    public NotificationPresenter(NotificationListener.NotificationInterface view) {
        this.view = view;
        this.biz = new NotificationListener();
        loading = new ShowLoading((NotifactionActivity) view);
        loading.setMessage(R.string.loading);
    }


    public void getNotification() {
        if (Api.ISOUTLINE){
            view.onNotificationSuccess(RxTestJson.notificationData());
        }else{
            view.onNotificationFailed();
        }
    }


}
