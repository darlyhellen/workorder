package com.xiangxun.workorder.ui.presenter;

import com.hellen.baseframe.application.FrameListener;
import com.xiangxun.workorder.base.StaticListener;
import com.xiangxun.workorder.bean.WorkOrderRoot;
import com.xiangxun.workorder.ui.biz.WorkOrderNewListener;

/**
 * Created by Zhangyuhui/Darly on 2017/5/22.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
public class WorkOrderNewPresenter {

    private WorkOrderNewListener biz;

    public WorkOrderNewPresenter() {
        this.biz = new WorkOrderNewListener();
    }



    public void refreshMainIcon() {
        biz.getWorkOrder(1, null, new FrameListener<WorkOrderRoot>() {
            @Override
            public void onSucces(WorkOrderRoot workOrderRoot) {
                StaticListener.getRefreshMainUIListener().refreshMainUI(workOrderRoot.getData().size());
            }

            @Override
            public void onFaild(int i, String s) {

            }
        });
    }

}
