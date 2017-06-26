package com.xiangxun.workorder.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.ui.presenter.WorkOrderNewPresenter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Zhangyuhui/Darly on 2017/5/25.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 打开APP启动的服务，每隔60s请求一次服务端，获取最新工单，修改首页UI界面。不会修改工单列表。
 */
public class WorkOrderNewService extends Service {
    private Timer timer;

    private WorkOrderNewPresenter presenter;

    private int delay = 60 * 1000;

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
        initService();
        return START_STICKY;
    }

    /**
     * 初始化服务，启动Timer，进行请求。
     */
    private void initService() {
        presenter = new WorkOrderNewPresenter();
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //在线程中启动请求，获取参数
                    if (presenter != null) {
                        presenter.refreshMainIcon();
                    }
                }
            }, 0, delay);
        }

    }

}
