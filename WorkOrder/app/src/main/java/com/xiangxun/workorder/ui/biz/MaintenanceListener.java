package com.xiangxun.workorder.ui.biz;

import android.app.Dialog;

import com.hellen.baseframe.application.FramePresenter;
import com.hellen.baseframe.application.FrameView;
import com.hellen.baseframe.common.db.ThreadInfo;
import com.xiangxun.workorder.widget.camera.PhotoPop;

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

    }

    @Override
    public void onStop(Dialog dialog) {

    }


    public interface MaintenanceInterface extends FrameView {

        void goBack();
    }
}
