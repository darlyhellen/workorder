package com.xiangxun.workorder.base;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.util.Log;

import com.hellen.baseframe.application.FrameAPP;
import com.hellen.baseframe.common.db.DBControler;
import com.hellen.baseframe.common.dlog.DLog;
import com.hellen.baseframe.common.obsinfo.ConApp;
import com.xiangxun.workorder.BuildConfig;
import com.xiangxun.workorder.common.Aset;
import com.xiangxun.workorder.common.image.ImageLoaderUtil;

import java.io.File;

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
        AppEnum.WIDTH.setLen(ConApp.getInstance().getWidth());
        AppEnum.HEIGHT.setLen(ConApp.getInstance().getHeight());
        creatFile();
        if (!BuildConfig.DEBUG) {
            // 是否为开发测试环境。正式环境下无需打开调试。
            initStrictMode();
        }
        DBControler.getInstance(this).init();
        //初始化图片工具类
        ImageLoaderUtil.init(this);
        Aset.copyAssetsGif(this);
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
    public void creatFile() {
        // TODO Auto-generated method stub
        File boot = new File(AppEnum.ROOT);
        if (!boot.exists()) {
            boot.mkdir();
        }
        File cacch = new File(AppEnum.CACTH);
        if (!cacch.exists()) {
            cacch.mkdir();
        }
        File dos = new File(AppEnum.DOWNS);
        if (!dos.exists()) {
            dos.mkdir();
        }
        File da = new File(AppEnum.DATA);
        if (!da.exists()) {
            da.mkdir();
        }
        File map = new File(AppEnum.MAP);
        if (!map.exists()) {
            map.mkdir();
        }
        File main = new File(AppEnum.MAINRADIO);
        if (!main.exists()) {
            main.mkdir();
        }
        File image = new File(AppEnum.IMAGE);
        if (!image.exists()) {
            image.mkdir();
        }
        File log = new File(AppEnum.LOG);
        if (!log.exists()) {
            log.mkdir();
        }
        File down = new File(AppEnum.DOWN);
        if (!down.exists()) {
            down.mkdir();
        }
        File video = new File(AppEnum.VIDEO);
        if (!video.exists()) {
            video.mkdir();
        }
    }


    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName() {
        String curVersionName = null;
        try {
            PackageManager pm = instance.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(instance.getPackageName(), 0);
            curVersionName = pi.versionName;
        } catch (Exception e) {
        }
        return curVersionName;
    }
}
