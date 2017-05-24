package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hellen.baseframe.common.db.ThreadInfo;
import com.hellen.baseframe.common.dlog.DLog;
import com.jmolsmobile.landscapevideocapture.VideoCaptureActivity;
import com.jmolsmobile.landscapevideocapture.configuration.CaptureConfiguration;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.ui.MainActivity;
import com.xiangxun.workorder.ui.biz.MainListener;
import com.xiangxun.workorder.ui.main.DownLoadActivity;
import com.xiangxun.workorder.ui.main.SetActivity;
import com.xiangxun.workorder.ui.video.VideoRecordActivity;

import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:首页解析类
 */
public class MainPresenter {

    private MainListener biz;

    private MainListener.MainView view;

    public MainPresenter(MainListener.MainView view) {
        this.view = view;
        this.biz = new MainListener();
    }


    /**
     * @param context
     * @param v       TODO点击事件在这里进行处理
     */
    public void onClickDown(Context context, View v) {
        switch (v.getId()) {
            case R.id.button:
                view.getPop().show(v);
                break;
            case R.id.insert:
                view.getBean().save();
                break;
            case R.id.update:
                view.getBean().setFileName("张宇辉");
                view.getBean().save();
                break;
            case R.id.select:
                List lv = view.getBean().selectUrl();
                for (Object be : lv) {
                    DLog.i(be);
                }
                List ls = view.getBean().findAll();
                for (Object be : ls) {
                    DLog.i(((ThreadInfo) be).toString());
                }
                break;
            case R.id.delete:
                view.getBean().delete();
                break;
            case R.id.down:
                context.startActivity(new Intent(context, DownLoadActivity.class));
                break;
            case R.id.video:
                context.startActivity(new Intent(context, VideoRecordActivity.class));
                videoPage(context);
                break;
            case R.id.xw_share:
                //進入設置頁面
                DLog.i("设置按钮点击");
                context.startActivity(new Intent(context, SetActivity.class));
                break;
        }
    }

    private void videoPage(Context context) {
        CaptureConfiguration.Builder builder = new CaptureConfiguration.Builder(AppEnum.WIDTH.getLen(), AppEnum.HEIGHT.getLen(), 1);
        builder.maxDuration(100);
        builder.maxFileSize(200 * 1024 * 1024);
        builder.frameRate(26);
        builder.showRecordingTime();         // Show the elapsed recording time
        builder.noCameraToggle();            // Remove button to toggle between front and back camera

        // Get the CaptureConfiguration
        CaptureConfiguration configuration = builder.build();

        final Intent intent = new Intent(context, VideoCaptureActivity.class);
        intent.putExtra(VideoCaptureActivity.EXTRA_CAPTURE_CONFIGURATION, configuration);
        intent.putExtra(VideoCaptureActivity.EXTRA_OUTPUT_FILENAME, AppEnum.VIDEO + "000000000001.mp4");
        ((MainActivity) context).startActivityForResult(intent, 200);
    }


}
