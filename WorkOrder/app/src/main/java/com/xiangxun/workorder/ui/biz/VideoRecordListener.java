package com.xiangxun.workorder.ui.biz;

import android.app.Dialog;
import android.widget.Chronometer;
import android.widget.ImageButton;

import com.hellen.baseframe.application.FramePresenter;
import com.hellen.baseframe.application.FrameView;

/**
 * Created by Zhangyuhui/Darly on 2017/5/23.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 線程, 耗時操作在這裡進行.
 */
public class VideoRecordListener implements FramePresenter {

    public interface VideoRecordInterface extends FrameView {

        Chronometer getmTimer();

        ImageButton getmBtnStartStop();

        boolean startRecording();

        void stopRecording();

        void end();
    }


    @Override
    public void onStart(Dialog dialog) {

    }

    @Override
    public void onStop(Dialog dialog) {

    }
}
