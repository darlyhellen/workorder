package com.xiangxun.workorder.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.multithread.MultithreadDownLoadCommon;
import com.hellen.baseframe.common.multithread.MultithreadDownLoadManager;
import com.hellen.baseframe.common.multithread.MultithreadDownLoadManager.OnMultithreadUIListener;
import com.hellen.baseframe.common.obsinfo.ToastApp;
import com.xiangxun.workorder.R;
import com.xiangxun.workorder.base.APP;
import com.xiangxun.workorder.base.AppEnum;
import com.xiangxun.workorder.bean.VersionData;
import com.xiangxun.workorder.ui.MaintenanceActivity;
import com.xiangxun.workorder.ui.presenter.WorkOrderNewPresenter;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Zhangyuhui/Darly on 2017/5/25.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 打开APP启动的服务，每隔60s请求一次服务端，获取最新工单，修改首页UI界面。不会修改工单列表。
 */
public class VersionUpdateService extends Service implements OnMultithreadUIListener {
    private VersionData data;

    private NotificationManager nm;
    private Notification notification;
    private RemoteViews views;
    private int notificationId = 43;
    private NotificationCompat.Builder builder;

    /*
     * (non-Javadoc)
     *
     * @see android.app.Service#onBind(android.content.Intent)
     */
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Service#onCreate()
     */
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        data = (VersionData) intent.getSerializableExtra("Root");
        if (APP.isNetworkConnected(APP.getInstance()) && data != null) {
            initService();
        } else {
            ToastApp.showToast(R.string.neterror);
        }
        return START_STICKY;

    }

    /**
     * 初始化服务，启动Timer，进行请求。
     */
    private void initService() {
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // 设置任务栏中下载进程显示的views
        views = new RemoteViews(getPackageName(), R.layout.update_service);
        views.setTextViewText(R.id.tvProcess, "已下载" + 0 + "%");
        views.setProgressBar(R.id.pbDownload, 100, 0, false);
        //实例化NotificationCompat.Builde并设置相关属性
        builder = new NotificationCompat.Builder(this)
                //设置小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(views);
        //设置通知时间，默认为系统发出通知的时间，通常不用设置
        //.setWhen(System.currentTimeMillis());
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        nm.notify(notificationId, builder.build());
        // 更新状态栏上的下载进度信息
        MultithreadDownLoadManager.init(this, MultithreadDownLoadCommon.TYPE_FIFO, 4);
        MultithreadDownLoadManager.getInstance().getFileInfo(data.getUrl(), AppEnum.DOWN);
        MultithreadDownLoadManager.getInstance().setOnMultithreadUIListener(this);
    }

    @Override
    public void onStartDown() {
    }

    @Override
    public void onLoading(float progress) {
        // 更新状态栏上的下载进度信息
        views.setTextViewText(R.id.tvProcess, "已下载" + (int) progress + "%");
        views.setProgressBar(R.id.pbDownload, 100, (int) progress, false);
        builder.setSmallIcon(R.mipmap.ic_launcher).setCustomContentView(views);
        nm.notify(notificationId, builder.build());
    }

    @Override
    public void onSuccess(File file) {
        nm.cancel(notificationId);
        Instanll(file, this);
        // 停止掉当前的服务
        stopSelf();
    }

    @Override
    public void onFailse(String s) {
        ToastApp.showToast(s);
    }

    // 安装下载后的apk文件
    private void Instanll(File file, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
