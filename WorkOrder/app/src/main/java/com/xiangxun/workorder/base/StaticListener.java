package com.xiangxun.workorder.base;

import com.hellen.baseframe.common.dlog.DLog;

/**
 * Created by Zhangyuhui/Darly on 2017/5/25.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 强制实现静态接口。
 */
public class StaticListener {

    private static StaticListener instance;

    private StaticListener() {
    }

    public static StaticListener getInstance() {
        if (instance == null) {
            instance = new StaticListener();
        }
        DLog.i(instance);
        return instance;
    }

    private RefreshMainUIListener refreshMainUIListener;

    public void setRefreshMainUIListener(RefreshMainUIListener refreshMainUIListener) {
        this.refreshMainUIListener = refreshMainUIListener;
        DLog.i("RefreshMainUIListener is instance！" + instance + this.refreshMainUIListener);
    }

    public RefreshMainUIListener getRefreshMainUIListener() {
        DLog.i("RefreshMainUIListener is use！" + instance + this.refreshMainUIListener);
        if (refreshMainUIListener == null) {
            DLog.i("RefreshMainUIListener is not instance！ Please instance to use");
        }
        return refreshMainUIListener;
    }

    public interface RefreshMainUIListener {

        void refreshMainUI(int num);
    }
}

