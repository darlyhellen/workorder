package com.xiangxun.workorder.ui.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;

import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.ui.biz.VideoRecordListener;
import com.xiangxun.workorder.ui.video.VideoRecordActivity;

import java.util.Calendar;

/**
 * Created by Zhangyuhui/Darly on 2017/5/23.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
public class VideoRecordPresenter {

    private VideoRecordListener biz;

    private VideoRecordActivity view;

    public VideoRecordPresenter(VideoRecordActivity view) {
        this.view = view;
        this.biz = new VideoRecordListener();
    }


    public void onClickDown(Context context, View v) {
        switch (v.getId()) {
            case R.id.id_video_capture_imagebutton_showfiles:
                DLog.i("id_video_capture_imagebutton_showfiles");
                break;
            case R.id.id_video_capture_imagebutton_setting:
                DLog.i("id_video_capture_imagebutton_setting");
                break;
            case R.id.id_video_ib_stop:
                DLog.i("id_video_ib_stop");
                record();
                break;
        }
    }


    private void record() {
        DLog.d("录像");
        if (view.isRecording()) {
            DLog.d("停止录像");
            view.stopRecording();
            view.getmTimer().stop();
            view.getmBtnStartStop().setBackgroundResource(R.mipmap.rec_start);
        } else {
            if (view.startRecording()) {
                DLog.d("开始录像");
                view.getmTimer().setBase(SystemClock.elapsedRealtime());
                view.getmTimer().start();
                view.getmBtnStartStop().setBackgroundResource(R.mipmap.rec_stop);
            }
        }
    }


    /**
     * 获取系统时间
     */
    public String getDate() {
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);           // 获取年份
        int month = ca.get(Calendar.MONTH);         // 获取月份
        int day = ca.get(Calendar.DATE);            // 获取日
        int minute = ca.get(Calendar.MINUTE);       // 分
        int hour = ca.get(Calendar.HOUR);           // 小时
        int second = ca.get(Calendar.SECOND);       // 秒
        return "" + year + (month + 1) + day + hour + minute + second;
    }

    public void showVideo(byte[] data){
        new ProcessFrameAsyncTask().execute(data);
    }


    public class ProcessFrameAsyncTask extends AsyncTask<byte[], Void, String> {

        @Override
        protected String doInBackground(byte[]... params) {
            processFrame(params[0]);
            return null;
        }

        private void processFrame(byte[] frameData) {

            DLog.i("正在处理预览帧...");
            DLog.i("预览帧大小" + String.valueOf(frameData.length));
            DLog.i("预览帧处理完毕...");
            //下面这段注释掉的代码是把预览帧数据输出到sd卡中，以.yuv格式保存
//            String path = getSDPath();
//            File dir = new File(path + "/FrameTest");
//            if (!dir.exists()) {
//                dir.mkdir();
//            }
//            path = dir + "/" + "testFrame"+".yuv";
//            File file =new File(path);
//            try {
//                FileOutputStream fileOutputStream=new FileOutputStream(file);
//                BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
//                bufferedOutputStream.write(frameData);
//                Log.i(TAG, "预览帧处理完毕...");
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }


    }
}
