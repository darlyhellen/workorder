package com.xiangxun.workorder.ui.biz;

import android.app.Dialog;

import com.hellen.baseframe.application.FramePresenter;
import com.hellen.baseframe.application.FrameView;

/**
 * Created by Zhangyuhui/Darly on 2017/5/26.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 新增巡检工单接口页面
 */
public class TourListener implements FramePresenter {

    public interface TourInterface extends FrameView {

        void end();
    }

    @Override
    public void onStart(Dialog dialog) {
        if (dialog != null) {
            dialog.show();
        }
    }

    /**
     * 编辑完成后进行工单提交
     */
    public void commitTour() {

    }

    @Override
    public void onStop(Dialog dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
