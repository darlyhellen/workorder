package com.xiangxun.workorder.ui.biz;

import android.app.Dialog;

import com.hellen.baseframe.application.FramePresenter;
import com.hellen.baseframe.application.FrameView;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 首页接口请求类
 */
public class MainListener implements FramePresenter {


    @Override
    public void onStart(Dialog dialog) {

    }

    @Override
    public void onStop(Dialog dialog) {

    }


    public interface MainInterface extends FrameView {

        void end();
    }
}
