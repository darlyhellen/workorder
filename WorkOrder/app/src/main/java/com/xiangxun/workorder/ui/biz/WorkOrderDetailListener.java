package com.xiangxun.workorder.ui.biz;

import android.app.Dialog;

import com.hellen.baseframe.application.FramePresenter;
import com.hellen.baseframe.application.FrameView;
import com.xiangxun.workorder.bean.WorkOrderData;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 首页接口请求类
 */
public class WorkOrderDetailListener implements FramePresenter {


    @Override
    public void onStart(Dialog dialog) {

    }

    @Override
    public void onStop(Dialog dialog) {

    }


    public interface WorkOrderDetailInterface extends FrameView {

        WorkOrderData getOrderData();
        
        void end();
    }
}
