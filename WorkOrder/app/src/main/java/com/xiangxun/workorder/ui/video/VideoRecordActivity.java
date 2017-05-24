package com.xiangxun.workorder.ui.video;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
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
    @ViewsBinder(R.id.id_video_capture_imagebutton_switch)
    private ImageButton mBtnSwitch;
    @ViewsBinder(R.id.id_video_capture_imagebutton_setting)
    private ImageButton mBtnSet;
    @ViewsBinder(R.id.id_video_capture_imagebutton_showfiles)
    private ImageButton mBtnShowFile;
    @ViewsBinder(R.id.id_video_crm_count_time)
    private Chronometer mTimer;

    private VideoRecordPresenter presenter;

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private int CameraID = 1;
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
        //开启前必须初始化摄像头
        initCameraAndSurfaceViewHolder();
    }

    public void switchCamera() {
        releaseCamera();
        CameraID = (CameraID + 1) % Camera.getNumberOfCameras();
        mCamera = openCamera(CameraID);
    }

    /**
     * 释放相机资源
     */
    public void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }


    public static Camera openCamera(int cameraId) {
        try {
            return Camera.open(cameraId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void initListener() {
        mBtnStartStop.setOnClickListener(this);
        mBtnSet.setOnClickListener(this);
        mBtnShowFile.setOnClickListener(this);
        mBtnSwitch.setOnClickListener(this);
    }


    private void initCameraAndSurfaceViewHolder() {
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        switchCamera();
    }


    public boolean isRecording() {
        return mMediaRecorder != null;
    }


    private boolean prepareMediaRecorder() {
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.reset();
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
            @Override
            public void onError(MediaRecorder mr, int what, int extra) {
                DLog.i("setOnErrorListener");
            }
        });
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置声源
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);//设置视频源
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);//设置音频输出格式为3gp  DEFAULT THREE_GPP
        mMediaRecorder.setVideoFrameRate(100);//每秒3帧
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);//录制视频编码 264
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);//设置音频编码为amr_nb   AMR_NB DEFAULT AAC
        mMediaRecorder.setVideoSize(640, 480);//设置录制视频尺寸     mWidth   mHeight
        mMediaRecorder.setVideoEncodingBitRate(5 * 1024 * 1024);
        mMediaRecorder.setOrientationHint(90);// 输出旋转90度，保持竖屏录制
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


    public void followScreenOrientation() {
        final int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mCamera.setDisplayOrientation(180);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            mCamera.setDisplayOrientation(90);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setCamera();
        startPreviewDisplay(holder);
        //下面这个方法能帮我们获取到相机预览帧，我们可以在这里实时地处理每一帧
        mCamera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
//                DLog.i("获取预览帧...");
//                presenter.showVideo(data);
//                DLog.d("预览帧大小：" + String.valueOf(data.length));
            }
        });
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        if (mHolder.getSurface() == null) {
            return;
        }
        followScreenOrientation();
        DLog.d("Restart preview display[SURFACE-CHANGED]");
        stopPreviewDisplay();
        startPreviewDisplay(mHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        DLog.d("Stop preview display[SURFACE-DESTROYED]");
        stopPreviewDisplay();
        mHolder.removeCallback(this);
        mCamera.setPreviewCallback(null);
        mCamera.release();
        mCamera = null;
    }

    public void setCamera() {
        mParameters = mCamera.getParameters();
        mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        mCamera.setParameters(mParameters);
        followScreenOrientation();
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    DLog.d("自动对焦成功");
                }
            }
        });
    }


    private void startPreviewDisplay(SurfaceHolder holder) {
        checkCamera();
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            DLog.e("Error while START preview for camera", e);
        }
    }

    private void stopPreviewDisplay() {
        checkCamera();
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            DLog.e("Error while STOP preview for camera", e);
        }
    }

    private void checkCamera() {
        if (mCamera == null) {
            throw new IllegalStateException("Camera must be set when start/stop preview, call <setCamera(Camera)> to set");
        }
    }

    @Override
    public void end() {

    }

    @Override
    public void setDisableClick() {
        mBtnStartStop.setClickable(false);
        mBtnSet.setClickable(false);
        mBtnShowFile.setClickable(false);
        mBtnSwitch.setClickable(false);
    }

    @Override
    public void setEnableClick() {
        mBtnStartStop.setClickable(true);
        mBtnSet.setClickable(true);
        mBtnShowFile.setClickable(true);
        mBtnSwitch.setClickable(true);
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
