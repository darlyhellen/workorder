package com.xiangxun.workorder.base;

import android.app.ActivityManager;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.hellen.baseframe.application.FrameAPP;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ConApp;
import com.hellen.baseframe.common.obsinfo.LogApp;
import com.xiangxun.workorder.BuildConfig;

import java.io.File;
import java.util.List;

/**
 * Created by Zhangyuhui/Darly on 2017/5/15.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 系统初始化类，在这里进行初始化数据操作。
 */
public class APP extends FrameAPP {
    protected static APP instance;

    public static APP getInstance() {
        if (instance == null) {
            Log.i("", "系统初始化错误...");
        }
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化新增的日志管理系统,可以进行双向使用，既可以使用Dlog直接进行展示，
        // 也可以使用APPlog进行侧面展示。
        DLog.init(BuildConfig.DEBUG, "WorkOrder");
        //初始化过程中，选择是否打印日志
        showinfo.notifyInfo(BuildConfig.DEBUG);
        //设置缓存文件名
        showinfo.notifyPrefer("WorkOrder");
        //设置固定参数
        showinfo.notifyCal(true);
        ConsMVP.WIDTH.setLen(ConApp.getInstance().getWidth());
        ConsMVP.HEIGHT.setLen(ConApp.getInstance().getHeight());
        creatFile();
        if (BuildConfig.DEBUG) {
            // 是否为开发测试环境。正式环境下无需打开调试。
            initStrictMode();
        }
    }

    /**
     * @Methods initStrictMode
     * @Infomaintion com.xiangxun.except.app, ExceptYH
     * @Author Zhangyuhui
     * @Date 2017-4-17 @Time 下午2:33:15
     * @Description TODO 设置监测模式。常规情况下，检测代码，提醒错误。
     */
    private void initStrictMode() {
        // TODO Auto-generated method stub
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll().penaltyLog().penaltyDialog().build());
        // 第二种
        // StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll()
        // .penaltyLog().build());
    }


    /**
     * 下午6:02:54
     *
     * @author Zhangyuhui BaseActivity.java TODO 建立文件夹
     */
    private void creatFile() {
        // TODO Auto-generated method stub
        File boot = new File(ConsMVP.ROOT);
        if (!boot.exists()) {
            boot.mkdir();
        }
        File main = new File(ConsMVP.MAINRADIO);
        if (!main.exists()) {
            main.mkdir();
        }
        File image = new File(ConsMVP.IMAGE);
        if (!image.exists()) {
            image.mkdir();
        }
        File log = new File(ConsMVP.LOG);
        if (!log.exists()) {
            log.mkdir();
        }
        File down = new File(ConsMVP.DOWN);
        if (!down.exists()) {
            down.mkdir();
        }
    }
}
