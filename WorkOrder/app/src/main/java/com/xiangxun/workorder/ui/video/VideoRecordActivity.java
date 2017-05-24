package com.xiangxun.workorder.ui.video;

import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;

import com.hellen.baseframe.binder.ContentBinder;
import com.hellen.baseframe.binder.ViewsBinder;
import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.base.BaseActivity;
import com.xiangxun.workorder.ui.biz.VideoRecordListener;
import com.xiangxun.workorder.ui.presenter.VideoRecordPresenter;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Zhangyuhui/Darly on 2017/5/23.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 視頻錄像類
 */
@ContentBinder(R.layout.activity_video_record)
public class VideoRecordActivity extends BaseActivity implements SurfaceHolder.Callback, VideoRecordListener.VideoRecordInterface, View.OnClickListener {

    @ViewsBinder(R.id.id_video_capture_surfaceview)
    private SurfaceView mSurfaceView;
    @ViewsBinder(R.id.id_video_ib_stop)
    private ImageButton mBtnStartStop;
    @ViewsBinder(R.id.id_video_capture_imagebutton_setting)
    private ImageButton mBtnSet;
    @ViewsBinder(R.id.id_video_capture_imagebutton_showfiles)
    private ImageButton mBtnShowFile;
    @ViewsBinder(R.id.id_video_crm_count_time)
    private Chronometer mTimer;

    private VideoRecordPresenter presenter;

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private MediaRecorder mMediaRecorder;
    private Camera.Parameters mParameters;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        // 设置横屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 选择支持半透明模式,在有SurfaceView的activity中使用。
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter = new VideoRecordPresenter(this);
        initCameraAndSurfaceViewHolder();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareMediaRecorder();
            }
        }, 500);
    }


    @Override
    protected void initListener() {
        mBtnStartStop.setOnClickListener(this);
        mBtnSet.setOnClickListener(this);
        mBtnShowFile.setOnClickListener(this);
    }


    private void initCameraAndSurfaceViewHolder() {
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        mCamera = Camera.open();
    }


    public boolean isRecording() {
        return mMediaRecorder != null;
    }


    private boolean prepareMediaRecorder() {
        mMediaRecorder = new MediaRecorder();
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH/*QUALITY_1080P*/));
        mMediaRecorder.setPreviewDisplay(mHolder.getSurface());
        File dir = new File(AppEnum.VIDEO);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String path = dir + "/" + presenter.getDate() + ".mp4";
        mMediaRecorder.setOutputFile(path);
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            releaseMediaRecorder();
            e.printStackTrace();
        }
        return true;
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            mCamera.lock();
        }
    }


    @Override
    public void onClick(View v) {
        presenter.onClickDown(this, v);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        mParameters = mCamera.getParameters();
        mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        mCamera.setParameters(mParameters);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    DLog.d("自动对焦成功");
                }
            }
        });
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();

            //下面这个方法能帮我们获取到相机预览帧，我们可以在这里实时地处理每一帧
            mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
                    DLog.i("获取预览帧...");
                    presenter.showVideo(data);
                    DLog.d("预览帧大小：" + String.valueOf(data.length));
                }
            });
        } catch (IOException e) {
            DLog.d("设置相机预览失败", e);
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mHolder.removeCallback(this);
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    @Override
    public void end() {

    }

    @Override
    public void setDisableClick() {

    }

    @Override
    public void setEnableClick() {

    }

    @Override
    public Chronometer getmTimer() {
        return mTimer;
    }

    @Override
    public ImageButton getmBtnStartStop() {
        return mBtnStartStop;
    }

    @Override
    public boolean startRecording() {
        if (prepareMediaRecorder()) {

            mMediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
                @Override
                public void onInfo(MediaRecorder mr, int what, int extra) {

                }
            });
            mMediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
                @Override
                public void onError(MediaRecorder mr, int what, int extra) {

                }
            });
            mMediaRecorder.start();
            return true;
        } else {
            releaseMediaRecorder();
        }
        return false;
    }

    @Override
    public void stopRecording() {
        if (mMediaRecorder != null) {
            try {
                //下面三个参数必须加，不加的话会奔溃，在mediarecorder.stop();
                //报错为：RuntimeException:stop failed
                mMediaRecorder.setOnErrorListener(null);
                mMediaRecorder.setOnInfoListener(null);
                mMediaRecorder.setPreviewDisplay(null);
                mMediaRecorder.stop();
            } catch (IllegalStateException e) {
                // TODO: handle exception
            }
        }
        releaseMediaRecorder();
    }


}
